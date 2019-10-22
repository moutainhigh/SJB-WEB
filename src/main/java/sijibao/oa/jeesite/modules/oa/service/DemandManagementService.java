/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.DemandManagementDao;
import com.sijibao.oa.modules.oa.entity.DemandAssign;
import com.sijibao.oa.modules.oa.entity.DemandManagement;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 需求管理Service
 * @author xuby
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class DemandManagementService extends CrudService<DemandManagementDao, DemandManagement> {
//	@Autowired
//	private AreaDao areaDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private DemandManagementDao demandManagementDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DemandAssignService demandAssignService;
	@Autowired
	private DemandManagementBudgetService demandManagementBudgetService;
	
	public DemandManagement get(String id) {
		return super.get(id);
	}
	
	public List<DemandManagement> findList(DemandManagement demandManagement) {
		return super.findList(demandManagement);
	}
	
	public Page<DemandManagement> findPage(Page<DemandManagement> page, DemandManagement demandManagement) {
		page = super.findPage(page, demandManagement);
		if(page.getList() != null && page.getList().size() > 0){
			for(DemandManagement d:page.getList()){
				String demandTypeName = DictUtils.getDictLabel(d.getDemandType(), "oa_demand_type", "");
				d.setDemandTypeName(demandTypeName);
				d.setDemandStatusTxt(DictUtils.getDictLabel(d.getDemandStatus(), "oa_demand_status", ""));
			}
		}
		return page;
	}
	
	/**
	 * 市场需求申请保存
	 */
	@Transactional(readOnly = false)
	public void save(DemandManagement demandManagement) {
		if(StringUtils.isBlank(demandManagement.getProjectId())){
			demandManagement.setProjectId(null);
			demandManagement.setProjectName(null);
		}
		
		demandManagement.setDemandStatus(Constant.expense_save); //保存状态
		demandManagement.setApplyTime(new Date()); //申请时间
		if(Constant.expense_yes.equals(demandManagement.getSaveFlag())){
			if(StringUtils.isBlank(demandManagement.getProcCode())){
				demandManagement.setProcCode(FlowUtils.genFlowCode());
			}
			String procTitle = "";
			if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
				procTitle = demandManagement.getApplyPerName()+"_发起市场需求申请_"+DateUtils.formatDate(demandManagement.getApplyTime(), "yyyyMMdd")+"_"+demandManagement.getProcCode();
			
			}else if(Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(demandManagement.getBillType())){
				procTitle = demandManagement.getApplyPerName()+"_发起实施需求申请_"+DateUtils.formatDate(demandManagement.getApplyTime(), "yyyyMMdd")+"_"+demandManagement.getProcCode();
			}
			demandManagement.setProcName(procTitle);
		}
		super.save(demandManagement);
		
		//发起流程
		if(!Constant.expense_yes.equals(demandManagement.getSaveFlag())){
			this.startProcess(demandManagement);
		}
		
		//保存预算明细
		if(demandManagement.getDemandBudgetList() != null && demandManagement.getDemandBudgetList().size() > 0){
			//获取当前登录人
			User user = UserUtils.getUser();
			//删除之前的明细信息
			DemandManagementBudget db = new DemandManagementBudget();
			db.setProcCode(demandManagement.getProcCode());
			db.setUserCode(user.getLoginName());
			demandManagementBudgetService.deleteForProcCode(db);
			for(DemandManagementBudget demandManagementBudget:demandManagement.getDemandBudgetList()){
				demandManagementBudget.setProcCode(demandManagement.getProcCode());
				demandManagementBudget.setUserCode(demandManagement.getApplyPerCode());
				demandManagementBudgetService.save(demandManagementBudget);
			}
		}
	}
	
	/**
	 * 发起流程
	 * @param demandManagement
	 */
	@Transactional(readOnly = false)
	public void startProcess(DemandManagement demandManagement){
		
		String procCode = "";
		if(StringUtils.isNotBlank(demandManagement.getProcCode())){
			procCode = demandManagement.getProcCode();
		}else{
			procCode = FlowUtils.genFlowCode(); //流程编码
		}
		
		//发起流程
		String procInsId = "";
		
		String procTitle = "";
		
		//首次发起流程
		if(StringUtils.isBlank(demandManagement.getProcInsId())){
			if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
				if(StringUtils.isBlank(demandManagement.getProcName())){
					procTitle = demandManagement.getApplyPerName()+"_发起市场需求申请_"+DateUtils.formatDate(demandManagement.getApplyTime(), "yyyyMMdd")+"_"+procCode;
				}else{
					procTitle = demandManagement.getProcName();
				}
				procInsId = actTaskService.startProcess(ActUtils.OA_DEMAND_FLOW[0], ActUtils.OA_DEMAND_FLOW[1], demandManagement.getId(), procTitle);
			}else if(Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(demandManagement.getBillType())){
				if(StringUtils.isBlank(demandManagement.getProcName())){
					procTitle = demandManagement.getApplyPerName()+"_发起实施需求申请_"+DateUtils.formatDate(demandManagement.getApplyTime(), "yyyyMMdd")+"_"+procCode;
				}else{
					procTitle = demandManagement.getProcName();
				}
				procInsId = actTaskService.startProcess(ActUtils.OA_IMPLEMENT_FLOW[0], ActUtils.OA_IMPLEMENT_FLOW[1], demandManagement.getId(), procTitle);
				//自动跳过第一个环节
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1");
				Act act = new Act();
				act.setProcInsId(procInsId); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				act.setComment("发起申请");
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), procTitle, vars);
			}else{
				throw new ServiceException("提交失败");
			}
			
			//更新需求申请表信息
			demandManagement.setProcCode(procCode);
			demandManagement.setProcName(procTitle);
			demandManagement.setProcInsId(procInsId);
			demandManagement.setDemandStatus(Constant.expense_approve); //更新状态为审批中
			dao.update(demandManagement);
		}else{ //驳回重新发起
			demandManagement.getAct().setComment("[重新申请]重新提交申请");
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(demandManagement.getAct().getFlag())? "1" : "0");
			actTaskService.complete(demandManagement.getAct().getTaskId(), demandManagement.getAct().getProcInsId(), demandManagement.getAct().getComment(), demandManagement.getProcName(), vars);
			demandManagement.setDemandStatus(Constant.expense_approve); //更新状态为审批中
			dao.update(demandManagement);
		}
	}
	
	/**
	 * 需求申请审批
	 * @param demandManagement
	 */
	@Transactional(readOnly = false)
	public void auditSave(DemandManagement demandManagement,User user) {
		if(StringUtils.isBlank(demandManagement.getAct().getComment())){
			demandManagement.getAct().setComment("提交申请");
		}
		// 设置意见
		demandManagement.getAct().setComment(("yes".equals(demandManagement.getAct().getFlag())?"[同意] ":"[驳回] ")+demandManagement.getAct().getComment());
		
		String flag = demandManagement.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(demandManagement.getAct().getFlag())? "1" : "0");
		
		/***********设置指派办理人员************/
		if(demandManagement.getEmployees() != null){
			List<String> employeeList = new ArrayList<String>();
			for(String employee:demandManagement.getEmployees()){
				employeeList.add(employee);
			}
			vars.put("employeeList",employeeList);
		}
		
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(demandManagement.getAct().getProcInsId()); //流程实例ID
			//查询开始环节任务
			String targetTaskDefinitionKey = "";
			if(Constant.DEMAND_MANAGEMENT_MARKET.equals(demandManagement.getBillType())){
				targetTaskDefinitionKey = Constant.LAST_NODE_KEY;
				
				act.setAssignee(user.getLoginName());
			}else if(Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(demandManagement.getBillType())){
				targetTaskDefinitionKey = "modify";
			}else{
				throw new ServiceException("提交失败");
			}
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			actTaskService.jumpTaskAndAddComentForChild(demandManagement.getAct().getProcInsId(),act.getTaskId(),demandManagement.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
		}else{
			/******************动态设置执行者start***************/
			if(demandManagement.getAct().getTaskDefKey().startsWith(Constant.DEMAND_ASSIGNEE_NODE)){
				DemandAssign demandAssign = new DemandAssign();
				demandAssign.setProcCode(demandManagement.getProcCode());
				demandAssign.setTargetAssign(user.getLoginName());
				/************查询发起分配人员************/
				demandAssign = demandAssignService.querySourceAssignByTargetAssign(demandAssign);
				if(demandAssign != null){
					vars.put("demandAssignee",demandAssign.getSourceAssign());
				}
			}
			/******************动态设置执行者end***************/
			
			actTaskService.complete(demandManagement.getAct().getTaskId(), demandManagement.getAct().getProcInsId(), demandManagement.getAct().getComment(), vars);
		}
		//判断流程是否退回并更新报销状态
		if("no".equals(flag)){
			if(Constant.DEMAND_MANAGEMENT_IMPLEMENT.equals(demandManagement.getBillType())){
				demandManagement.setDemandStatus(Constant.expense_back); //申请状态已驳回
				demandManagementDao.updateDemandStatus(demandManagement);
			}
		}
		
		//判断流程是否结束并更新报销状态
		Act e = new Act();
		e.setProcInsId(demandManagement.getAct().getProcInsId()); //流程实例ID
		List<Act> actList = actTaskService.queryThisRunTaskIdList(e);
		if(actList == null || actList.size() == 0){
			//流程已结束
			demandManagement.setFlowFinishTime(new Date());
			demandManagement.setDemandStatus(Constant.expense_approve_end); //申请状态已完结
			demandManagementDao.updateDemandStatusAndFlowFinshTime(demandManagement);
		}else{
			if(demandManagement.getEmployees() != null){
				/******************保存分配办理记录start********************/
				for(String employee:demandManagement.getEmployees()){
					DemandAssign demandAssign = new DemandAssign();
					demandAssign.setProcCode(demandManagement.getProcCode()); //流程编号
					demandAssign.setSourceAssign(user.getLoginName()); //指派发起人
					demandAssign.setSourceAssignPost(user.getPostIds()); //指派发起人所属岗位
					demandAssign.setTargetAssign(employee); //被指派者 
					demandAssign.setTargetAssignPost(""); //被指派者所属岗位
					demandAssign.setSourceTaskId(demandManagement.getAct().getTaskId()); //发起人任务ID
					demandAssign.setSourceTaskName(demandManagement.getAct().getTaskDefKey());//发起人节点名称
					demandAssign.setIsLast(Constant.IS_NOT_LAST); //是否是最末级受理人
					for(Act a:actList){
						if(employee.equals(a.getAssignee())){
							demandAssign.setTargetTaskId(a.getTaskId()); //被指派者任务ID
							demandAssign.setTargetTaskName(a.getTaskDefKey()); //被指派者节点名称
							if(Constant.LAST_NODE_KEY.equals(a.getTaskDefKey())){
								demandAssign.setIsLast(Constant.IS_LAST);
							}
							break;
						}
					}
					//保存分配信息
					demandAssignService.save(demandAssign);
				}
			/******************保存分配办理记录end********************/
			}
		}
	}
	/**
	 * 删除需求申请
	 */
	@Transactional(readOnly = false)
	public void delete(DemandManagement demandManagement) {
		if(Constant.expense_save.equals(demandManagement.getDemandStatus())){
			//删除人员指派记录
			demandAssignService.deleteByProcCode(demandManagement.getProcCode());
			DemandManagementBudget db = new DemandManagementBudget();
			db.setProcCode(demandManagement.getProcCode());
			demandManagementBudgetService.deleteForProcCode(db);
			dao.deleteDemandInfo(demandManagement);
		}else{
			if(StringUtils.isNotBlank(demandManagement.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(demandManagement.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(demandManagement.getProcInsId()); //删除历史任务
			}
			super.delete(demandManagement);
			//修改单据状态为已删除
			demandManagement.setDemandStatus(Constant.expense_delete); //需求申请状态被删除
			dao.updateDemandStatus(demandManagement);
		}
	}
	
	/**
	 * 根据岗位ID查询该岗位下所有用户
	 * @param user
	 * @return
	 */
	public List<User> queryUserInfoForPostCode(PostInfo postInfo){
		List<User> resultList = userDao.queryUserInfoForPostCode(postInfo);
		return resultList;
	}
	
	/**
	 * 根据流程实例ID查询相关业务信息
	 * @param procInsId
	 * @return
	 */
	public DemandManagement getDemandByProcInsId(String procInsId){
		return dao.getDemandByProcInsId(procInsId);
	}
	
	/**
	 * 任务撤销
	 * @param expenseFlow
	 */
	@Transactional(readOnly = false)
	public String repealTask(DemandManagement demandManagement,User user){
		
		/********判断当前节点的下一个节点任务是否已经完成*********/
		
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		
		//查询被撤销的节点信息
		Act act = new Act();
		if(demandManagement.getAct().getTaskDefKey().startsWith(Constant.DEMAND_ASSIGNEE_NODE)){
			DemandAssign demandAssign = new DemandAssign();
			demandAssign.setProcCode(demandManagement.getProcCode());
			demandAssign.setTargetAssign(user.getLoginName());
			//查询发起分配人员
			demandAssign = demandAssignService.querySourceAssignByTargetAssign(demandAssign);
			act.setAssignee(demandAssign.getSourceAssign());
			vars.put("demandAssignee", user.getLoginName());
		}
		act.setTaskId(demandManagement.getAct().getTaskId());
		act.setProcInsId(demandManagement.getAct().getProcInsId()); //流程实例ID
		List<Act> actList = actTaskService.queryThisRunTaskIdForExecutionId(act); //查询当前任务信息
		if(actList != null && actList.size() > 0){
			act = actList.get(0);
		}else{
			return "当前单据未找到需要收回的流程,收回失败!";
		}
		String targetTaskDefinitionKey = demandManagement.getAct().getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComentForChild(demandManagement.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
		return "success";
	}
	
	/**
	 * 更新单据状态
	 * @param demandManagement
	 */
	@Transactional(readOnly = false)
	public void updateDemandStatus(DemandManagement demandManagement){
		dao.updateDemandStatus(demandManagement);
	}
	
}