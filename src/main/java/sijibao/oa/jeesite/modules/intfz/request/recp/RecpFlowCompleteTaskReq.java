package sijibao.oa.jeesite.modules.intfz.request.recp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 完成任务请求对象
 * @author xuby
 */
@ApiModel(value="接待申请完成任务请求对象")
public class RecpFlowCompleteTaskReq {
	@ApiModelProperty(value="流程实例ID")
	private String procInsId; //流程实例ID
	@ApiModelProperty(value="审批意见状态：yes/no")
	private String flag; //审批意见状态
	@ApiModelProperty(value="审批意见：同意/驳回")
	private String comment; //审批意见
	@ApiModelProperty(value="流程申请ID")
	private String recpFlowId; //业务流程ID
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
	public String getRecpFlowId() {
		return recpFlowId;
	}
	public void setRecpFlowId(String recpFlowId) {
		this.recpFlowId = recpFlowId;
	}
	@Override
	public String toString() {
		return "RecpFlowCompleteTaskReq [procInsId=" + procInsId + ", flag=" + flag + ", comment=" + comment
				+ ", recpFlowId=" + recpFlowId + "]";
	}
	
}
