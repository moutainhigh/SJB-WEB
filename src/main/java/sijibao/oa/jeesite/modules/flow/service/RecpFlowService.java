/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.flow.dao.RecpFlowDao;
import com.sijibao.oa.modules.flow.dao.RecpParamsDao;
import com.sijibao.oa.modules.flow.entity.RecpFlow;
import com.sijibao.oa.modules.flow.entity.RecpParams;
import com.sijibao.oa.modules.flow.utils.FlowUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.oa.service.DemandManagementBudgetService;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 接待申请Service
 * @author xuby
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class RecpFlowService extends CrudService<RecpFlowDao, RecpFlow> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RecpFlowDao recpFlowDao;
	@Autowired
	private DemandManagementBudgetService demandManagementBudgetService;
	@Autowired
	private RecpParamsDao recpParamsDao;
	public RecpFlow get(String id) {
		return super.get(id);
	}
	
	public List<RecpFlow> findList(RecpFlow recpFlow) {
		List<RecpFlow> resultList = super.findList(recpFlow); 
		for(RecpFlow r:resultList){
			r.setRecpStatusValue(DictUtils.getDictLabel(r.getRecpStatus(), "expense_status", "")); //设置单据状态
		}
		return resultList;
	}
	
	public Page<RecpFlow> findPage(Page<RecpFlow> page, RecpFlow recpFlow) {
		Page<RecpFlow> resultPage = super.findPage(page, recpFlow);

		if(null != recpFlow.getRelated()) {//如果是报销里面关联查询接待申请 则不替换项目名称
			for (RecpFlow r : resultPage.getList()) {
				r.setRecpStatusValue(DictUtils.getDictLabel(r.getRecpStatus(), "expense_status", "")); //设置单据状态
			}
		}else{
			for (RecpFlow r : resultPage.getList()) {
				r.setRecpStatusValue(DictUtils.getDictLabel(r.getRecpStatus(), "expense_status", "")); //设置单据状态
				if (Constant.expense_approve_end.equals(r.getRecpStatus())) {
					r.setProjectName("客户接待");
				}
			}
		}
		return resultPage;
	}
	
	/**
	 * 保存接待申请
	 */
	@Transactional(readOnly = false)
	public void save(RecpFlow recpFlow) {
		String flowCode = null;
		String title = "";
		RecpFlow temp = new RecpFlow();
		if(Constant.expense_yes.equals(recpFlow.getSaveFlag())){
			recpFlow.setRecpStatus(Constant.expense_save); //申请状态保存
		}else{
			recpFlow.setRecpStatus(Constant.expense_approve); //申请状态审批中
		}
		//保存陪客人数信息
		if(recpFlow.getEmployees() != null){
			recpFlow.setRecpParticNum(recpFlow.getEmployees().length); //陪客人数
		}
		if(StringUtils.isBlank(recpFlow.getProjectId())){
			recpFlow.setProjectId(null);
			recpFlow.setProjectName(null);
		}
		// 申请发起
		if (StringUtils.isBlank(recpFlow.getId())){
			flowCode = FlowUtils.genFlowCode();
			recpFlow.setProcCode(flowCode);
			title = recpFlow.getApplyPerName()+"_发起接待申请_"+DateUtils.formatDate(recpFlow.getApplyTime(), "yyyyMMdd")+"_"+flowCode;
			recpFlow.setProcName(title);
			recpFlow.preInsert();
			dao.insert(recpFlow);
		}else{// 重新编辑申请	
			title = recpFlow.getProcName();
			recpFlow.preUpdate();
			dao.update(recpFlow);
			temp = get(recpFlow.getId());
			flowCode = temp.getProcCode();
		}
		
		//保存陪客人数信息
		if(recpFlow.getEmployees() != null){
			//保存陪客人员明细信息
			recpParamsDao.deleteForProcCode(flowCode);
			for(String employee:recpFlow.getEmployees()){
				RecpParams recpParams = new RecpParams();
				recpParams.setProcCode(flowCode); //流程编号
				recpParams.setParamType(Constant.RECP_PARAM_TYPE_01); //类型
				User user = UserUtils.getByLoginName(employee);
				recpParams.setParamName(user.getName()); //人员名称
				recpParams.setParamValue(employee); //人员信息
				recpParams.preInsert();
				recpParamsDao.insert(recpParams);
			}
		}
		
		//保存预算明细
		if(recpFlow.getDemandBudgetList() != null && recpFlow.getDemandBudgetList().size() > 0){
			//获取当前登录人
			User user = UserUtils.getUser();
			//删除之前的明细信息
			DemandManagementBudget db = new DemandManagementBudget();
			db.setProcCode(recpFlow.getProcCode());
			db.setUserCode(user.getLoginName());
			demandManagementBudgetService.deleteForProcCode(db);
			for(int i = 0;i < recpFlow.getDemandBudgetList().size();i++){
				DemandManagementBudget demandManagementBudget = recpFlow.getDemandBudgetList().get(i);
				demandManagementBudget.setProcCode(recpFlow.getProcCode());
				demandManagementBudget.setUserCode(recpFlow.getApplyPerCode());
				demandManagementBudget.setRowNum(i);
				demandManagementBudgetService.save(demandManagementBudget);
			}
		}
		
		if(!Constant.expense_yes.equals(recpFlow.getSaveFlag())){
			
			if(StringUtils.isBlank(recpFlow.getProcInsId())){
				// 启动流程
				String procInsd = actTaskService.startProcess(ActUtils.OA_RECP_FLOW[0], ActUtils.OA_RECP_FLOW[1], recpFlow.getId(), title);
				//自动跳过第一个环节
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "1");
				Act act = new Act();
				act.setProcInsId(procInsd); //流程实例ID
				act = actTaskService.queryThisRunTaskId(act);
				act.setComment("发起申请");
				if(StringUtils.isBlank(title)){
					title = temp.getProcName();
				}
				actTaskService.complete(act.getTaskId(), act.getProcInsId(), act.getComment(), title, vars);
			}else{
				recpFlow.getAct().setComment(("yes".equals(recpFlow.getAct().getFlag())?"[重新申请] ":"[删除] ")+"重新提交申请");
				// 完成流程任务
				Map<String, Object> vars = Maps.newHashMap();
				vars.put("pass", "yes".equals(recpFlow.getAct().getFlag())? "1" : "0");
				actTaskService.complete(recpFlow.getAct().getTaskId(), recpFlow.getAct().getProcInsId(), recpFlow.getAct().getComment(), temp.getProcName(), vars);
			}
		}
	}
	
	/**
	 * 删除接待申请
	 */
	@Transactional(readOnly = false)
	public void delete(RecpFlow recpFlow) {
		super.delete(recpFlow);
	}

	/**
	 * 更新接待申请
	 */
	@Transactional(readOnly = false)
	public void updateRecp(RecpFlow recpFlow) {
		super.save(recpFlow);
	}
	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void auditSave(RecpFlow recpFlow) {
		//获取当前登录人
		User user = UserUtils.getUser();
		// 设置意见
		recpFlow.getAct().setComment(("yes".equals(recpFlow.getAct().getFlag())?"[同意] ":"[驳回] ")+recpFlow.getAct().getComment());
		
		String flag = recpFlow.getAct().getFlag();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(recpFlow.getAct().getFlag())? "1" : "0");
		if("no".equals(flag)){
			Act act = new Act();
			act.setProcInsId(recpFlow.getAct().getProcInsId()); //流程实例ID
			act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
			//查询开始环节任务
			String targetTaskDefinitionKey = "modify";
			actTaskService.jumpTaskAndAddComent(recpFlow.getAct().getProcInsId(),act.getTaskId(),recpFlow.getAct().getComment(),user.getLoginName(),targetTaskDefinitionKey, vars);
		}else{
			actTaskService.complete(recpFlow.getAct().getTaskId(), recpFlow.getAct().getProcInsId(), recpFlow.getAct().getComment(), vars);
		}
		//判断流程是否退回并更新报销状态
		if("no".equals(flag)){
			recpFlow.setRecpStatus(Constant.expense_back); //报销状态被退回
			recpFlowDao.updateRecpStatus(recpFlow);
		}
		
		//判断流程是否结束并更新报销状态
		Act e = new Act();
		e.setProcInsId(recpFlow.getAct().getProcInsId()); //流程实例ID
		e = actTaskService.queryThisRunTaskId(e);
		if(e == null){
			//流程已结束
			recpFlow.setFlowFinishTime(new Date());
			recpFlow.setRecpStatus(Constant.expense_approve_end); //报销状态审批结束
			recpFlowDao.updateRecpStatusAndFlowFinshTime(recpFlow);
		}
	}
	
	/**
	 * 任务撤销
	 * @param recpFlow
	 */
	@Transactional(readOnly = false)
	public void repealTask(RecpFlow recpFlow,User user){
		
		/********判断当前节点的下一个节点任务是否已经完成*********/
		
		/**********流程跳转，发起撤销start********/
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "1");
		
		//查询被撤销的节点信息
		Act act = new Act();
		act.setProcInsId(recpFlow.getAct().getProcInsId()); //流程实例ID
		act = actTaskService.queryThisRunTaskId(act); //查询当前任务信息
		
		//查询发起撤销节点key
		Act targetAct = new Act();
		if("0".equals(recpFlow.getAct().getTaskId())){
			targetAct.setTaskDefKey("modify");
		}else{
			targetAct.setTaskId(recpFlow.getAct().getTaskId());
			targetAct.setProcInsId(recpFlow.getAct().getProcInsId());
			targetAct = actTaskService.queryHisTask(targetAct);
		}
		
		String targetTaskDefinitionKey = targetAct.getTaskDefKey(); //发起撤销的节点key
		actTaskService.jumpTaskAndAddComent(recpFlow.getAct().getProcInsId(), act.getTaskId(),
				"[流程撤回]当前审核任务被"+user.getName()+"撤回", user.getLoginName(), targetTaskDefinitionKey, vars);
		/**********流程跳转，发起撤销end********/
	}
	
	
	/**
	 * 删除任务
	 * @param recpFlow
	 */
	@Transactional(readOnly = false)
	public void repealApply(RecpFlow recpFlow){
		if(Constant.expense_save.equals(recpFlow.getRecpStatus())){ //保存的单据进行物理删除
			DemandManagementBudget demandManagementBudget = new DemandManagementBudget();
			demandManagementBudget.setProcCode(recpFlow.getProcCode());
			demandManagementBudgetService.deleteForProcCode(demandManagementBudget); //删除招待申请明细信息
			recpFlowDao.deleteRecpFlowInfo(recpFlow); //删除报销主表信息
		}else{
			if(StringUtils.isNotBlank(recpFlow.getProcInsId())){
				//删除任务
				actTaskService.deleteRunTask(recpFlow.getProcInsId()); //删除运行任务
				actTaskService.deleteHisTask(recpFlow.getProcInsId()); //删除历史任务
			}
			//删除单据
			recpFlowDao.delete(recpFlow);
			//修改单据状态为已删除
			recpFlow.setRecpStatus(Constant.expense_delete); //报销状态被删除
			recpFlowDao.updateRecpStatus(recpFlow);
		}
	}
	
	/**
	 * 根据流程实例ID查询招待申请信息
	 * @param procInsId
	 * @return
	 */
	public RecpFlow getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}
	
	/**
	 * 根据流程实例ID查询招待申请信息
	 * @param procInsId
	 * @return
	 */
	public RecpFlow getByProcCode(String procCode){
		return dao.getByProcCode(procCode);
	}

    public List<String> queryLoginNames(String[] employees) {
		return dao.queryLoginNames(employees);
    }
}