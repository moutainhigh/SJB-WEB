package sijibao.oa.jeesite.modules.intfz.request.common;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 流程ACT请求对象
 * @author Petter
 */
@ApiModel
public class ActRequest {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="流程任务ID")
	private String taskId; 		// 任务编号
	@ApiModelProperty(value="流程任务名称")
	private String taskName; 	// 任务名称
	@ApiModelProperty(value="流程任务定义KEY(任务环节标识)")
	private String taskDefKey; 	// 任务定义Key（任务环节标识）

	@ApiModelProperty(value="流程实例ID")
	private String procInsId; 	// 流程实例ID
	@ApiModelProperty(value="流程定义ID")
	private String procDefId; 	// 流程定义ID
	@ApiModelProperty(value="流程定义KEY(流程定义标识)")
	private String procDefKey; 	// 流程定义Key（流程定义标识）

	@ApiModelProperty(value="业务绑定ID")
	private String businessId;		// 业务绑定ID
	
	@ApiModelProperty(value="任务标题")
	private String title; 		// 任务标题

	@ApiModelProperty(value="任务状态:todo/claim/finish")
	private String status; 		// 任务状态（todo/claim/finish）

	@ApiModelProperty(value="任务意见")
	private String comment; 	// 任务意见
	@ApiModelProperty(value="意见状态")
	private String flag; 		// 意见状态
	
	@ApiModelProperty(value="任务执行人编号")
	private String assignee; // 任务执行人编号
	@ApiModelProperty(value="任务执行人名称")
	private String assigneeName; // 任务执行人名称

//	private Variable taskVars; 	// 流程任务变量
	
	@ApiModelProperty(value="开始查询日期")
	private Date beginDate;	// 开始查询日期
	@ApiModelProperty(value="结束查询日期")
	private Date endDate;	// 结束查询日期
	@ApiModelProperty(value="单据类型:1:市场,2:实施,3:报销,4:开户")
	private String billType; //单据类型
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public String getProcDefKey() {
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	@Override
	public String toString() {
		return "ActRequest [taskId=" + taskId + ", taskName=" + taskName + ", taskDefKey=" + taskDefKey + ", procInsId="
				+ procInsId + ", procDefId=" + procDefId + ", procDefKey=" + procDefKey + ", businessId=" + businessId
				+ ", title=" + title + ", status=" + status + ", comment=" + comment + ", flag=" + flag + ", assignee="
				+ assignee + ", assigneeName=" + assigneeName + ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", billType=" + billType + "]";
	}
	
}
