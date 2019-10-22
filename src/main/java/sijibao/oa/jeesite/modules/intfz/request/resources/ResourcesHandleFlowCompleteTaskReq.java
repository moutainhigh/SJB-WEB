package sijibao.oa.jeesite.modules.intfz.request.resources;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源办理完成任务请求对象
 * @author xuby
 */
@ApiModel
public class ResourcesHandleFlowCompleteTaskReq {
	@ApiModelProperty(value="流程实例ID")
	private String procInsId; //流程实例ID
	@ApiModelProperty(value="审批意见：同意/驳回")
	private String comment; //审批意见
	@ApiModelProperty(value="审批意见状态：yes/no")
	private String flag; //审批意见状态
	@ApiModelProperty(value="流程申请ID")
	private String resHandleFlowId; //资源办理流程ID
	@ApiModelProperty(value="指派人员")
	private List<ResourcesAssignRequest> assignList; //指派人员
	
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public List<ResourcesAssignRequest> getAssignList() {
		return assignList;
	}
	public void setAssignList(List<ResourcesAssignRequest> assignList) {
		this.assignList = assignList;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getResHandleFlowId() {
		return resHandleFlowId;
	}
	public void setResHandleFlowId(String resHandleFlowId) {
		this.resHandleFlowId = resHandleFlowId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "ResourcesHandleFlowCompleteTaskReq [procInsId=" + procInsId + ", comment=" + comment + ", flag=" + flag
				+ ", resHandleFlowId=" + resHandleFlowId + ", assignList=" + assignList + "]";
	}
	
}
