/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.web;

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
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.entity.TaskInfoEntity;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.DemandManagement;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.service.DemandManagementBudgetService;
import com.sijibao.oa.modules.oa.service.DemandManagementService;
import com.sijibao.oa.modules.oa.service.ProjectInfoService;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.PostService;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 需求申请Controller
 * @author xuby
 * @version 2018-03-28
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/oaDemandManagement")
public class DemandManagementController extends BaseController {

	@Autowired
	private DemandManagementService demandManagementService;
	@Autowired
	private DemandManagementBudgetService demandManagementBudgetService;
	@Autowired
	private PostService postService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private ProjectInfoService projectInfoService;
	@ModelAttribute
	public DemandManagement get(@RequestParam(required=false) String id) {
		DemandManagement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = demandManagementService.get(id);
		}
		if (entity == null){
			entity = new DemandManagement();
		}
		return entity;
	}
	
	/**
	 * 市场发起需求申请列表查询
	 * @param demandManagement
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:oaDemandManagement:view")
	@RequestMapping(value = {"list", ""})
	public String list(DemandManagement demandManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		demandManagement.setApplyPerCode(user.getLoginName());
		demandManagement.setBillType(Constant.DEMAND_MANAGEMENT_MARKET);
		Page<DemandManagement> page = demandManagementService.findPage(new Page<DemandManagement>(request, response), demandManagement);
		
		//查询系统中的项目信息
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectState(Constant.PEOJECT_STATE_ONE);
		List<ProjectInfo> projectInfoList = projectInfoService.findList(projectInfo);

		model.addAttribute("projectInfoList", projectInfoList);
		model.addAttribute("page", page);
		return "modules/oa/demandManagementList";
	}
	
	/**
	 * 实施需求发起列表查询
	 * @param demandManagement
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:oaDemandManagement:view")
	@RequestMapping(value = "listImplement")
	public String listImplement(DemandManagement demandManagement, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		demandManagement.setApplyPerCode(user.getLoginName());
		demandManagement.setBillType(Constant.DEMAND_MANAGEMENT_IMPLEMENT);
		Page<DemandManagement> page = demandManagementService.findPage(new Page<DemandManagement>(request, response), demandManagement);
		
		//查询系统中的项目信息
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectState(Constant.PEOJECT_STATE_ONE);
		List<ProjectInfo> projectInfoList = projectInfoService.findList(projectInfo);

		model.addAttribute("projectInfoList", projectInfoList);
		model.addAttribute("page", page);
		return "modules/oa/demandManagementImplementList";
	}
	
	/**
	 * (市场发起)需求发起详情展示
	 * @param demandManagement
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:oaDemandManagement:view")
	@RequestMapping(value = "form")
	public String form(DemandManagement demandManagement, Model model) {
		
		User user = UserUtils.getUser();
		String view = "demandManagementForm";
		DecimalFormat df = new DecimalFormat("0.00");
		if(StringUtils.isNotBlank(demandManagement.getId())){
			// 环节编号
			String taskDefKey = demandManagement.getAct().getTaskDefKey();
			
			String demandTypeName = DictUtils.getDictLabel(demandManagement.getDemandType(), "oa_demand_type", "");
			demandManagement.setDemandTypeName(demandTypeName);
			if(StringUtils.isNotBlank(demandManagement.getAmountSum())){
				demandManagement.setAmountSum(df.format(Double.parseDouble(demandManagement.getAmountSum())));
			}
			if(demandManagement.getAct().isFinishTask()){
				if(Constant.expense_save.equals(demandManagement.getDemandStatus())){
					view = "demandManagementForm";
				}else{
					if(StringUtils.isBlank(taskDefKey) || !taskDefKey.startsWith(Constant.DEMAND_ASSIGNEE_NODE)){
						model.addAttribute("noRepeal", true);
					}
					
					/***************判断是否可以删除报销申请start****************/
					if (StringUtils.isNotBlank(demandManagement.getApplyPerCode())
							&& user.getLoginName().equals(demandManagement.getApplyPerCode()) &&
							!Constant.expense_approve_end.equals(demandManagement.getDemandStatus()) 
							&&!Constant.expense_delete.equals(demandManagement.getDemandStatus())) {
						model.addAttribute("delete", true);
					}
					/***************判断是否可以删除报销申请end****************/
					view = "demandManagementBudgetMarketView";
				}
				
			}else if(!Constant.expense_save.equals(demandManagement.getDemandStatus())
					&& user.getLoginName().equals(demandManagement.getApplyPerCode())){
				if(!Constant.expense_approve_end.equals(demandManagement.getDemandStatus()) 
						&&!Constant.expense_delete.equals(demandManagement.getDemandStatus())){
					model.addAttribute("delete", true);
				}
				view = "demandManagementView";
			}else if(Constant.LAST_NODE_KEY.equals(taskDefKey)){ //如果是最末级受理人则显示预算明细
				model.addAttribute("isShowBudget", Constant.expense_yes);
				view = "demandManagementBudgetMarketForm";
			}else if(!Constant.LAST_NODE_KEY.equals(taskDefKey) && taskDefKey.startsWith(Constant.DEMAND_ASSIGNEE_NODE)){
				view = "demandManagementBudgetMarketAudit";
			}else{
				view = "demandManagementAudit";
			}
			
			if(Constant.DEMAND_MAIN_NODE.equals(taskDefKey) 
					|| Constant.DEMAND_CHIILD_NODE1.equals(taskDefKey)
					 || Constant.DEMAND_CHIILD_NODE2.equals(taskDefKey)){
				PostInfo postInfo = new PostInfo();
				String postCode = SysParamsUtils.getParamValue(taskDefKey, Global.SYS_PARAMS, ""); //指派人员岗位
				postInfo.setPostCode(postCode);
				if(Constant.DEMAND_CHIILD_NODE2.equals(taskDefKey)){
					postInfo.setOfficeId(user.getOffice().getId());
				}
				List<User> employeeList = demandManagementService.queryUserInfoForPostCode(postInfo);
				//查询某个岗位下的所有人员信息
				model.addAttribute("employeeList", employeeList);
			}
			
			//查询预算明细信息
			DemandManagementBudget demandManagementBudget = new DemandManagementBudget();
			demandManagementBudget.setProcCode(demandManagement.getProcCode());
			if(StringUtils.isNotBlank(taskDefKey) &&
					taskDefKey.startsWith(Constant.DEMAND_ASSIGNEE_NODE)){
				if(!Constant.DEMAIND_CHILD_NODE3.equals(taskDefKey)){
					//根据节点key和流程执行ID查询当前需求发起人ID
					TaskInfoEntity taskInfo = new TaskInfoEntity();
					taskInfo.setTaskDefinitionKey(Constant.DEMAIND_CHILD_NODE3);
					taskInfo.setExecutionId(demandManagement.getAct().getExecutionId());
					taskInfo.setProcessInstanceId(demandManagement.getAct().getProcInsId());
					taskInfo = actTaskService.queryAssigneeForExecutionId(taskInfo);
					demandManagementBudget.setUserCode(taskInfo.getAssignee());
				}else{
					demandManagementBudget.setUserCode(user.getLoginName());
				}
			}
			List<DemandManagementBudget> budgetList = demandManagementBudgetService.findList(demandManagementBudget);
			if(budgetList != null && budgetList.size() > 0){
				for(DemandManagementBudget d:budgetList){
					d.setExpenseAmt(df.format(new BigDecimal(d.getExpenseAmt())));
				}
				User assigneeInfo = UserUtils.getByLoginName(demandManagementBudget.getUserCode());
				model.addAttribute("assigneeInfo", assigneeInfo);
				model.addAttribute("budgetList", budgetList);
			}
		}else{
			demandManagement.setApplyPerCode(user.getLoginName());
			demandManagement.setApplyPerName(user.getName());
			demandManagement.setOffice(user.getOffice());
			
			PostInfo postInfo = postService.get(user.getPostIds());
			if(postInfo != null){
				demandManagement.setPostCode(postInfo.getPostCode());
				demandManagement.setPostName(postInfo.getPostName());
			}
		}
		model.addAttribute("demandManagement", demandManagement);
		
		return "modules/oa/"+view;
	}
	
	/**
	 * (实施发起)需求申请详情展示
	 * @param demandManagement
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:oaDemandManagement:view")
	@RequestMapping(value = "formImplement")
	public String formImplement(DemandManagement demandManagement, Model model) {
		
		User user = UserUtils.getUser();
		DecimalFormat df = new DecimalFormat("0.00");
		String view = "demandManagementImplementForm";
		if(StringUtils.isNotBlank(demandManagement.getId())){
			// 环节编号
			String taskDefKey = demandManagement.getAct().getTaskDefKey();
			if(StringUtils.isNotBlank(demandManagement.getAmountSum())){
				demandManagement.setAmountSum(df.format(Double.parseDouble(demandManagement.getAmountSum())));
			}
			// 查看申请单
			if(demandManagement.getAct().isFinishTask()){
				/*************判断是否可以撤销流程start*************/
				if(StringUtils.isBlank(demandManagement.getAct().getTaskId())){
					model.addAttribute("noRepeal", true);
				}else{
					//判断单据状态是否为审批结束
					if(Constant.expense_approve_end.equals(demandManagement.getDemandStatus())){
						model.addAttribute("noRepeal", true);
					}else{
						//判断当前任务是否已经在本人待办任务中
						Act act = actTaskService.queryThisRunTaskId(demandManagement.getAct());
						if(act != null && StringUtils.isNotBlank(act.getAssignee()) && user.getLoginName().equals(act.getAssignee())){
							demandManagement.getAct().setStatus("finish");
							model.addAttribute("noRepeal", true);
						}
					}
				}
				/*************判断是否可以撤销流程end*************/
				
				
				/***************判断是否可以删除报销申请start****************/
				if (StringUtils.isNotBlank(demandManagement.getApplyPerCode())
						&& user.getLoginName().equals(demandManagement.getApplyPerCode()) && 
						!Constant.expense_approve_end.equals(demandManagement.getDemandStatus()) 
						&&!Constant.expense_delete.equals(demandManagement.getDemandStatus())) {
					model.addAttribute("delete", true);
				}
				/***************判断是否可以删除报销申请end****************/
				view = "demandManagementImplementView";
			}
			// 修改环节
			else if ("modify".equals(taskDefKey)){
				view = "demandManagementImplementForm";
			}
			// 审核环节
			else if (taskDefKey.startsWith("audit")){
				view = "demandManagementImplementAudit";
			}
			//保存修改
			if(Constant.expense_save.equals(demandManagement.getDemandStatus())){
				view = "demandManagementImplementForm";
			}
			
			String demandTypeName = DictUtils.getDictLabel(demandManagement.getDemandType(), "oa_demand_type", "");
			demandManagement.setDemandTypeName(demandTypeName);
			
			//查询预算明细信息
			DemandManagementBudget demandManagementBudget = new DemandManagementBudget();
			demandManagementBudget.setProcCode(demandManagement.getProcCode());
			List<DemandManagementBudget> budgetList = demandManagementBudgetService.findList(demandManagementBudget);
			if(budgetList != null && budgetList.size() > 0){
				for(DemandManagementBudget d:budgetList){
					d.setExpenseAmt(df.format(new BigDecimal(d.getExpenseAmt())));
				}
				model.addAttribute("budgetList", budgetList);
			}
		}else{
			demandManagement.setApplyPerCode(user.getLoginName());
			demandManagement.setApplyPerName(user.getName());
			demandManagement.setOffice(user.getOffice());
			
			PostInfo postInfo = postService.get(user.getPostIds());
			if(postInfo != null){
				demandManagement.setPostCode(postInfo.getPostCode());
				demandManagement.setPostName(postInfo.getPostName());
			}
		}
		model.addAttribute("demandManagement", demandManagement);
		return "modules/oa/"+view;
	}
	
	
	
	
	
	/**
	 * 申请审批（完成任务）
	 * @param testAudit
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:oaDemandManagement:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(DemandManagement demandManagement,String title,String assigneeName,Date beginDate,Date endDate , Model model){
		if (Constant.expense_no.equals(demandManagement.getAct().getFlag())
				&& StringUtils.isBlank(demandManagement.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(demandManagement, model);
		}
		//获取当前登录人
		User user = UserUtils.getUser();
		//保存预算明细
		if(demandManagement.getDemandBudgetList() != null && demandManagement.getDemandBudgetList().size() > 0){
			//删除之前的明细信息
			DemandManagementBudget db = new DemandManagementBudget();
			db.setProcCode(demandManagement.getProcCode());
			db.setUserCode(user.getLoginName());
			demandManagementBudgetService.deleteForProcCode(db);
			for(DemandManagementBudget demandManagementBudget:demandManagement.getDemandBudgetList()){
				demandManagementBudget.setProcCode(demandManagement.getProcCode());
				demandManagementBudget.setUserCode(user.getLoginName());
				demandManagementBudgetService.save(demandManagementBudget);
			}
		}
		
		demandManagementService.auditSave(demandManagement,user);
		
		return "redirect:" + adminPath + "/act/task/todo/?title=" + demandManagement.getAct().getTitle() + "&assigneeName=" +  demandManagement.getAct().getAssigneeName()
				+ "&beginDate" + demandManagement.getAct().getBeginDate() + "&endDate" + demandManagement.getAct().getEndDate();
	}
	
	/**
	 * 需求申请保存
	 * @param demandManagement
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:oaDemandManagement:edit")
	@RequestMapping(value = "save")
	public String save(DemandManagement demandManagement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, demandManagement)){
			return form(demandManagement, model);
		}
		demandManagementService.save(demandManagement);
		if(Constant.expense_yes.equals(demandManagement.getSaveFlag())){
			addMessage(redirectAttributes, "保存成功");
		}else{
			addMessage(redirectAttributes, "提交申请成功");
		}
		if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
			return "redirect:"+Global.getAdminPath()+"/oa/oaDemandManagement/?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/oa/oaDemandManagement/listImplement?repage";
		}
	}
	
	/**
	 * 需求申请删除
	 * @param demandManagement
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:oaDemandManagement:edit")
	@RequestMapping(value = "delete")
	public String delete(DemandManagement demandManagement, RedirectAttributes redirectAttributes) {
		demandManagementService.delete(demandManagement);
		addMessage(redirectAttributes, "删除申请成功");
		if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
			return "redirect:"+Global.getAdminPath()+"/oa/oaDemandManagement/?repage";
		}else{
			return "redirect:"+Global.getAdminPath()+"/oa/oaDemandManagement/listImplement?repage";
		}
		
	}
	
	/**
	 * 任务撤销
	 * @param expenseFlow
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:oaDemandManagement:edit")
	@RequestMapping(value = "repealTask")
	public String repealTask(DemandManagement demandManagement, Model model){
		if(StringUtils.isBlank(demandManagement.getAct().getTaskId())){
			addMessage(model,"当前任务ID不能为空");
			demandManagement.getAct().setStatus("finish");
			if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
				return form(demandManagement, model);
			}else{
				return formImplement(demandManagement, model);
			}
		}
		
		if(StringUtils.isBlank(demandManagement.getAct().getProcInsId())){
			addMessage(model,"当前流程实例ID不能为空");
			demandManagement.getAct().setStatus("finish");
			if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
				return form(demandManagement, model);
			}else{
				return formImplement(demandManagement, model);
			}
		}
		
		//校验当前单据是否有任务需要收回
		Act actExec = new Act();
		actExec.setTaskId(demandManagement.getAct().getTaskId());
		actExec.setProcInsId(demandManagement.getAct().getProcInsId());
		List<Act> actExecList = actTaskService.queryThisRunTaskIdForExecutionId(actExec);
		if(actExecList == null || actExecList.size() == 0){
			addMessage(model,"当前单据未找到需要收回的任务，无需进行收回!");
			demandManagement.getAct().setStatus("finish");
			if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
				return form(demandManagement, model);
			}else{
				return formImplement(demandManagement, model);
			}
		}
		//获取当前登录人
		User user = UserUtils.getUser();
		
		//判断当前单据已经审批结束
		if(Constant.expense_approve_end.equals(demandManagement.getDemandStatus())){
			addMessage(model,"当前单据已经审批结束，无法撤回");
			demandManagement.getAct().setStatus("finish");
			if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
				return form(demandManagement, model);
			}else{
				return formImplement(demandManagement, model);
			}
		}
		//发起撤销
		String message = demandManagementService.repealTask(demandManagement,user);
		if(!"success".equals(message)){
			addMessage(model,message);
			if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
				return form(demandManagement, model);
			}else{
				return formImplement(demandManagement, model);
			}
		}
		return "redirect:" + adminPath + "/act/task/todo/?title=" + demandManagement.getAct().getTitle();
	}
	
}