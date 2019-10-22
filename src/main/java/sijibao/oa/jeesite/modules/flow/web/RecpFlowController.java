/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.web;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

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
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.entity.RecpFlow;
import com.sijibao.oa.modules.flow.entity.RecpParams;
import com.sijibao.oa.modules.flow.service.RecpFlowService;
import com.sijibao.oa.modules.flow.service.RecpParamsService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.DemandManagementBudgetService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 接待申请Controller
 * @author xuby
 * @version 2018-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/flow/recpFlow")
public class RecpFlowController extends BaseController {

	@Autowired
	private RecpFlowService recpFlowService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private DemandManagementBudgetService demandManagementBudgetService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RecpParamsService recpParamsService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@ModelAttribute
	public RecpFlow get(@RequestParam(required=false) String id) {
		RecpFlow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = recpFlowService.get(id);
		}
		if (entity == null){
			entity = new RecpFlow();
		}
		return entity;
	}
	
	/**
	 * 接待申请查询列表
	 * @param recpFlow
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:recpFlow:view")
	@RequestMapping(value = {"list", ""})
	public String list(RecpFlow recpFlow, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			recpFlow.setCreateBy(user);
		}
		Page<RecpFlow> page = recpFlowService.findPage(new Page<RecpFlow>(request, response), recpFlow);
		//查询系统中的项目信息
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectState(Constant.PEOJECT_STATE_ONE);
		List<ProjectInfo> projectInfoList = projectInfoService.findList(projectInfo);

		model.addAttribute("projectInfoList", projectInfoList);
		model.addAttribute("page", page);
		return "modules/flow/recpFlowList";
	}
	
	/**
	 * 接待申请发起跳转
	 * @param recpFlow
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:recpFlow:view")
	@RequestMapping(value = "form")
	public String form(RecpFlow recpFlow, Model model) {
		String view = "recpFlowForm";
		User user = UserUtils.getUser();
		DecimalFormat df = new DecimalFormat("0.00");
		if(StringUtils.isBlank(user.getPostIds())){
			throw new ServiceException("申请失败,当前人员没有分配岗位，请联系管理员!"); 
		}
		List<User> userList = systemService.findUserNotAccess(new User());
		model.addAttribute("employeeList",userList);
		// 查看审批申请单
		if (StringUtils.isNotBlank(recpFlow.getId())){

			// 环节编号
			String taskDefKey = recpFlow.getAct().getTaskDefKey();
			
			// 查看工单
			if(recpFlow.getAct().isFinishTask()){
				/*************判断是否可以撤销流程start*************/
				if(StringUtils.isBlank(recpFlow.getAct().getTaskId())){
					model.addAttribute("noRepeal", true);
				}else{
					//判断单据状态是否为审批结束
					if(Constant.expense_approve_end.equals(recpFlow.getRecpStatus())){
						model.addAttribute("noRepeal", true);
					}else{
						//判断当前任务是否已经在本人待办任务中
						Act act = actTaskService.queryThisRunTaskId(recpFlow.getAct());
						if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
							recpFlow.getAct().setStatus("finish");
							model.addAttribute("noRepeal", true);
						}
					}
				}
				/*************判断是否可以撤销流程end*************/
				
				/***************判断是否可以删除报销申请start****************/
				if (StringUtils.isNotBlank(recpFlow.getApplyPerCode())
						&& user.getLoginName().equals(recpFlow.getApplyPerCode())) {
					model.addAttribute("delete", true);
				}
				/***************判断是否可以删除报销申请end****************/
				view = "recpFlowView";
			}
			// 修改环节
			else if ("modify".equals(taskDefKey)){
				view = "recpFlowForm";
			}
			// 审核环节
			else if (taskDefKey.startsWith("audit")){
				//提前打款处理人员
				String bringRemitPersonel = SysParamsUtils.getParamValue(Global.BRING_REMIT_PERSONEL, Global.SYS_PARAMS, "");
				if(bringRemitPersonel.contains(user.getLoginName())){
					model.addAttribute("bringRemitPersonel", true);
				}
				view = "recpFlowAudit";
			}
			//保存修改
			if(Constant.expense_save.equals(recpFlow.getRecpStatus())){
				view = "recpFlowForm";
			}
			if(recpFlow.getBudgetTotal() != null){
				recpFlow.setBudgetTotal(new BigDecimal(df.format(recpFlow.getBudgetTotal())));
			}
			//查询预算明细信息
			DemandManagementBudget demandManagementBudget = new DemandManagementBudget();
			demandManagementBudget.setProcCode(recpFlow.getProcCode());
			List<DemandManagementBudget> budgetList = demandManagementBudgetService.findList(demandManagementBudget);
			if(budgetList != null && budgetList.size() > 0){
				for(DemandManagementBudget d:budgetList){
					d.setExpenseAmt(df.format(new BigDecimal(d.getExpenseAmt())));
				}
				model.addAttribute("budgetList", budgetList);
			}
			
			//查询陪客人员信息
			if(StringUtils.isNotBlank(recpFlow.getProcCode())){
				RecpParams recpParams = new RecpParams();
				recpParams.setProcCode(recpFlow.getProcCode()); //流程编号
				List<RecpParams> recpParamsList = recpParamsService.findList(recpParams);
				if(recpParamsList != null && recpParamsList.size() > 0){
					String[] recpParticPersonels = new String[recpParamsList.size()];
					for(int i = 0;i < recpParamsList.size();i++){
						recpParticPersonels[i] = recpParamsList.get(i).getParamValue();
					}
					recpFlow.setEmployees(recpParticPersonels);
				}
			}
		}else{
			recpFlow.setApplyPerCode(user.getLoginName());
			recpFlow.setApplyPerName(user.getName());
			recpFlow.setApplyTime(new Date());
			recpFlow.setOffice(user.getOffice());
		}
		model.addAttribute("recpFlow", recpFlow);
		return "modules/flow/" + view;
	}
	
	/**
	 * 接待申请保存
	 * @param recpFlow
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:recpFlow:edit")
	@RequestMapping(value = "save")
	public String save(RecpFlow recpFlow, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, recpFlow)){
			return form(recpFlow, model);
		}
		recpFlowService.save(recpFlow);
		String returnMessage = "提交审批成功";
		if(Constant.expense_yes.equals(recpFlow.getSaveFlag())){
			returnMessage = "保存申请成功";
		}
		addMessage(redirectAttributes, returnMessage);
		return "redirect:"+Global.getAdminPath()+"/flow/recpFlow/?repage";
	}
	
	/**
	 * 接待申请删除
	 * @param recpFlow
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("flow:recpFlow:edit")
	@RequestMapping(value = "delete")
	public String delete(RecpFlow recpFlow, RedirectAttributes redirectAttributes) {
		recpFlowService.repealApply(recpFlow);
		addMessage(redirectAttributes, "删除申请成功");
		return "redirect:"+Global.getAdminPath()+"/flow/recpFlow/?repage";
	}
	
	
	/**
	 * 任务审批（完成任务）
	 * @param testAudit
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:recpFlow:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(RecpFlow recpFlow,String title,String assigneeName,Date beginDate,Date endDate , Model model){
		if (Constant.expense_no.equals(recpFlow.getAct().getFlag())
				&& StringUtils.isBlank(recpFlow.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(recpFlow, model);
		}
		recpFlowService.auditSave(recpFlow);
		return "redirect:" + adminPath + "/act/task/todo/?title=" + recpFlow.getAct().getTitle() + "&assigneeName=" +  recpFlow.getAct().getAssigneeName()
				+ "&beginDate" + recpFlow.getAct().getBeginDate() + "&endDate" + recpFlow.getAct().getEndDate();
	}
	
	/**
	 * 任务撤销
	 * @param recpFlow
	 * @param model
	 * @return
	 */
	@RequiresPermissions("flow:recpFlow:edit")
	@RequestMapping(value = "repealTask")
	public String repealTask(RecpFlow recpFlow, Model model){
		if(StringUtils.isBlank(recpFlow.getAct().getTaskId())){
			addMessage(model,"当前任务ID不能为空");
			return form(recpFlow, model);
		}
		
		if(StringUtils.isBlank(recpFlow.getAct().getProcInsId())){
			addMessage(model,"当前流程实例ID不能为空");
			return form(recpFlow, model);
		}
		
		//获取当前登录人
		User user = UserUtils.getUser();
		
		//判断当前单据已经审批结束
		RecpFlow e = recpFlowService.getByProcInsId(recpFlow.getAct().getProcInsId());
		if(Constant.expense_approve_end.equals(e.getRecpStatus())){
			addMessage(model,"当前单据已经审批结束，无法撤回");
			return form(recpFlow, model);
		}
		//发起撤销
		recpFlowService.repealTask(recpFlow,user);
		return "redirect:" + adminPath + "/act/task/todo/?title=" + recpFlow.getAct().getTitle();
	}
	
	/**
	 * 查询审批结束的接待申请
	 * @param recpFlow
	 * @param request
	 * @param response
	 */
	@RequiresPermissions("flow:recpFlow:view")
	@RequestMapping(value = "queryRecpFlowInfo")
	public String queryRecpFlowInfo(RecpFlow recpFlow,
									HttpServletRequest request, HttpServletResponse response, Model model){
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			recpFlow.setCreateBy(user);
		}
		recpFlow.setRecpStatus(Constant.expense_approve_end);
		Page<RecpFlow> page = recpFlowService.findPage(new Page<RecpFlow>(request, response), recpFlow); 
		model.addAttribute("page", page);
		return "modules/flow/recpFlowRelevanceList";
	}
}