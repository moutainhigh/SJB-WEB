package sijibao.oa.jeesite.modules.intfz.request.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请流程收回对象
 * @author xby
 */
@ApiModel(value="资源申请流程收回对象")
public class ResourcesApplyFlowRepealRequest {

	@ApiModelProperty(value="流程任务ID")
	private String taskId; 		// 任务编号
	
	@ApiModelProperty(value="流程实例ID")
	private String procInsId; 	// 流程实例ID
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	@Override
	public String toString() {
		return "ResourcesApplyFlowRepealRequest [taskId=" + taskId + ", procInsId=" + procInsId + "]";
	}
	
}
