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
import com.sijibao.oa.modules.flow.entity.FlowProcRoleDetail;
import com.sijibao.oa.modules.flow.service.FlowProcRoleDetailService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 流程角色明细表Controller
 * @author xby
 * @version 2018-06-20
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/flowProcRoleDetail")
public class FlowProcRoleDetailController extends BaseController {

	@Autowired
	private FlowProcRoleDetailService flowProcRoleDetailService;
	
	@ModelAttribute
	public FlowProcRoleDetail get(@RequestParam(required=false) String id) {
		FlowProcRoleDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = flowProcRoleDetailService.get(id);
		}
		if (entity == null){
			entity = new FlowProcRoleDetail();
		}
		return entity;
	}
	
	/**
	 * 查询流程角色明细列表
	 * @param flowProcRoleDetail
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:flowProcRoleDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(FlowProcRoleDetail flowProcRoleDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FlowProcRoleDetail> page = flowProcRoleDetailService.findPage(new Page<FlowProcRoleDetail>(request, response), flowProcRoleDetail); 
		model.addAttribute("page", page);
		model.addAttribute("mainId", flowProcRoleDetail.getMainId());
		return "modules/flow/flowProcRoleDetailList";
	}
	
	/**
	 * 流程角色明细新增/编辑跳转
	 * @param flowProcRoleDetail
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:flowProcRoleDetail:view")
	@RequestMapping(value = "form")
	public String form(FlowProcRoleDetail flowProcRoleDetail, Model model) {
		model.addAttribute("flowProcRoleDetail", flowProcRoleDetail);
		return "modules/flow/flowProcRoleDetailForm";
	}
	
	/**
	 * 流程角色明细保存
	 * @param flowProcRoleDetail
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:flowProcRoleDetail:edit")
	@RequestMapping(value = "save")
	public String save(FlowProcRoleDetail flowProcRoleDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, flowProcRoleDetail)){
			return form(flowProcRoleDetail, model);
		}
		//判断流程角色离职
		User u = UserUtils.get(flowProcRoleDetail.getUserId());
		if(!"1".equals(u.getUserStatus())){
			addMessage(model,"审批人员(" + u.getName() + ")已离职，请重新选择后再提交。");
			return form(flowProcRoleDetail, model);
		}
		try {
            flowProcRoleDetailService.save(flowProcRoleDetail);
            addMessage(redirectAttributes, "保存成功");
        } catch (Exception e){
            addMessage(redirectAttributes, e.getMessage());
            logger.error(e.getMessage());
        }
		return "redirect:"+Global.getAdminPath()+"/flow/flowProcRoleDetail/?repage&mainId="+flowProcRoleDetail.getMainId();
	}
	
	/**
	 * 流程角色明细删除
	 * @param flowProcRoleDetail
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:flowProcRoleDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(FlowProcRoleDetail flowProcRoleDetail, RedirectAttributes redirectAttributes) {
		flowProcRoleDetailService.delete(flowProcRoleDetail);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/flow/flowProcRoleDetail/?repage&mainId="+flowProcRoleDetail.getMainId();
	}

}