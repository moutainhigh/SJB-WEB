/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.web;

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
import com.sijibao.oa.modules.flow.entity.FlowProcOrg;
import com.sijibao.oa.modules.flow.service.FlowProcOrgService;

/**
 * 流程机构Controller
 * @author xby
 * @version 2018-06-25
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/flowProcOrg")
public class FlowProcOrgController extends BaseController {

	@Autowired
	private FlowProcOrgService flowProcOrgService;
	
	@ModelAttribute
	public FlowProcOrg get(@RequestParam(required=false) String id) {
		FlowProcOrg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flowProcOrgService.get(id);
		}
		if (entity == null){
			entity = new FlowProcOrg();
		}
		return entity;
	}
	
	/**
	 * 列表查询
	 * @param flowProcOrg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:flowProcOrg:view")
	@RequestMapping(value = {"list", ""})
	public String list(FlowProcOrg flowProcOrg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FlowProcOrg> page = flowProcOrgService.findPage(new Page<FlowProcOrg>(request, response), flowProcOrg); 
		model.addAttribute("page", page);
		return "modules/flow/flowProcOrgList";
	}
	
	/**
	 * 保存编辑跳转
	 * @param flowProcOrg
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:flowProcOrg:view")
	@RequestMapping(value = "form")
	public String form(FlowProcOrg flowProcOrg, Model model) {
		model.addAttribute("flowProcOrg", flowProcOrg);
		return "modules/flow/flowProcOrgForm";
	}
	
	/**
	 * 信息保存
	 * @param flowProcOrg
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:flowProcOrg:edit")
	@RequestMapping(value = "save")
	public String save(FlowProcOrg flowProcOrg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, flowProcOrg)){
			return form(flowProcOrg, model);
		}
		flowProcOrgService.save(flowProcOrg);
		addMessage(redirectAttributes, "保存流程机构成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowProcOrg/?repage";
	}
	
	/**
	 * 信息删除
	 * @param flowProcOrg
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:flowProcOrg:edit")
	@RequestMapping(value = "delete")
	public String delete(FlowProcOrg flowProcOrg, RedirectAttributes redirectAttributes) {
		flowProcOrgService.delete(flowProcOrg);
		addMessage(redirectAttributes, "删除流程机构成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowProcOrg/?repage";
	}

}