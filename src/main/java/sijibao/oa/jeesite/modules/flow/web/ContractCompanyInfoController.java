/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.flow.entity.ContractCompanyInfo;
import com.sijibao.oa.modules.flow.service.ContractCompanyInfoService;

/**
 * 合同公司信息Controller
 * @author xby
 * @version 2018-07-12
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/contractCompanyInfo")
public class ContractCompanyInfoController extends BaseController {

	@Autowired
	private ContractCompanyInfoService contractCompanyInfoService;
	
	@ModelAttribute
	public ContractCompanyInfo get(@RequestParam(required=false) String id) {
		ContractCompanyInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractCompanyInfoService.get(id);
		}
		if (entity == null){
			entity = new ContractCompanyInfo();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(ContractCompanyInfo contractCompanyInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractCompanyInfo> page = contractCompanyInfoService.findPage(new Page<ContractCompanyInfo>(request, response), contractCompanyInfo); 
		model.addAttribute("page", page);
		return "modules/flow/contractCompanyInfoList";
	}

	@RequestMapping(value = "form")
	public String form(ContractCompanyInfo contractCompanyInfo, Model model) {
		model.addAttribute("contractCompanyInfo", contractCompanyInfo);
		return "modules/flow/contractCompanyInfoForm";
	}

	@RequestMapping(value = "save")
	public String save(ContractCompanyInfo contractCompanyInfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractCompanyInfo)){
			return form(contractCompanyInfo, model);
		}
		if (!"true".equals(checkContractName(contractCompanyInfo.getOldContractName(), contractCompanyInfo.getContractName()))){
			addMessage(model, "保存用户'" + contractCompanyInfo.getContractName() + "'失败，登录名已存在");
			return form(contractCompanyInfo, model);
		}
		contractCompanyInfoService.save(contractCompanyInfo);
		addMessage(redirectAttributes, "保存合同公司信息成功");
		return "redirect:"+Global.getAdminPath()+"/flow/contractCompanyInfo/?repage";
	}
	
	@RequestMapping(value = "delete")
	public String delete(ContractCompanyInfo contractCompanyInfo, RedirectAttributes redirectAttributes) {
		contractCompanyInfoService.delete(contractCompanyInfo);
		addMessage(redirectAttributes, "删除合同公司信息成功");
		return "redirect:"+Global.getAdminPath()+"/flow/contractCompanyInfo/?repage";
	}
	
	/**
	 * 验证合同名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	public String checkContractName(String oldContractName, String contractName) {
		if (contractName !=null && contractName.equals(oldContractName)) {
			return "true";
		} else if(contractName !=null && contractCompanyInfoService.getContractName(contractName) == null){
			return "true";
		}
		return "false";
	}

}