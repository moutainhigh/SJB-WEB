package sijibao.oa.jeesite.modules.intfz.request.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程申请处理对象
 * @author xuby
 */
@ApiModel(value="合同管理流程申请处理对象")
public class ContractFlowHandleReq {

	@ApiModelProperty(value="合同管理申请ID")
	private String contractFlowId;//流程申请ID

	public String getContractFlowId() {
		return contractFlowId;
	}

	public void setContractFlowId(String contractFlowId) {
		this.contractFlowId = contractFlowId;
	}

	@Override
	public String toString() {
		return "ContractFlowHandleReq [contractFlowId=" + contractFlowId + "]";
	}
}
