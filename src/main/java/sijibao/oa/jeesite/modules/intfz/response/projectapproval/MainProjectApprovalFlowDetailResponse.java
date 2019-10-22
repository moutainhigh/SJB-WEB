package sijibao.oa.jeesite.modules.intfz.response.projectapproval;

import java.io.Serializable;
import java.util.List;

import com.sijibao.oa.activiti.api.response.common.FlowLogResponse;

/**
 * 合同管理流程详情对象
 * @author xby
 * @version 2018-11-02
 */
public class MainProjectApprovalFlowDetailResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private List<FlowLogResponse> flowLoglist;
	
	private MainProjectApprovalFlowResp projectApprovalFlowResp;

	public List<FlowLogResponse> getFlowLoglist() {

		return flowLoglist;
	}

	public void setFlowLoglist(List<FlowLogResponse> flowLoglist) {
		this.flowLoglist = flowLoglist;
	}

	public MainProjectApprovalFlowResp getProjectApprovalFlowResp() {
		return projectApprovalFlowResp;
	}

	public void setProjectApprovalFlowResp(MainProjectApprovalFlowResp projectApprovalFlowResp) {
		this.projectApprovalFlowResp = projectApprovalFlowResp;
	}

	@Override
	public String toString() {
		return "MainProjectApprovalFlowDetailResponse{" +
				"flowLoglist=" + flowLoglist +
				", projectApprovalFlowResp=" + projectApprovalFlowResp +
				'}';
	}
}
