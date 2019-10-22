/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.act.dao;

import java.util.List;
import java.util.Map;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.act.entity.Act;
import com.sijibao.oa.modules.act.entity.TaskInfoEntity;

/**
 * 审批DAO接口
 * @author thinkgem
 * @version 2014-05-16
 */
@MyBatisDao
public interface ActDao extends CrudDao<Act> {

	public int updateProcInsIdByBusinessId(Act act);
	
	/**
	 * 查询当前审批任务ID
	 * @param act
	 * @return
	 */
	public List<Act> queryThisRunTaskId(Act act);
	
	
	public List<Act> queryThisRunTaskIdListForProcInsId(Act act);
	/**
	 * 查询同一执行ID下的当前审批任务ID
	 * @param act
	 * @return
	 */
	public List<Act> queryThisRunTaskIdForExecutionId(Act act);
	
	/**
	 * 删除历史任务
	 * @param act
	 * @return
	 */
	public Act deleteHisTaskInfo(Act act);
	
	/**
	 * 查询待办任务列表
	 * @param act
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoList(TaskInfoEntity taskInfoEntity);
	
	/**
	 * 查询已办任务列表
	 * @param act
	 * @return
	 */
	public List<TaskInfoEntity> queryDoneList(TaskInfoEntity taskInfoEntity);
	
	/**
	 * 查询报销代办任务
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoExpenseFlow(TaskInfoEntity taskInfoEntity);
	
	/**
	 * 查询接待申请代办任务
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoRecpFlow(TaskInfoEntity taskInfoEntity);
	
	/**
	 * 查询市场需求申请代办任务
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoDemandManagementMarketFlow(TaskInfoEntity taskInfoEntity);
	
	/**
	 * 查询实施需求发起代办任务
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoDemandManagementImplementFlow(TaskInfoEntity taskInfoEntity);
	
	
	/**
	 * 查询出差申请代办任务
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoTravelFlow(TaskInfoEntity taskInfoEntity);
	
	
	/**
	 * 查询资源申请代办任务
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoResourcesApplyFlow(TaskInfoEntity taskInfoEntity);
	
	
	/**
	 * 查询资源办理代办任务
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoResourcesHandleFlow(TaskInfoEntity taskInfoEntity);
	
	/**
	 * 查询合同代办任务
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryTodoContractFlow(TaskInfoEntity taskInfoEntity);
	/**
	 * 删除待办任务
	 * @param act
	 */
	public void deleteRunTask(Act act);
	
	/**
	 * 删除历史任务
	 * @param act
	 */
	public void deleteHisTask(Act act);
	
	/**
	 * 根据任务ID，流程实例ID查询历史任务的节点key
	 * @param act
	 * @return
	 */
	public Act queryHisTask(Act act);
	
	/**
	 * 根据流程执行ID和流程节点名称查询对应的执行者信息
	 * @param taskInfoEntity
	 * @return
	 */
	public TaskInfoEntity queryAssigneeForExecutionId(TaskInfoEntity taskInfoEntity);
	
	/**
	 * 资源办理根据当前环节任务ID查询指派环节任务信息
	 * @param taskInfoEntity
	 * @return
	 */
	public List<TaskInfoEntity> queryChildTaskInfoForParentTaskId(TaskInfoEntity taskInfoEntity);
	
	/**
	 * 根据任务ID删除运行中任务
	 * @param taskId
	 */
	public void deleteRunTaskForTaskId(String taskId);
	
	/**
	 *  根据流程字典值查询流程key
	 * @param gateGory
	 * @return
	 */
	public TaskInfoEntity queryActModel(String gateGory);

	public List<String> queryThisHiTaskIdForExecutionId(String procInsId);

	public String queryTheLastAssignee(String procInsId);

    int queryTodoListCount(TaskInfoEntity taskInfoEntity);

	List<TaskInfoEntity> queryTodoProjectApprovalFlow(TaskInfoEntity taskInfoEntity);

	List<Map<String, Object>> queryTodoListCountAll(TaskInfoEntity taskInfoEntity);
}
