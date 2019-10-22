package sijibao.oa.jeesite.modules.oa.entity;

import java.util.ArrayList;
import java.util.List;


/**
 * 合同方返回对象
 * @author Administrator
 * @version 2018-10-24
 */
public class ContractPartyType {

	
	private String partyType; //合同方类型
	private String partyName; //合同方类型描述
	private List<ContractParty> contractPartyType = new ArrayList<ContractParty>(); //合同方字段
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
	public List<ContractParty> getContractPartyType() {
		return contractPartyType;
	}
	public void setContractPartyType(List<ContractParty> contractPartyType) {
		this.contractPartyType = contractPartyType;
	}
	public ContractPartyType(){}
	
	@Override
	public String toString() {
		return "ContractPartyType [partyType=" + partyType + ", partyName=" + partyName + ", contractPartyType="
				+ contractPartyType + "]";
	}
	
}
