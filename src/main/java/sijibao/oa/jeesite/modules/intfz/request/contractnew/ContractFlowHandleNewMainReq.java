package sijibao.oa.jeesite.modules.intfz.request.contractnew;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程申请处理对象
 * @author xuby
 */
@ApiModel(value="合同管理流程申请处理对象")
public class ContractFlowHandleNewMainReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="合同管理申请ID")
	private String contractFlowId;//流程申请ID

	public String getContractFlowId() {
		return contractFlowId;
	}

	public void setContractFlowId(String contractFlowId) {
		this.contractFlowId = contractFlowId;
	}
	
	public ContractFlowHandleNewMainReq(){}
	
	@Override
	public String toString() {
		return "ContractFlowHandleReq [contractFlowId=" + contractFlowId + "]";
	}
}
