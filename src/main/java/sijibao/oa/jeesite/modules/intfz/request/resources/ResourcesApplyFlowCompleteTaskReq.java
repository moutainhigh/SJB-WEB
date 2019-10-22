package sijibao.oa.jeesite.modules.intfz.request.resources;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请完成任务请求对象
 * @author xuby
 */
@ApiModel
public class ResourcesApplyFlowCompleteTaskReq {
	@ApiModelProperty(value="流程实例ID")
	private String procInsId; //流程实例ID
	@ApiModelProperty(value="审批意见状态：yes/no")
	private String flag; //审批意见状态
	@ApiModelProperty(value="流程申请ID")
	private String resApplyFlowId; //资源申请流程ID
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
	public String getResApplyFlowId() {
		return resApplyFlowId;
	}
	public void setResApplyFlowId(String resApplyFlowId) {
		this.resApplyFlowId = resApplyFlowId;
	}
	@Override
	public String toString() {
		return "ResourcesApplyFlowCompleteTaskReq [procInsId=" + procInsId + ", flag=" + flag + ", resApplyFlowId="
				+ resApplyFlowId + ", assignList=" + assignList + "]";
	}
	
}
