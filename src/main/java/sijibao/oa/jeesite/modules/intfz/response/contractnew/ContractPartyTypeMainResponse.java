package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同方返回对象
 * @author xby
 * @version 2018-10-24
 */
@ApiModel(value="合同方返回对象")
public class ContractPartyTypeMainResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="合同方类型：1甲方，2乙方，3丙方")
	private String partyType; //合同方类型
	
	@ApiModelProperty(value="合同方类型描述")
	private String partyName; //合同方类型描述
	
	@ApiModelProperty(value="合同方字段")
	private List<ContractPartyMainResponse> contractPartyType; //合同方字段
	public String getPartyType() {
		return partyType;
	}
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public List<ContractPartyMainResponse> getContractPartyType() {
		return contractPartyType;
	}
	public void setContractPartyType(List<ContractPartyMainResponse> contractPartyType) {
		this.contractPartyType = contractPartyType;
	}
	@Override
	public String toString() {
		return "ContractPartyTypeMainResponse [partyType=" + partyType + ", partyName=" + partyName
				+ ", contractPartyType=" + contractPartyType + "]";
	}
	
}
