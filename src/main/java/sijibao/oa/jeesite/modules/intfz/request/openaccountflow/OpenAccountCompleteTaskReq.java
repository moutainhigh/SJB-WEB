package sijibao.oa.jeesite.modules.intfz.request.openaccountflow;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开户申请完成任务请求对象
 * @author wanxb
 */
@ApiModel(value="开户申请完成任务请求对象")
public class OpenAccountCompleteTaskReq {
	@ApiModelProperty(value="流程任务ID")
	private String taskId; //任务ID
	@ApiModelProperty(value="流程实例ID")
	private String procInsId; //流程实例ID
	@ApiModelProperty(value="审批意见状态：yes/no")
	private String flag; //审批意见状态
	@ApiModelProperty(value="流程申请ID")
	private String businessId; //报销流程ID
	
	
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
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "OpenAccountCompleteTaskReq [taskId=" + taskId + ", procInsId=" + procInsId + ", flag=" + flag
				+ ", businessId=" + businessId + "]";
	}
	
}
