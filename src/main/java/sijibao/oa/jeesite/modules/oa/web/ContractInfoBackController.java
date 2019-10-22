/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.sijibao.base.common.api.response.PagerResponse;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.ContractInfoBack;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzContractInfoBackService;
import com.sijibao.oa.office.api.request.contracthis.ContractInfoBackReq;
import com.sijibao.oa.office.api.response.contracthis.ContractInfoBackResponse;

/**
 * 合同信息（后台添加）Controller
 * @author wanxb
 * @version 2018-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/contractInfoBack")
public class ContractInfoBackController extends BaseController {

	@Autowired
	private IntfzContractInfoBackService contractInfoBackService;
	
	@ModelAttribute
	public ContractInfoBack get(@RequestParam(required=false) String id) {
		ContractInfoBack entity = null;
		if (StringUtils.isNotBlank(id)){
			ContractInfoBackResponse ss = contractInfoBackService.get(id);
			entity = change(ss, ContractInfoBack.class);
					
		}
		if (entity == null){
			entity = new ContractInfoBack();
		}
		return entity;
	}
	/**
	 * 列表页面
	 * @param contractInfoBack
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:contractInfoBack:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractInfoBack contractInfoBack, HttpServletRequest request, HttpServletResponse response, Model model) {
		ContractInfoBackReq change = change(contractInfoBack, ContractInfoBackReq.class);
		Page<ContractInfoBack> sss = new Page<ContractInfoBack>(request, response);
		change.setPageNo(sss.getPageNo());
		change.setPageSize(sss.getPageSize());
		change.setDelFlag(contractInfoBack.getDel());
		PagerResponse<ContractInfoBackResponse> findPage = contractInfoBackService.findPage(change);
		ArrayList<ContractInfoBack> list = Lists.newArrayList();
		for (ContractInfoBackResponse ss : findPage.getList()) {
			ContractInfoBack s = change(ss, ContractInfoBack.class);
			list.add(s);
		}
		Page<ContractInfoBack> page = new Page<ContractInfoBack>(request, response); 
		page.setList(list);
		page.setCount(findPage.getCount());
		page.setPageNo(sss.getPageNo());
		page.setPageSize(sss.getPageSize());
		contractInfoBack.setDelFlag("");
		model.addAttribute("page", page);
		return "modules/oa/contractInfoBackList";
	}
	/**
	 * 表单页面
	 * @param contractInfoBack
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:contractInfoBack:view")
	@RequestMapping(value = "form")
	public String form(ContractInfoBack contractInfoBack, Model model) {
		model.addAttribute("contractInfoBack", contractInfoBack);
		return "modules/oa/contractInfoBackForm";
	}
	/**
	 * 保存
	 * @param contractInfoBack
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:contractInfoBack:edit")
	@RequestMapping(value = "save")
	public String save(ContractInfoBack contractInfoBack, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractInfoBack)){
			return form(contractInfoBack, model);
		}
		ContractInfoBackReq change = change(contractInfoBack, ContractInfoBackReq.class);
		User user = UserUtils.getUser();
		change.setUserId(user.getId());
		change.setContractCode(CodeUtils.genCode("HT", "123546", 5));
		
		contractInfoBackService.save(change);
		addMessage(redirectAttributes, "保存合同信息（后台添加）成功");
		return "redirect:"+Global.getAdminPath()+"/oa/contractInfoBack/?repage";
	}
	/**
	 * 删除
	 * @param contractInfoBack
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:contractInfoBack:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractInfoBack contractInfoBack, RedirectAttributes redirectAttributes) {
		ContractInfoBackReq change = change(contractInfoBack, ContractInfoBackReq.class);
		change.setUserId(UserUtils.getUser().getId());
		if("0".equals(change.getDelFlag())){
			change.setDelFlag("1");
		}else{
			change.setDelFlag("0");
		}
		contractInfoBackService.delete(change);
		addMessage(redirectAttributes, "操作成功！");
		return "redirect:"+Global.getAdminPath()+"/oa/contractInfoBack/?repage";
	}

}