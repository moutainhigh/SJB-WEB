/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

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

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.oa.service.DemandManagementBudgetService;

/**
 * 需求预算明细Controller
 * @author xuby
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaDemandManagementBudget")
public class DemandManagementBudgetController extends BaseController {

	@Autowired
	private DemandManagementBudgetService demandManagementBudgetService;
	
	@ModelAttribute
	public DemandManagementBudget get(@RequestParam(required=false) String id) {
		DemandManagementBudget entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = demandManagementBudgetService.get(id);
		}
		if (entity == null){
			entity = new DemandManagementBudget();
		}
		return entity;
	}
	
	@RequiresPermissions("oa:oaDemandManagementBudget:view")
	@RequestMapping(value = {"list", ""})
	public String list(DemandManagementBudget oaDemandManagementBudget, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DemandManagementBudget> page = demandManagementBudgetService.findPage(new Page<DemandManagementBudget>(request, response), oaDemandManagementBudget); 
		model.addAttribute("page", page);
		return "modules/oa/demandManagementBudgetList";
	}

	@RequiresPermissions("oa:demandManagementBudget:view")
	@RequestMapping(value = "form")
	public String form(DemandManagementBudget demandManagementBudget, Model model) {
		model.addAttribute("oaDemandManagementBudget", demandManagementBudget);
		return "modules/oa/demandManagementBudgetForm";
	}

	@RequiresPermissions("oa:oaDemandManagementBudget:edit")
	@RequestMapping(value = "save")
	public String save(DemandManagementBudget demandManagementBudget, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, demandManagementBudget)){
			return form(demandManagementBudget, model);
		}
		demandManagementBudgetService.save(demandManagementBudget);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaDemandManagementBudget/?repage";
	}
	
	@RequiresPermissions("oa:oaDemandManagementBudget:edit")
	@RequestMapping(value = "delete")
	public String delete(DemandManagementBudget demandManagementBudget, RedirectAttributes redirectAttributes) {
		demandManagementBudgetService.delete(demandManagementBudget);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/oa/oaDemandManagementBudget/?repage";
	}

}