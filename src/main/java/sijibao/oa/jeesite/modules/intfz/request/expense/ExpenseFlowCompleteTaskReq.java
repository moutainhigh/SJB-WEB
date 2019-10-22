package sijibao.oa.jeesite.modules.intfz.request.expense;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销申请完成任务请求对象
 * @author xuby
 */
@ApiModel(value="报销申请完成任务请求对象")
public class ExpenseFlowCompleteTaskReq {
	@ApiModelProperty(value="流程实例ID")
	private String procInsId; //流程实例ID
	@ApiModelProperty(value="审批意见状态：yes/no")
	private String flag; //审批意见状态
	@ApiModelProperty(value="审批意见：同意/驳回")
	private String comment; //审批意见
	@ApiModelProperty(value="报销申请ID")
	private String expenseFlowId; //业务流程ID
	
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getExpenseFlowId() {
		return expenseFlowId;
	}
	public void setExpenseFlowId(String expenseFlowId) {
		this.expenseFlowId = expenseFlowId;
	}
	@Override
	public String toString() {
		return "ExpenseFlowCompleteTaskReq [procInsId=" + procInsId + ", flag=" + flag + ", comment=" + comment
				+ ", expenseFlowId=" + expenseFlowId + "]";
	}
	
}
