package sijibao.oa.jeesite.modules.intfz.response.contract;

import java.io.Serializable;
import java.util.List;

import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;
import com.sijibao.oa.modules.intfz.response.contractnew.ContractFlowDetailInfoNewMainResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程详情对象
 * @author xby
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="合同管理流程详情对象")
public class ContractFlowDetailResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="合同管理流程日志信息")
	private List<FlowLogResponse> flowLoglist;
	
//	@ApiModelProperty(value="合同审批信息")
//	private ContractFlowResponse contractFlowResponse;
	
	@ApiModelProperty(value="合同审批信息(新)")
	private ContractFlowDetailInfoNewMainResponse contractFlowDetailInfoNewResponse;
	
	
	public ContractFlowDetailResponse(){}
//	public ContractFlowDetailResponse(ContractFlowResponse contractFlowResponse,
//			List<FlowLogResponse> flowLoglist) {
//		this.contractFlowResponse = contractFlowResponse;
//		this.flowLoglist = flowLoglist;
//	}
	public ContractFlowDetailResponse(ContractFlowDetailInfoNewMainResponse contractFlowDetailInfoNewResponse,
			List<FlowLogResponse> flowLoglist) {
		this.contractFlowDetailInfoNewResponse = contractFlowDetailInfoNewResponse;
		this.flowLoglist = flowLoglist;
	}
	
	public List<FlowLogResponse> getFlowLoglist() {
		return flowLoglist;
	}
	public void setFlowLoglist(List<FlowLogResponse> flowLoglist) {
		this.flowLoglist = flowLoglist;
	}
//	public ContractFlowResponse getContractFlowResponse() {
//		return contractFlowResponse;
//	}
//	public void setContractFlowResponse(ContractFlowResponse contractFlowResponse) {
//		this.contractFlowResponse = contractFlowResponse;
//	}
	public ContractFlowDetailInfoNewMainResponse getContractFlowDetailInfoNewResponse() {
		return contractFlowDetailInfoNewResponse;
	}
	public void setContractFlowDetailInfoNewResponse(
			ContractFlowDetailInfoNewMainResponse contractFlowDetailInfoNewResponse) {
		this.contractFlowDetailInfoNewResponse = contractFlowDetailInfoNewResponse;
	}
	
}
