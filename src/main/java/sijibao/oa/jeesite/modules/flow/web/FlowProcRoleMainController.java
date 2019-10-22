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
import com.sijibao.oa.modules.flow.entity.FlowProcRoleMain;
import com.sijibao.oa.modules.flow.service.FlowProcRoleMainService;

/**
 * 流程角色主表Controller
 * @author xby
 * @version 2018-06-20
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/flowProcRoleMain")
public class FlowProcRoleMainController extends BaseController {

	@Autowired
	private FlowProcRoleMainService flowProcRoleMainService;
	
	@ModelAttribute
	public FlowProcRoleMain get(@RequestParam(required=false) String id) {
		FlowProcRoleMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flowProcRoleMainService.get(id);
		}
		if (entity == null){
			entity = new FlowProcRoleMain();
		}
		return entity;
	}
	
	/**
	 * 查询流程角色列表
	 * @param flowProcRoleMain
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:flowProcRoleMain:view")
	@RequestMapping(value = {"list", ""})
	public String list(FlowProcRoleMain flowProcRoleMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FlowProcRoleMain> page = flowProcRoleMainService.findPage(new Page<FlowProcRoleMain>(request, response), flowProcRoleMain); 
		model.addAttribute("page", page);
		return "modules/flow/flowProcRoleMainList";
	}
	
	/**
	 * 流程角色新增/编辑跳转
	 * @param flowProcRoleMain
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:flowProcRoleMain:view")
	@RequestMapping(value = "form")
	public String form(FlowProcRoleMain flowProcRoleMain, Model model) {
		model.addAttribute("flowProcRoleMain", flowProcRoleMain);
		return "modules/flow/flowProcRoleMainForm";
	}
	
	/**
	 * 流程角色保存
	 * @param flowProcRoleMain
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:flowProcRoleMain:edit")
	@RequestMapping(value = "save")
	public String save(FlowProcRoleMain flowProcRoleMain, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, flowProcRoleMain)){
			return form(flowProcRoleMain, model);
		}

		flowProcRoleMainService.save(flowProcRoleMain);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowProcRoleMain/?repage";
	}
	
	/**
	 * 流程角色删除
	 * @param flowProcRoleMain
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:flowProcRoleMain:edit")
	@RequestMapping(value = "delete")
	public String delete(FlowProcRoleMain flowProcRoleMain, RedirectAttributes redirectAttributes) {
		flowProcRoleMainService.delete(flowProcRoleMain);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowProcRoleMain/?repage";
	}

}