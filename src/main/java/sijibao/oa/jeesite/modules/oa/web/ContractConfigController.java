/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.ContractConfig;
import com.sijibao.oa.modules.oa.entity.ContractConfigAttachment;
import com.sijibao.oa.modules.oa.entity.ContractMember;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzContractConfigService;
import com.sijibao.oa.office.api.request.contracthis.ContractConfigAttachmentReq;
import com.sijibao.oa.office.api.request.contracthis.ContractConfigRequest;
import com.sijibao.oa.office.api.request.contracthis.ContractConfigSaveReq;
import com.sijibao.oa.office.api.request.contracthis.ContractMemberReq;
import com.sijibao.oa.office.api.response.contracthis.ContractConfigAttachmentResponse;
import com.sijibao.oa.office.api.response.contracthis.ContractConfigResponse;
import com.sijibao.oa.office.api.response.contracthis.ContractMemberResponse;

/**
 * 合同模版配置Controller
 * @author wanxb
 * @version 2018-10-29
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/contractConfig")
public class ContractConfigController extends BaseController {

	@Autowired
	private IntfzContractConfigService intfzContractConfigService;
	
	public ContractConfig get(@RequestParam(required=false) String id) {
		ContractConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			ContractConfigResponse ss = intfzContractConfigService.getById(id);
			entity = change(ss, ContractConfig.class);
			ArrayList<ContractMember> list = Lists.newArrayList();
			for (ContractMemberResponse sss : ss.getContractMemberList()) {
				ContractMember change = change(sss, ContractMember.class);
				list.add(change);
			}
			entity.setContractMemberList(list);
			ArrayList<ContractConfigAttachment> listt = Lists.newArrayList();
			for (ContractConfigAttachmentResponse ssss : ss.getContractConfigAttachmentList()) {
				ContractConfigAttachment change = change(ssss, ContractConfigAttachment.class);
				listt.add(change);
			}
			entity.setContractConfigAttachmentList(listt);
		}
		if (entity == null){
			entity = new ContractConfig();
		}
		return entity;
	}
	/**
	 * 列表查询
	 * @param contractConfig
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("contract:contractConfig:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractConfig contractConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		ContractConfigRequest change = change(contractConfig, ContractConfigRequest.class);
		User user = UserUtils.getUser();
		change.setUserId(user.getId());
		Page<ContractConfig> page = new Page<ContractConfig>(request, response);
		change.setPageNo(page.getPageNo());
		change.setPageSize(page.getPageSize());
		PagerResponse<ContractConfigResponse> resp = intfzContractConfigService.findPage(change); 
		ArrayList<ContractConfig> list = Lists.newArrayList();
		for (ContractConfigResponse s : resp.getList()) {
			ContractConfig ss = change(s, ContractConfig.class);
			list.add(ss);
		}
		page.setCount(resp.getCount());
		page.setList(list);
		model.addAttribute("page", page);
		return "modules/oa/contractConfigList";
	}
	/**
	 * 表单页面
	 * @param contractConfig
	 * @param model
	 * @return
	 */
	@RequiresPermissions("contract:contractConfig:view")
	@RequestMapping(value = "form")
	public String form(ContractConfig contractConfig, Model model) {
		contractConfig = this.get(contractConfig.getId());
		model.addAttribute("contractConfig", contractConfig);
		return "modules/oa/contractConfigForm";
	}
	/**
	 * 保存、编辑页面
	 * @param contractConfig
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("contract:contractConfig:edit")
	@RequestMapping(value = "save")
	public String save(ContractConfig contractConfig, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractConfig)){
			return form(contractConfig, model);
		}
		
		if(contractConfigName(contractConfig.getOldContractName(),contractConfig.getContractName())){
			addMessage(model, "合同名称已存在！");
			return form(contractConfig, model);
		}
		
		ContractConfigSaveReq change = change(contractConfig, ContractConfigSaveReq.class);
		ArrayList<ContractMemberReq> list = Lists.newArrayList();
		int count = 0;
		for (ContractMember ss : contractConfig.getContractMemberList()) {
			if(Constant.CONTRACT_DATASOURCE_CONTRACT.equals(ss.getDataSource())){
				count++;
			}
			ContractMemberReq aa = change(ss, ContractMemberReq.class);
			list.add(aa);
		}
		if(count == 0){
			addMessage(model, "合同方中必须存在一方数据来源为公司合同信息管理！");
			return form(contractConfig, model);
		}
		if(count > 1){
			addMessage(model, "合同方中只能存在一方数据来源为公司合同信息管理！");
			return form(contractConfig, model);
		}
		if(StringUtils.isBlank(contractConfig.getContractName()) &&
				StringUtils.isNotBlank(contractConfig.getId()) &&
				intfzContractConfigService.getById(contractConfig.getId()) != null
		){
			change.setContractName(intfzContractConfigService.getById(contractConfig.getId()).getContractName());
		}
		
		ArrayList<ContractConfigAttachmentReq> list2 = Lists.newArrayList();
		for (ContractConfigAttachment bb : contractConfig.getContractConfigAttachmentList()) {
			ContractConfigAttachmentReq cc = change(bb, ContractConfigAttachmentReq.class);
			list2.add(cc);
		}
		change.setContractMemberList(list);
		change.setContractConfigAttachmentList(list2);
		User user = UserUtils.getUser();
		change.setUserId(user.getId());
		change.setId(contractConfig.getId());
		intfzContractConfigService.save(change);
		addMessage(redirectAttributes, "保存合同模版成功");
		return "redirect:"+Global.getAdminPath()+"/oa/contractConfig/?repage";
	}
	/**
	 * 删除
	 * @param contractConfig
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("contract:contractConfig:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractConfig contractConfig, RedirectAttributes redirectAttributes) {
		intfzContractConfigService.delete(contractConfig.getId());
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/oa/contractConfig/?repage";
	}
	/**
	 * 逻辑删除
	 * @param contractConfig
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("contract:contractConfig:del")
	@RequestMapping(value = "del")
	public String del(ContractConfig contractConfig, RedirectAttributes redirectAttributes) {
		intfzContractConfigService.del(contractConfig.getId());
		addMessage(redirectAttributes, "操作成功");
		return "redirect:"+Global.getAdminPath()+"/oa/contractConfig/?repage";
	}

	/**
	 * 验证不能重名
	 * @param oldName
	 * @param name
	 * @return
	 */
	public boolean contractConfigName(String oldName, String name) {
		List<ContractConfigResponse> list = intfzContractConfigService.getContractConfigByName(name);
		if (name !=null && name.equals(oldName)) {
			return false;
		} else if(name !=null && list == null ){
			return false;
		}else if(list.size() == 0){
			return false;
		}
		return true;
	}



}