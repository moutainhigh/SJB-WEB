package sijibao.oa.jeesite.modules.act.service.cmd;

import java.util.Map;

import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Event;

import com.sijibao.oa.modules.sys.entity.User;

/**
 * 直接完成当前任务，不向后继续跳转 
 * @author xuby
 */
public class CompletedTaskCmd implements Command<Void> {
	
	private TaskEntity taskEntity;
	protected Map<String, Object> variables;
	private User user;
	protected String message;

	public CompletedTaskCmd(TaskEntity taskEntity, Map<String, Object> variables, User user, String message) {
		this.taskEntity = taskEntity;
		this.variables = variables;
		this.user = user;
		this.message = message;
	}

	@Override
	public Void execute(CommandContext commandContext) {
		
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
			
			//添加审批意见
		    CommentEntity comment = new CommentEntity();
		    comment.setUserId(user.getLoginName());
		    comment.setType(CommentEntity.TYPE_COMMENT);
		    comment.setTime(commandContext.getProcessEngineConfiguration().getClock().getCurrentTime());
		    comment.setTaskId(taskEntity.getId());
		    comment.setProcessInstanceId(taskEntity.getProcessInstanceId());
		    comment.setAction(Event.ACTION_ADD_COMMENT);
		    
		    String eventMessage = message.replaceAll("\\s+", " ");
		    if (eventMessage.length()>163) {
		      eventMessage = eventMessage.substring(0, 160)+"...";
		    }
		    comment.setMessage(eventMessage);
		    comment.setFullMessage(message);
		    commandContext.getCommentEntityManager().insert(comment);
		}

		return null;
	}
}