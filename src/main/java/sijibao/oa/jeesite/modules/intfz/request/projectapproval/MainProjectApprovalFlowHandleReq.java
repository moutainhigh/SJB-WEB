package sijibao.oa.jeesite.modules.intfz.request.projectapproval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 立项申请流程处理对象
 * @author wanxb
 */
@ApiModel
public class MainProjectApprovalFlowHandleReq {

	@ApiModelProperty(value="立项申请ID")
	private String projectApprovalFlowId;//流程申请ID

	public String getProjectApprovalFlowId() {
		return projectApprovalFlowId;
	}

	public void setProjectApprovalFlowId(String projectApprovalFlowId) {
		this.projectApprovalFlowId = projectApprovalFlowId;
	}

	@Override
	public String toString() {
		return "MainProjectApprovalFlowHandleReq{" +
				"projectApprovalFlowId='" + projectApprovalFlowId + '\'' +
				'}';
	}
}
