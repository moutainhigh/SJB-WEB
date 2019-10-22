/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.act.service;

import java.io.InputStream;
import java.util.*;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.RuntimeServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityBehavior;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.act.cmd.AddCommentCmd;
import com.sijibao.oa.modules.act.dao.ActDao;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.entity.TaskInfoEntity;
import com.sijibao.oa.modules.act.service.cmd.CompletedTaskCmd;
import com.sijibao.oa.modules.act.service.cmd.CreateAndTakeTransitionCmd;
import com.sijibao.oa.modules.act.service.cmd.JumpTaskCmd;
import com.sijibao.oa.modules.act.service.cmd.JumpTaskListCmd;
import com.sijibao.oa.modules.act.service.creator.ChainedActivitiesCreator;
import com.sijibao.oa.modules.act.service.creator.MultiInstanceActivityCreator;
import com.sijibao.oa.modules.act.service.creator.RuntimeActivityDefinitionEntityIntepreter;
import com.sijibao.oa.modules.act.service.creator.SimpleRuntimeActivityDefinitionEntity;
import com.sijibao.oa.modules.act.utils.ActUtils;
import com.sijibao.oa.modules.act.utils.ProcessDefCache;
import com.sijibao.oa.modules.act.utils.ProcessDefUtils;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.DemandAssign;
import com.sijibao.oa.modules.oa.service.DemandAssignService;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 流程定义相关Service
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service
@Transactional(readOnly = true)
public class ActTaskService extends BaseService {

	@Autowired
	private ActDao actDao;

	@Autowired
	private ProcessEngineFactoryBean processEngineFactory;
	
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private DemandAssignService demandAssignService;
	/**
	 * 获取待办列表
	 * @param
	 * @return
	 */
	public List<Act> todoList(Act act, String userId){
		//String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		
		List<Act> result = new ArrayList<Act>();
		
		// =============== 已经签收的任务  ===============
		TaskQuery todoTaskQuery = taskService.createTaskQuery().taskAssignee(userId).active()
				.includeProcessVariables().orderByTaskCreateTime().desc();
		
		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			todoTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			todoTaskQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			todoTaskQuery.taskCreatedBefore(act.getEndDate());
		}
		
		// 查询列表
		List<Task> todoList = todoTaskQuery.list();
		for (Task task : todoList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
//			e.setTaskVars(task.getTaskLocalVariables());
//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("todo");
			result.add(e);
		}
		
		// =============== 等待签收的任务  ===============
		TaskQuery toClaimQuery = taskService.createTaskQuery().taskCandidateUser(userId)
				.includeProcessVariables().active().orderByTaskCreateTime().desc();
		
		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			toClaimQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			toClaimQuery.taskCreatedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			toClaimQuery.taskCreatedBefore(act.getEndDate());
		}
		
		// 查询列表
		List<Task> toClaimList = toClaimQuery.list();
		for (Task task : toClaimList) {
			Act e = new Act();
			e.setTask(task);
			e.setVars(task.getProcessVariables());
//			e.setTaskVars(task.getTaskLocalVariables());
//			System.out.println(task.getId()+"  =  "+task.getProcessVariables() + "  ========== " + task.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(task.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("claim");
			result.add(e);
		}
		return result;
	}
	
	/**
	 * 查询待办任务列表
	 * @param page
	 * @param
	 * @return
	 */
	public Page<TaskInfoEntity> queryTodoList(Page<TaskInfoEntity> page,TaskInfoEntity taskInfoEntity){
		taskInfoEntity.setPage(page);
		Page<TaskInfoEntity> resultPage = page.setList((List<TaskInfoEntity>) actDao.queryTodoList(taskInfoEntity));
		return resultPage;
	}
	
	/**
	 * 查询已办任务列表
	 * @param page
	 * @param
	 * @return
	 */
	public Page<TaskInfoEntity> queryDoneList(Page<TaskInfoEntity> page,TaskInfoEntity taskInfoEntity){
		taskInfoEntity.setPage(page);
		Page<TaskInfoEntity> resultPage = page.setList((List<TaskInfoEntity>) actDao.queryDoneList(taskInfoEntity));
		for(TaskInfoEntity t:resultPage.getList()){
			t.setStatus("finish");
			Act a = new Act();
			a.setProcInsId(t.getProcessInstanceId());
			List<Act> actList = this.queryThisRunTaskIdList(a);
			if(actList == null || actList.size() == 0){
				t.setTaskName("已完结");
			}else{
				a = actList.get(0);
				String userName = "";
				User user = new User();
				user.setLoginName(a.getAssignee());
				user = userDao.getByLoginName(user);
				if(user != null && StringUtils.isNotBlank(user.getName())){
					userName = user.getName();
				}
				t.setTaskName(userName+a.getTaskName());
			}
		}
		return resultPage;
	}
	
	/**
	 * 获取已办任务
	 * @param page
	 * @param
	 * @return
	 */
	public Page<Act> historicList(Page<Act> page, Act act, String userId){
		//String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());

		HistoricTaskInstanceQuery histTaskQuery = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished()
				.includeProcessVariables().orderByHistoricTaskInstanceEndTime().desc();

		// 设置查询条件
		if (StringUtils.isNotBlank(act.getProcDefKey())){
			histTaskQuery.processDefinitionKey(act.getProcDefKey());
		}
		if (act.getBeginDate() != null){
			histTaskQuery.taskCompletedAfter(act.getBeginDate());
		}
		if (act.getEndDate() != null){
			histTaskQuery.taskCompletedBefore(act.getEndDate());
		}

		// 查询总数
		page.setCount(histTaskQuery.count());

		// 查询列表
		List<HistoricTaskInstance> histList = histTaskQuery.listPage(page.getFirstResult(), page.getMaxResults());
		//处理分页问题
		List<Act> actList= Lists.newArrayList();
		for (HistoricTaskInstance histTask : histList) {
			Act e = new Act();
			e.setHistTask(histTask);
			e.setVars(histTask.getProcessVariables());
//			e.setTaskVars(histTask.getTaskLocalVariables());
//			System.out.println(histTask.getId()+"  =  "+histTask.getProcessVariables() + "  ========== " + histTask.getTaskLocalVariables());
			e.setProcDef(ProcessDefCache.get(histTask.getProcessDefinitionId()));
//			e.setProcIns(runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult());
//			e.setProcExecUrl(ActUtils.getProcExeUrl(task.getProcessDefinitionId()));
			e.setStatus("finish");
			if(histTask.getProcessVariables().get("businessId") != null){
				e.setBusinessId(histTask.getProcessVariables().get("businessId").toString());
			}
			e.setProcInsId(histTask.getProcessInstanceId());
			Act a = this.queryThisRunTaskId(e);
			if(a == null){
				e.setTaskName("已完结");
			}else{
				String userName = "";
				User user = new User();
				user.setLoginName(a.getAssignee());
				user = userDao.getByLoginName(user);
				if(user != null && StringUtils.isNotBlank(user.getName())){
					userName = user.getName();
				}
				e.setTaskName(userName+histTask.getName());
			}
			actList.add(e);
			//page.getList().add(e);
		}
		page.setList(actList);
		return page;
	}
	
	/**
	 * 获取流转历史列表
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	public List<Act> histoicFlowList(String procInsId, String startAct, String endAct){
		List<Act> actList = Lists.newArrayList();
		List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
				.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
		
		boolean start = false;
		Map<String, Integer> actMap = Maps.newHashMap();
		for (int i=0; i<list.size(); i++){
			
			HistoricActivityInstance histIns = list.get(i);
			
			// 过滤开始节点前的节点
			if (StringUtils.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())){
				start = true;
			}
			if (StringUtils.isNotBlank(startAct) && !start){
				continue;
			}
			
			// 只显示开始节点和结束节点，并且执行人不为空的任务
			if (StringUtils.isNotBlank(histIns.getAssignee())
					 || "startEvent".equals(histIns.getActivityType())){
//					 || "endEvent".equals(histIns.getActivityType())){
				
				// 给节点增加一个序号
				Integer actNum = actMap.get(histIns.getActivityId());
				if (actNum == null){
					actMap.put(histIns.getActivityId(), actMap.size());
				}
				if(actMap.size() == 2 && "modify".equals(histIns.getActivityId())){
					continue;
				}
				
				Act e = new Act();
				e.setHistIns(histIns);
				// 获取流程发起人名称
				if ("startEvent".equals(histIns.getActivityType())){
					List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
					if (il.size() > 0){
						if (StringUtils.isNotBlank(il.get(0).getStartUserId())){
							User user = UserUtils.getByLoginName(il.get(0).getStartUserId());
							if (user != null){
								e.setAssignee(histIns.getAssignee());
								e.setAssigneeName(user.getName());
							}
						}
					}
				}
				// 获取任务执行人名称
				if (StringUtils.isNotEmpty(histIns.getAssignee())){
					User user = UserUtils.getByLoginName(histIns.getAssignee());
					if (user != null){
						e.setAssignee(histIns.getAssignee());
						e.setAssigneeName(user.getName());
					}
				}
				// 获取意见评论内容
				if (StringUtils.isNotBlank(histIns.getTaskId())){
					List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
					if (commentList.size()>0){
						e.setComment(commentList.get(0).getFullMessage());
					}
				}
				actList.add(e);
			}
			
			// 过滤结束节点后的节点
			if (StringUtils.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())){
				boolean bl = false;
				Integer actNum = actMap.get(histIns.getActivityId());
				// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
				for (int j=i+1; j<list.size(); j++){
					HistoricActivityInstance hi = list.get(j);
					Integer actNumA = actMap.get(hi.getActivityId());
					if ((actNumA != null && actNumA < actNum) || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())){
						bl = true;
					}
				}
				if (!bl){
					break;
				}
			}
		}
		return actList;
	}
	
	/**
	 * 获取流转历史列表(子流程使用)
	 * @param procInsId 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	public List<Act> histoicFlowListForChildFlow(String procInsId,String executionId,String startAct, String endAct,String taskDefKey){
		List<Act> actList = Lists.newArrayList();
		List<HistoricActivityInstance> list = new ArrayList<HistoricActivityInstance>();
		if(StringUtils.isNotBlank(executionId) && taskDefKey.startsWith(Constant.DEMAND_ASSIGNEE_NODE) && !Constant.DEMAIND_CHILD_NODE3.equals(taskDefKey)){
			list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId).executionId(executionId)
					.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
		}else{
			list = historyService.createHistoricActivityInstanceQuery().processInstanceId(procInsId)
					.orderByHistoricActivityInstanceStartTime().asc().orderByHistoricActivityInstanceEndTime().asc().list();
		}
		
		//查询流程分配信息
		List<DemandAssign> resultList = new ArrayList<DemandAssign>();
		DemandAssign demandAssign = new DemandAssign();
		demandAssign.setProcInsId(procInsId);
		demandAssign.setTargetAssign(UserUtils.getUser().getLoginName());
		resultList = demandAssignService.querySourceAssignByTargetAssignList(demandAssign,resultList);
		
		boolean start = false;
		Map<String, Integer> actMap = Maps.newHashMap();
		int count = 0;
		for (int i=0; i<list.size(); i++){
			HistoricActivityInstance histIns = list.get(i);
			if("exclusiveGateway".equals(histIns.getActivityType())){
				continue;
			}
			//过滤其他子流程的审批历史
			int childCount = 0;
			if(resultList != null && resultList.size() > 0){
				for(DemandAssign d:resultList){
					if(d != null && StringUtils.isNotBlank(d.getTargetTaskName()) && d.getTargetTaskName().equals(histIns.getActivityId()) &&
							!d.getTargetAssign().equals(histIns.getAssignee())){
						childCount = 1;
					}
				}
			}
			if(childCount > 0){
				continue;
			}
			
			//如果流程执行ID不为空，则过滤调开始节点
			if(StringUtils.isNotBlank(executionId) && taskDefKey.startsWith(Constant.DEMAND_ASSIGNEE_NODE)){
				if("startEvent".equals(histIns.getActivityType())) continue;
			}
			
			// 过滤开始节点前的节点
			if (StringUtils.isNotBlank(startAct) && startAct.equals(histIns.getActivityId())){
				start = true;
			}
			if (StringUtils.isNotBlank(startAct) && !start){
				continue;
			}
			
			// 只显示开始节点和结束节点，并且执行人不为空的任务
			if (StringUtils.isNotBlank(histIns.getAssignee())
					 || "startEvent".equals(histIns.getActivityType())){
//					 || "endEvent".equals(histIns.getActivityType())){
				
				// 给节点增加一个序号
				Integer actNum = actMap.get(histIns.getActivityId());
				if (actNum == null){
					actMap.put(histIns.getActivityId(), actMap.size());
				}
				if(actMap.size() == 2 && "modify".equals(histIns.getActivityId())){
					continue;
				}
				
				Act e = new Act();
				if("startEvent".equals(histIns.getActivityType()) && count > 0){
						continue;
				}else{
					e.setHistIns(histIns);
				}
				e.setTaskDefKey(histIns.getActivityId());
				// 获取流程发起人名称
				if ("startEvent".equals(histIns.getActivityType())){
					count++;
					List<HistoricProcessInstance> il = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInsId).orderByProcessInstanceStartTime().asc().list();
//					List<HistoricIdentityLink> il = historyService.getHistoricIdentityLinksForProcessInstance(procInsId);
					if (il.size() > 0){
						if (StringUtils.isNotBlank(il.get(0).getStartUserId())){
							User user = UserUtils.getByLoginName(il.get(0).getStartUserId());
							if (user != null){
								e.setAssignee(histIns.getAssignee());
								e.setAssigneeName(user.getName());
							}
						}
					}
				}
				// 获取任务执行人名称
				if (StringUtils.isNotEmpty(histIns.getAssignee())){
					User user = UserUtils.getByLoginName(histIns.getAssignee());
					if (user != null){
						e.setAssignee(histIns.getAssignee());
						e.setAssigneeName(user.getName());
					}
				}
				// 获取意见评论内容
				if (StringUtils.isNotBlank(histIns.getTaskId())){
					List<Comment> commentList = taskService.getTaskComments(histIns.getTaskId());
					if (commentList.size()>0){
						e.setComment(commentList.get(0).getFullMessage());
					}
				}
				actList.add(e);
			}
			
			// 过滤结束节点后的节点
			if (StringUtils.isNotBlank(endAct) && endAct.equals(histIns.getActivityId())){
				boolean bl = false;
				Integer actNum = actMap.get(histIns.getActivityId());
				// 该活动节点，后续节点是否在结束节点之前，在后续节点中是否存在
				for (int j=i+1; j<list.size(); j++){
					HistoricActivityInstance hi = list.get(j);
					Integer actNumA = actMap.get(hi.getActivityId());
					if ((actNumA != null && actNumA < actNum) || StringUtils.equals(hi.getActivityId(), histIns.getActivityId())){
						bl = true;
					}
				}
				if (!bl){
					break;
				}
			}
		}
		return actList;
	}
	
	
	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	public Page<Object[]> processList(Page<Object[]> page, String category) {
		/*
		 * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
		 */
	    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
	    		.latestVersion().active().orderByProcessDefinitionKey().asc();
	    
	    if (StringUtils.isNotEmpty(category)){
	    	processDefinitionQuery.processDefinitionCategory(category);
		}
	    
	    page.setCount(processDefinitionQuery.count());
	    
	    List<ProcessDefinition> processDefinitionList = processDefinitionQuery.listPage(page.getFirstResult(), page.getMaxResults());
	    for (ProcessDefinition processDefinition : processDefinitionList) {
	      String deploymentId = processDefinition.getDeploymentId();
	      Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
	      page.getList().add(new Object[]{processDefinition, deployment});
	    }
		return page;
	}
	
	/**
	 * 获取流程表单（首先获取任务节点表单KEY，如果没有则取流程开始节点表单KEY）
	 * @return
	 */
	public String getFormKey(String procDefId, String taskDefKey){
		String formKey = "";
		if (StringUtils.isNotBlank(procDefId)){
			if (StringUtils.isNotBlank(taskDefKey)){
				try{
					formKey = formService.getTaskFormKey(procDefId, taskDefKey);
				}catch (Exception e) {
					formKey = "";
				}
			}
			if (StringUtils.isBlank(formKey)){
				formKey = formService.getStartFormKey(procDefId);
			}
			if (StringUtils.isBlank(formKey)){
				formKey = "/404";
			}
		}
		logger.debug("getFormKey: {}", formKey);
		return formKey;
	}
	
	/**
	 * 获取流程实例对象
	 * @param procInsId
	 * @return
	 */
	@Transactional(readOnly = false)
	public ProcessInstance getProcIns(String procInsId) {
		return runtimeService.createProcessInstanceQuery().processInstanceId(procInsId).singleResult();
	}

	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId) {
		return startProcess(procDefKey, businessTable, businessId, "");
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title) {
		Map<String, Object> vars = Maps.newHashMap();
		return startProcess(procDefKey, businessTable, businessId, title, vars);
	}
	/**
	 * 启动流程(微信)
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcessForWechat(String procDefKey, String businessTable, String businessId, String title,User user) {
		Map<String, Object> vars = Maps.newHashMap();
		return startProcessForWechat(procDefKey, businessTable, businessId, title, vars,user);
	}
	
	/**
	 * 启动流程(微信)
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcessForResourcesHandle(String procDefKey, String businessTable, String businessId, String title,User user,Map<String, Object> vars) {
		return startProcessForWechat(procDefKey, businessTable, businessId, title, vars,user);
	}
	
	/**
	 * 启动流程
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcess(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId())
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		//设置当前申请人部门ID变量
		if(StringUtils.isNotBlank(UserUtils.getUser().getOffice().getId())){
			vars.put("officeId",UserUtils.getUser().getOffice().getId());
		}
		
		//设置业务ID
		if (StringUtils.isNotBlank(businessId)){
			vars.put("businessId", businessId);
		}
		//添加多级子流程实例变量
		if(vars.get("employeeList") == null){
			List<String> employeeList = new ArrayList<String>();
			vars.put("employeeList",employeeList);
		}
		
		String demandAssignee = "";
		vars.put("demandAssignee", demandAssignee);
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+":"+businessId, vars);
		
		// 更新业务表流程实例ID
		Act act = new Act();
		act.setBusinessTable(businessTable);// 业务表名
		act.setBusinessId(businessId);	// 业务表ID
		act.setProcInsId(procIns.getId());
		actDao.updateProcInsIdByBusinessId(act);
		return act.getProcInsId();
	}
	
	/**
	 * 启动流程(微信)
	 * @param procDefKey 流程定义KEY
	 * @param businessTable 业务表表名
	 * @param businessId	业务表编号
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars			流程变量
	 * @return 流程实例ID
	 */
	@Transactional(readOnly = false)
	public String startProcessForWechat(String procDefKey, String businessTable, String businessId, String title, Map<String, Object> vars,User user) {
		String userId = user.getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId())
		
		// 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
		identityService.setAuthenticatedUserId(userId);
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		//设置当前申请人部门ID变量
		if(StringUtils.isNotBlank(user.getOffice().getId())){
			vars.put("officeId",user.getOffice().getId());
		}
		
		//设置业务ID
		if (StringUtils.isNotBlank(businessId)){
			vars.put("businessId", businessId);
		}
		
		//添加多级子流程实例变量
		List<String> employeeList = new ArrayList<String>();
		vars.put("employeeList",employeeList);
		
		String demandAssignee = "";
		vars.put("demandAssignee", demandAssignee);
		
		
		// 启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(procDefKey, businessTable+":"+businessId, vars);
		
		// 更新业务表流程实例ID
		Act act = new Act();
		act.setBusinessTable(businessTable);// 业务表名
		act.setBusinessId(businessId);	// 业务表ID
		act.setProcInsId(procIns.getId());
		actDao.updateProcInsIdByBusinessId(act);
		return act.getProcInsId();
	}

	/**
	 * 获取任务
	 * @param taskId 任务ID
	 */
	public Task getTask(String taskId){
		return taskService.createTaskQuery().taskId(taskId).singleResult();
	}
	
	/**
	 * 删除任务
	 * @param taskId 任务ID
	 * @param deleteReason 删除原因
	 */
	@Transactional(readOnly = false)
	public void deleteTask(String taskId, String deleteReason){
		taskService.deleteTask(taskId, deleteReason);
	}
	
	/**
	 * 删除运行中任务
	 * @param
	 * @param
	 * @param procInsId 流程实例ID
	 */
	@Transactional(readOnly = false)
	public void deleteRunTask(String procInsId){
		Act act = new Act();
		act.setProcInsId(procInsId); //流程实例ID
		actDao.deleteRunTask(act);
	}


	/**
	 * 删除历史任务
	 * @param
	 * @param
	 * @param procInsId 流程实例ID
	 */
	@Transactional(readOnly = false)
	public void deleteHisTask(String procInsId){
		Act act = new Act();
		act.setProcInsId(procInsId); //流程实例ID
		actDao.deleteHisTask(act);
	}
	
	/**
	 * 签收任务
	 * @param taskId 任务ID
	 * @param userId 签收用户ID（用户登录名）
	 */
	@Transactional(readOnly = false)
	public void claim(String taskId, String userId){
		taskService.claim(taskId, userId);
	}
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, Map<String, Object> vars){
		complete(taskId, procInsId, comment, "", vars);
	}
	
	/**
	 * 提交任务, 并保存意见
	 * @param taskId 任务ID
	 * @param procInsId 流程实例ID，如果为空，则不保存任务提交意见
	 * @param comment 任务提交意见的内容
	 * @param title			流程标题，显示在待办任务标题
	 * @param vars 任务变量
	 */
	@Transactional(readOnly = false)
	public void complete(String taskId, String procInsId, String comment, String title, Map<String, Object> vars){
		// 添加意见
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			taskService.addComment(taskId, procInsId, comment);
		}
		
		// 设置流程变量
		if (vars == null){
			vars = Maps.newHashMap();
		}
		
		// 设置流程标题
		if (StringUtils.isNotBlank(title)){
			vars.put("title", title);
		}
		
		// 提交任务
		taskService.complete(taskId, vars);
	}

//	/**
//	 * 完成第一个任务
//	 * @param procInsId
//	 */
//	@Transactional(readOnly = false)
//	public void completeFirstTask(String procInsId){
//		completeFirstTask(procInsId, null, null, null);
//	}
//
//	/**
//	 * 完成第一个任务
//	 * @param procInsId
//	 * @param comment
//	 * @param title
//	 * @param vars
//	 */
//	@Transactional(readOnly = false)
//	public void completeFirstTask(String procInsId, String comment, String title, Map<String, Object> vars){
//		String userId = UserUtils.getUser().getLoginName();
//		Task task = taskService.createTaskQuery().taskAssignee(userId).processInstanceId(procInsId).active().singleResult();
//		if (task != null){
//			complete(task.getId(), procInsId, comment, title, vars);
//		}
//	}
	
	/**
	 * 自动跳过相同审批人员
	 * @param procInsId
	 * @param title
	 * @param vars
	 * @param
	 */
	public String completeAutoIdenPersonel(String procInsId,String title,Map<String,Object> vars,String applyPerCode){
		String comment = "[同意]相同审批人员，自动审批跳过";
		Act act = new Act();
		act.setProcInsId(procInsId);
//		Task task = taskService.createTaskQuery().taskAssignee(applyPerCode).processInstanceId(procInsId).active().singleResult();

		Task task = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
		if(task != null){
			if(StringUtils.equals(task.getAssignee(), applyPerCode)){ //如果执行者与申请人相同，则自动跳过
				complete(task.getId(), procInsId, comment, vars);
				this.completeAutoIdenPersonel(procInsId, title, vars, applyPerCode);	
			}
		}else{
			Task taskRun = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
			if(taskRun != null){
				return taskRun.getAssignee();
			}
		}
		return applyPerCode;
	}
	
	/**
	 * 审批人员为空，自动跳过
	 * @param procInsId
	 * @param title
	 * @param vars
	 * @param
	 */
	public String completeAutoEmptyPersonsel(String procInsId,String title,Map<String,Object> vars){
		String comment = "[同意]审批人员为空，自动审批跳过";
		Act act = new Act();
		act.setProcInsId(procInsId);
//		Task task = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(procInsId).active().list();
		if(taskList != null){
			if(taskList.size() == 1) {
				Task task = taskList.get(0);
				if (StringUtils.isBlank(task.getAssignee())) { //如果执行者与申请人相同，则自动跳过
					complete(task.getId(), procInsId, comment, vars);
					this.completeAutoEmptyPersonsel(procInsId, title, vars);
				}
			}
		}else{
//			Task taskRun = taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
			List<Task> taskList2 = taskService.createTaskQuery().processInstanceId(procInsId).active().list();
			if(taskList2 != null){
				return taskList2.get(0).getAssignee();
			}
		}
		return null;
	}


	/**
	 * 非最后一个节点
	 * 审批人员为空，自动审批跳过
	 * 审批人与申请人相同，自动审批跳过
	 * 连续相同审批人员，自动审批跳过
	 *
	 * @param procInsId 流程实例id
	 * @param title 流程标题
	 * @param vars 流程变量
	 */
	public void completeAutoEmptyAndIdenPersonel(String procInsId, String title, Map<String,Object> vars, String applyPerCode){
		Act act = new Act();
		act.setProcInsId(procInsId);
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(procInsId).active().list();

		if(taskList != null){
			if(taskList.size() == 1) {
				Task task = taskList.get(0);
				String lastAssignee = actDao.queryTheLastAssignee(procInsId);

				if(task != null && !task.getTaskDefinitionKey().contains("last")){//禁止最后一个节点自动跳过
                    if (StringUtils.isBlank(task.getAssignee())) { //如果审批人员为空，自动跳过
                        String comment = "[同意]审批人员为空，自动审批跳过";
                        complete(task.getId(), procInsId, comment, vars);
                        this.completeAutoEmptyAndIdenPersonel(procInsId, title, vars, applyPerCode);
                    } else if(StringUtils.equals(task.getAssignee(), applyPerCode)){ //如果执行者与申请人相同，则自动跳过
						String comment = "[同意]审批人与申请人相同，自动审批跳过";
						complete(task.getId(), procInsId, comment, vars);
						this.completeAutoEmptyAndIdenPersonel(procInsId, title, vars, applyPerCode);
					} else if(StringUtils.isNotBlank(lastAssignee) && StringUtils.equals(task.getAssignee(), lastAssignee)) { //连续两个审批人为同一个审批人时，第二个审批节点跳过
						String comment = "[同意]相同审批人员，自动审批跳过";
						complete(task.getId(), procInsId, comment, vars);
						this.completeAutoEmptyAndIdenPersonel(procInsId, title, vars, applyPerCode);
					}
                }
			}
		}
	}
	
//	/**
//	 * 委派任务
//	 * @param taskId 任务ID
//	 * @param userId 被委派人
//	 */
//	public void delegateTask(String taskId, String userId){
//		taskService.delegateTask(taskId, userId);
//	}
//	
//	/**
//	 * 被委派人完成任务
//	 * @param taskId 任务ID
//	 */
//	public void resolveTask(String taskId){
//		taskService.resolveTask(taskId);
//	}
//	
//	/**
//	 * 回退任务
//	 * @param taskId
//	 */
//	public void backTask(String taskId){
//		taskService.
//	}
	
	/**
	 * 添加任务意见
	 */
	public void addTaskComment(String taskId, String procInsId, String comment){
		taskService.addComment(taskId, procInsId, comment);
	}
	
	//////////////////  回退、前进、跳转、前加签、后加签、分裂 移植  https://github.com/bluejoe2008/openwebflow  ////////////////////////////////////////////////// 

	/**
	 * 任务后退一步
	 */
	public void taskBack(String procInsId, Map<String, Object> variables) {
		taskBack(getCurrentTask(procInsId), variables);
	}

	/**
	 * 任务后退至指定活动
	 */
	public void taskBack(TaskEntity currentTaskEntity, Map<String, Object> variables) {
		ActivityImpl activity = (ActivityImpl) ProcessDefUtils
				.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
				.getIncomingTransitions().get(0).getSource();
		jumpTask(currentTaskEntity, activity, variables);
	}

	/**
	 * 任务前进一步
	 */
	public void taskForward(String procInsId, Map<String, Object> variables) {
		taskForward(getCurrentTask(procInsId), variables);
	}

	/**
	 * 任务前进至指定活动
	 */
	public void taskForward(TaskEntity currentTaskEntity, Map<String, Object> variables) {
		ActivityImpl activity = (ActivityImpl) ProcessDefUtils
				.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
				.getOutgoingTransitions().get(0).getDestination();

		jumpTask(currentTaskEntity, activity, variables);
	}
	
	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	public void jumpTask(String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables) {
		jumpTask(getCurrentTask(procInsId), targetTaskDefinitionKey, variables);
	}
	
	
	/**
	 * 任务跳转（包含审批意见添加）
	 */
	public void jumpTaskAndAddComent(String procInsId,String taskId,String comment, String userId,String targetTaskDefinitionKey, Map<String, Object> variables) {
		// 添加意见
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			 managementService.executeCommand(new AddCommentCmd(taskId, procInsId, comment, userId));
		}
		jumpTask(getCurrentTask(procInsId), targetTaskDefinitionKey, variables);
	}

	
	/**
	 * 任务跳转（子流程使用）
	 */
	public void jumpTaskAndAddComentForChild(String procInsId,String taskId,String comment, String userId,String targetTaskDefinitionKey, Map<String, Object> variables) {
		// 添加意见
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			 managementService.executeCommand(new AddCommentCmd(taskId, procInsId, comment, userId));
		}
		jumpTask(getCurrentTaskForChild(procInsId,taskId), targetTaskDefinitionKey, variables);
	}
	
	/**
	 * 任务跳转多任务实例（子流程使用）
	 */
	public void jumpTaskAndAddComentForChildList(String procInsId,List<Act> actList,String comment, String userId,String targetTaskDefinitionKey, Map<String, Object> variables) {
		// 添加意见
		if (StringUtils.isNotBlank(procInsId) && StringUtils.isNotBlank(comment)){
			for(Act a:actList){
				managementService.executeCommand(new AddCommentCmd(a.getTaskId(), procInsId, comment, userId));
			}
		}
		jumpTaskForList(getCurrentTaskForList(actList,procInsId), targetTaskDefinitionKey, variables);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 */
	public void jumpTask(String procInsId, String currentTaskId, String targetTaskDefinitionKey, Map<String, Object> variables) {
		jumpTask(getTaskEntity(currentTaskId), targetTaskDefinitionKey, variables);
	}
	
	/**
	 * 只完成当前任务，不向后跳转
	 */
	public void completedTaskAndNotJump(String currentTaskId,Map<String, Object> variables,User user,String message){
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new CompletedTaskCmd(getTaskEntity(currentTaskId), variables, user, message));
	}
	
	
	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * @param currentTaskEntity 当前任务节点
	 * @param targetTaskDefinitionKey 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	public void jumpTask(TaskEntity currentTaskEntity, String targetTaskDefinitionKey, Map<String, Object> variables) {
		ActivityImpl activity = ProcessDefUtils.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(),
				targetTaskDefinitionKey);
		jumpTask(currentTaskEntity, activity, variables);
	}
	
	public void jumpTaskForList(List<TaskEntity> currentTaskEntityList, String targetTaskDefinitionKey, Map<String, Object> variables) {
		ActivityImpl activity = ProcessDefUtils.getActivity(processEngine, currentTaskEntityList.get(0).getProcessDefinitionId(),
				targetTaskDefinitionKey);
		jumpTaskForList(currentTaskEntityList, activity, variables);
	}

	/**
	 * 跳转（包括回退和向前）至指定活动节点
	 * @param currentTaskEntity 当前任务节点
	 * @param targetActivity 目标任务节点（在模型定义里面的节点名称）
	 * @throws Exception
	 */
	private void jumpTask(TaskEntity currentTaskEntity, ActivityImpl targetActivity, Map<String, Object> variables) {
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new JumpTaskCmd(currentTaskEntity, targetActivity, variables));
	}
	
	private void jumpTaskForList(List<TaskEntity> taskEntityList, ActivityImpl targetActivity, Map<String, Object> variables) {
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new JumpTaskListCmd(taskEntityList, targetActivity, variables,taskEntityList.get(0)));
	}
	
	/**
	 * 后加签
	 */
	@SuppressWarnings("unchecked")
	public ActivityImpl[] insertTasksAfter(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
		List<String> assigneeList = new ArrayList<String>();
		assigneeList.add(Authentication.getAuthenticatedUserId());
		assigneeList.addAll(CollectionUtils.arrayToList(assignees));
		String[] newAssignees = assigneeList.toArray(new String[0]);
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		ActivityImpl prototypeActivity = ProcessDefUtils.getActivity(processEngine, processDefinition.getId(), targetTaskDefinitionKey);
		return cloneAndMakeChain(processDefinition, procInsId, targetTaskDefinitionKey, prototypeActivity.getOutgoingTransitions().get(0).getDestination().getId(), variables, newAssignees);
	}

	/**
	 * 前加签
	 */
	public ActivityImpl[] insertTasksBefore(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignees) {
		ProcessDefinitionEntity procDef = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		return cloneAndMakeChain(procDef, procInsId, targetTaskDefinitionKey, targetTaskDefinitionKey, variables, assignees);
	}

	/**
	 * 分裂某节点为多实例节点
	 */
	public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, String... assignee) {
		return splitTask(procDefId, procInsId, targetTaskDefinitionKey, variables, true, assignee);
	}
	
	/**
	 * 分裂某节点为多实例节点
	 */
	@SuppressWarnings("unchecked")
	public ActivityImpl splitTask(String procDefId, String procInsId, String targetTaskDefinitionKey, Map<String, Object> variables, boolean isSequential, String... assignees) {
		SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
		info.setProcessDefinitionId(procDefId);
		info.setProcessInstanceId(procInsId);

		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);

		radei.setPrototypeActivityId(targetTaskDefinitionKey);
		radei.setAssignees(CollectionUtils.arrayToList(assignees));
		radei.setSequential(isSequential);
		
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(procDefId);
		ActivityImpl clone = new MultiInstanceActivityCreator().createActivities(processEngine, processDefinition, info)[0];

		TaskEntity currentTaskEntity = this.getCurrentTask(procInsId);
		
		CommandExecutor commandExecutor = ((RuntimeServiceImpl) runtimeService).getCommandExecutor();
		commandExecutor.execute(new CreateAndTakeTransitionCmd(currentTaskEntity, clone, variables));

//		recordActivitiesCreation(info);
		return clone;
	}

	private TaskEntity getCurrentTask(String procInsId) {
		return (TaskEntity) taskService.createTaskQuery().processInstanceId(procInsId).active().singleResult();
	}
	
	private List<TaskEntity> getCurrentTaskForList(List<Act> actList, String procInsId) {
		List<TaskEntity> taskEntityList = new ArrayList<TaskEntity>();
		for(Act a:actList){
			taskEntityList.add((TaskEntity)taskService.createTaskQuery().processInstanceId(procInsId).taskId(a.getTaskId()).active().singleResult());
		}
		return  taskEntityList;
	}
	
	private TaskEntity getCurrentTaskForChild(String procInsId, String taskId) {
		
		return (TaskEntity) taskService.createTaskQuery().processInstanceId(procInsId).taskId(taskId).active().singleResult();
	}
	
	private TaskEntity getTaskEntity(String taskId) {
		return (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
	}

	@SuppressWarnings("unchecked")
	private ActivityImpl[] cloneAndMakeChain(ProcessDefinitionEntity procDef, String procInsId, String prototypeActivityId, String nextActivityId, Map<String, Object> variables, String... assignees) {
		SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
		info.setProcessDefinitionId(procDef.getId());
		info.setProcessInstanceId(procInsId);

		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);
		radei.setPrototypeActivityId(prototypeActivityId);
		radei.setAssignees(CollectionUtils.arrayToList(assignees));
		radei.setNextActivityId(nextActivityId);

		ActivityImpl[] activities = new ChainedActivitiesCreator().createActivities(processEngine, procDef, info);

		jumpTask(procInsId, activities[0].getId(), variables);
//		recordActivitiesCreation(info);

		return activities;
	}
	
//	private void recordActivitiesCreation(SimpleRuntimeActivityDefinitionEntity info) {
//		info.serializeProperties();
//		_activitiesCreationStore.save(info);
//	}
	
	//////////////////////////////////////////////////////////////////// 
	

//	private void recordActivitiesCreation(SimpleRuntimeActivityDefinitionEntity info) throws Exception {
//		info.serializeProperties();
//		_activitiesCreationStore.save(info);
//	}
//
//	/**
//	 * 分裂某节点为多实例节点
//	 * 
//	 * @param targetTaskDefinitionKey
//	 * @param assignee
//	 * @throws IOException
//	 * @throws IllegalAccessException
//	 * @throws IllegalArgumentException
//	 */
//	public ActivityImpl split(String targetTaskDefinitionKey, boolean isSequential, String... assignees) throws Exception {
//		SimpleRuntimeActivityDefinitionEntity info = new SimpleRuntimeActivityDefinitionEntity();
//		info.setProcessDefinitionId(processDefinition.getId());
//		info.setProcessInstanceId(_processInstanceId);
//
//		RuntimeActivityDefinitionEntityIntepreter radei = new RuntimeActivityDefinitionEntityIntepreter(info);
//
//		radei.setPrototypeActivityId(targetTaskDefinitionKey);
//		radei.setAssignees(CollectionUtils.arrayToList(assignees));
//		radei.setSequential(isSequential);
//
//		ActivityImpl clone = new MultiInstanceActivityCreator().createActivities(_processEngine, processDefinition, info)[0];
//
//		TaskEntity currentTaskEntity = getCurrentTask();
//		executeCommand(new CreateAndTakeTransitionCmd(currentTaskEntity.getExecutionId(), clone));
//		executeCommand(new DeleteRunningTaskCmd(currentTaskEntity));
//
//		recordActivitiesCreation(info);
//		return clone;
//	}
//
//	public ActivityImpl split(String targetTaskDefinitionKey, String... assignee) throws Exception {
//		return split(targetTaskDefinitionKey, true, assignee);
//	}

	////////////////////////////////////////////////////////////////////
	
	/**
	 * 读取带跟踪的图片
	 * @param executionId	环节ID
	 * @return	封装了各种节点信息
	 */
	public InputStream tracePhoto(String processDefinitionId, String executionId) {
//		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
		
		List<String> activeActivityIds = Lists.newArrayList();
		if (runtimeService.createExecutionQuery().executionId(executionId).count() > 0){
			activeActivityIds = runtimeService.getActiveActivityIds(executionId);
		}
		
		// 不使用spring请使用下面的两行代码
		// ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl)ProcessEngines.getDefaultProcessEngine();
		// Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

		// 使用spring注入引擎请使用下面的这行代码
		Context.setProcessEngineConfiguration(processEngineFactory.getProcessEngineConfiguration());
//		return ProcessDiagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
		return processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator()
				.generateDiagram(bpmnModel, "png", activeActivityIds);
	}
	
	/**
	 * 流程跟踪图信息
	 * @param processInstanceId		流程实例ID
	 * @return	封装了各种节点信息
	 */
	public List<Map<String, Object>> traceProcess(String processInstanceId) throws Exception {
		Execution execution = runtimeService.createExecutionQuery().executionId(processInstanceId).singleResult();//执行实例
		Object property = PropertyUtils.getProperty(execution, "activityId");
		String activityId = "";
		if (property != null) {
			activityId = property.toString();
		}
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		List<ActivityImpl> activitiList = processDefinition.getActivities();//获得当前任务的所有节点

		List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
		for (ActivityImpl activity : activitiList) {

			boolean currentActiviti = false;
			String id = activity.getId();

			// 当前节点
			if (id.equals(activityId)) {
				currentActiviti = true;
			}

			Map<String, Object> activityImageInfo = packageSingleActivitiInfo(activity, processInstance, currentActiviti);

			activityInfos.add(activityImageInfo);
		}

		return activityInfos;
	}
	

	/**
	 * 封装输出信息，包括：当前节点的X、Y坐标、变量信息、任务类型、任务描述
	 * @param activity
	 * @param processInstance
	 * @param currentActiviti
	 * @return
	 */
	private Map<String, Object> packageSingleActivitiInfo(ActivityImpl activity, ProcessInstance processInstance,
														  boolean currentActiviti) throws Exception {
		Map<String, Object> vars = new HashMap<String, Object>();
		Map<String, Object> activityInfo = new HashMap<String, Object>();
		activityInfo.put("currentActiviti", currentActiviti);
		setPosition(activity, activityInfo);
		setWidthAndHeight(activity, activityInfo);

		Map<String, Object> properties = activity.getProperties();
		vars.put("节点名称", properties.get("name"));
		vars.put("任务类型", ActUtils.parseToZhType(properties.get("type").toString()));

		ActivityBehavior activityBehavior = activity.getActivityBehavior();
		logger.debug("activityBehavior={}", activityBehavior);
		if (activityBehavior instanceof UserTaskActivityBehavior) {

			Task currentTask = null;

			// 当前节点的task
			if (currentActiviti) {
				currentTask = getCurrentTaskInfo(processInstance);
			}

			// 当前任务的分配角色
			UserTaskActivityBehavior userTaskActivityBehavior = (UserTaskActivityBehavior) activityBehavior;
			TaskDefinition taskDefinition = userTaskActivityBehavior.getTaskDefinition();
			Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
			if (!candidateGroupIdExpressions.isEmpty()) {

				// 任务的处理角色
				setTaskGroup(vars, candidateGroupIdExpressions);

				// 当前处理人
				if (currentTask != null) {
					setCurrentTaskAssignee(vars, currentTask);
				}
			}
		}

		vars.put("节点说明", properties.get("documentation"));

		String description = activity.getProcessDefinition().getDescription();
		vars.put("描述", description);

		logger.debug("trace variables: {}", vars);
		activityInfo.put("vars", vars);
		return activityInfo;
	}

	/**
	 * 设置任务组
	 * @param vars
	 * @param candidateGroupIdExpressions
	 */
	private void setTaskGroup(Map<String, Object> vars, Set<Expression> candidateGroupIdExpressions) {
		String roles = "";
		for (Expression expression : candidateGroupIdExpressions) {
			String expressionText = expression.getExpressionText();
			String roleName = identityService.createGroupQuery().groupId(expressionText).singleResult().getName();
			roles += roleName;
		}
		vars.put("任务所属角色", roles);
	}

	/**
	 * 设置当前处理人信息
	 * @param vars
	 * @param currentTask
	 */
	private void setCurrentTaskAssignee(Map<String, Object> vars, Task currentTask) {
		String assignee = currentTask.getAssignee();
		if (assignee != null) {
			org.activiti.engine.identity.User assigneeUser = identityService.createUserQuery().userId(assignee).singleResult();
			String userInfo = assigneeUser.getFirstName() + " " + assigneeUser.getLastName();
			vars.put("当前处理人", userInfo);
		}
	}

	/**
	 * 获取当前节点信息
	 * @param processInstance
	 * @return
	 */
	private Task getCurrentTaskInfo(ProcessInstance processInstance) {
		Task currentTask = null;
		try {
			String activitiId = (String) PropertyUtils.getProperty(processInstance, "activityId");
			logger.debug("current activity id: {}", activitiId);

			currentTask = taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskDefinitionKey(activitiId)
					.singleResult();
			logger.debug("current task for processInstance: {}", ToStringBuilder.reflectionToString(currentTask));

		} catch (Exception e) {
			logger.error("can not get property activityId from processInstance: {}", processInstance);
		}
		return currentTask;
	}

	/**
	 * 设置宽度、高度属性
	 * @param activity
	 * @param activityInfo
	 */
	private void setWidthAndHeight(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("width", activity.getWidth());
		activityInfo.put("height", activity.getHeight());
	}

	/**
	 * 设置坐标位置
	 * @param activity
	 * @param activityInfo
	 */
	private void setPosition(ActivityImpl activity, Map<String, Object> activityInfo) {
		activityInfo.put("x", activity.getX());
		activityInfo.put("y", activity.getY());
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}
	
	/**
	 * 查询当前任务ID
	 * @param act
	 * @return
	 */
	public Act queryThisRunTaskId(Act act){
		return actDao.queryThisRunTaskId(act)!=null&&actDao.queryThisRunTaskId(act).size()>0?actDao.queryThisRunTaskId(act).get(0):null;
	}


	
	public List<Act> queryThisRunTaskIdList(Act act){
		return actDao.queryThisRunTaskId(act);
	}
	
	public List<Act> queryThisRunTaskIdListForProcInsId(Act act){
		return actDao.queryThisRunTaskIdListForProcInsId(act);
	}
	
	/**
	 * 根据任务ID，流程实例ID查询历史任务的节点key
	 * @param act
	 * @return
	 */
	public Act queryHisTask(Act act){
		return actDao.queryHisTask(act);
	}
	
	/**
	 * 根据流程执行ID和流程节点名称查询对应的执行者信息
	 * @param taskInfoEntity
	 * @return
	 */
	public TaskInfoEntity queryAssigneeForExecutionId(TaskInfoEntity taskInfoEntity){
		return actDao.queryAssigneeForExecutionId(taskInfoEntity);
	}
	
	/**
	 * 查询同一执行ID下的当前审批任务ID
	 * @param act
	 * @return
	 */
	public List<Act> queryThisRunTaskIdForExecutionId(Act act){
		return actDao.queryThisRunTaskIdForExecutionId(act);
	}
	
	
	public ActivityImpl queryActivityImpl(String procInsId){
		TaskEntity currentTaskEntity = this.getCurrentTask(procInsId);
		ActivityImpl activity = (ActivityImpl) ProcessDefUtils
				.getActivity(processEngine, currentTaskEntity.getProcessDefinitionId(), currentTaskEntity.getTaskDefinitionKey())
				.getIncomingTransitions().get(0).getSource();
		return activity;
	}
	
	/**
	 * 资源办理根据当前环节任务ID查询指派环节任务信息
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryChildTaskInfoForParentTaskId(TaskInfoEntity taskInfoEntity){
		return actDao.queryChildTaskInfoForParentTaskId(taskInfoEntity);
	}
	
	/**
	 * 根据任务ID删除运行中任务
	 * @param taskId
	 */
	public void deleteRunTaskForTaskId(String taskId){
		actDao.deleteRunTaskForTaskId(taskId);
	}
	
	/**
	 *  根据流程字典值查询流程key
	 * @param gateGory
	 * @return
	 */
	public TaskInfoEntity queryActModel(String gateGory){
		return actDao.queryActModel(gateGory);
	}
}
