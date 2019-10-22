package sijibao.oa.jeesite.modules.act.service.cmd;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.runtime.AtomicOperation;

public class JumpTaskListCmd implements Command<Void> {
	
	private List<TaskEntity> taskEntityList;
	private ActivityImpl targetActivity;
	protected Map<String, Object> variables;
	private TaskEntity thisTaskEntity;
	public JumpTaskListCmd(List<TaskEntity> taskEntityList, ActivityImpl targetActivity, Map<String, Object> variables, TaskEntity thisTask) {
		this.taskEntityList = taskEntityList;
		this.targetActivity = targetActivity;
		this.variables = variables;
		this.thisTaskEntity = thisTask;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		
		for(TaskEntity taskEntity:taskEntityList){
			if (taskEntity != null) {
				
				//删除当前的任务，不能删除当前正在执行的任务，所以要先清除掉关联
				if (variables != null) {
					if (taskEntity.getExecutionId() != null) {
						taskEntity.setExecutionVariables(variables);
					} else {
						taskEntity.setVariables(variables);
					}
				}
				// 完成待办任务
				Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity,
						TaskEntity.DELETE_REASON_COMPLETED, false);	// DELETE_REASON_DELETED  DELETE_REASON_COMPLETED
				
				// 完成活动历史
				Context.getCommandContext().getHistoryManager()
				.recordActivityEnd((ExecutionEntity) taskEntity.getExecution());
			}
		}
		
		// 跳转任务
		ExecutionEntity execution = thisTaskEntity.getExecution();
		execution.setActivity(targetActivity);
		execution.performOperation(AtomicOperation.ACTIVITY_START);

		return null;
	}
}