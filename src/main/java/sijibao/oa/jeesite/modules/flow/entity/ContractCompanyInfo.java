/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 合同公司信息Entity
 * @author xby
 * @version 2018-07-12
 */
public class ContractCompanyInfo extends DataEntity<ContractCompanyInfo> {
	
	private static final long serialVersionUID = 1L;
	private String contractCompanyCode;//编码
	private String contractName;//合同名称
	private String secondPartyName;		// 乙方名称
	private String secondCreditCode;		// 乙方统一社会信用代码
	private String secondAddress;		// 乙方住所
	private String secondLegalRepresentative;		// 乙方法定代表人
	private String secondLinkman;		// 乙方联系人
	private String secondLinkmanPhone;		// 乙方联系人电话
	private String secondLinkmanMail;		// 乙方联系人邮箱
	private String validityTime;//有效期
	private String contractTypeKey;//合同类型
	private String penaltyProportion;//违约金比例
	
	private String oldContractName;//判断修改页面

	public String getOldContractName() {
		return oldContractName;
	}

	public void setOldContractName(String oldContractName) {
		this.oldContractName = oldContractName;
	}

	public ContractCompanyInfo() {
		super();
	}

	public ContractCompanyInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="乙方名称长度必须介于 1 和 64 之间")
	public String getSecondPartyName() {
		return secondPartyName;
	}

	public void setSecondPartyName(String secondPartyName) {
		this.secondPartyName = secondPartyName;
	}
	
	@Length(min=1, max=64, message="乙方统一社会信用代码长度必须介于 1 和 64 之间")
	public String getSecondCreditCode() {
		return secondCreditCode;
	}

	public void setSecondCreditCode(String secondCreditCode) {
		this.secondCreditCode = secondCreditCode;
	}
	
	@Length(min=1, max=64, message="乙方住所长度必须介于 1 和 64 之间")
	public String getSecondAddress() {
		return secondAddress;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}
	
	@Length(min=1, max=32, message="乙方法定代表人长度必须介于 1 和 32 之间")
	public String getSecondLegalRepresentative() {
		return secondLegalRepresentative;
	}

	public void setSecondLegalRepresentative(String secondLegalRepresentative) {
		this.secondLegalRepresentative = secondLegalRepresentative;
	}
	
	@Length(min=1, max=32, message="乙方联系人长度必须介于 1 和 32 之间")
	public String getSecondLinkman() {
		return secondLinkman;
	}

	public void setSecondLinkman(String secondLinkman) {
		this.secondLinkman = secondLinkman;
	}
	
	@Length(min=1, max=16, message="乙方联系人电话长度必须介于 1 和 16 之间")
	public String getSecondLinkmanPhone() {
		return secondLinkmanPhone;
	}

	public void setSecondLinkmanPhone(String secondLinkmanPhone) {
		this.secondLinkmanPhone = secondLinkmanPhone;
	}
	
	@Length(min=1, max=32, message="乙方联系人邮箱长度必须介于 1 和 32 之间")
	public String getSecondLinkmanMail() {
		return secondLinkmanMail;
	}

	public void setSecondLinkmanMail(String secondLinkmanMail) {
		this.secondLinkmanMail = secondLinkmanMail;
	}

	public String getContractCompanyCode() {
		return contractCompanyCode;
	}

	public void setContractCompanyCode(String contractCompanyCode) {
		this.contractCompanyCode = contractCompanyCode;
	}

	public String getValidityTime() {
		return validityTime;
	}

	public void setValidityTime(String validityTime) {
		this.validityTime = validityTime;
	}


	public String getPenaltyProportion() {
		return penaltyProportion;
	}

	public void setPenaltyProportion(String penaltyProportion) {
		this.penaltyProportion = penaltyProportion;
	}

	public String getContractTypeKey() {
		return contractTypeKey;
	}

	public void setContractTypeKey(String contractTypeKey) {
		this.contractTypeKey = contractTypeKey;
	}
	

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	@Override
	public String toString() {
		return "ContractCompanyInfo [contractCompanyCode=" + contractCompanyCode + ", contractName=" + contractName
				+ ", secondPartyName=" + secondPartyName + ", secondCreditCode=" + secondCreditCode + ", secondAddress="
				+ secondAddress + ", secondLegalRepresentative=" + secondLegalRepresentative + ", secondLinkman="
				+ secondLinkman + ", secondLinkmanPhone=" + secondLinkmanPhone + ", secondLinkmanMail="
				+ secondLinkmanMail + ", validityTime=" + validityTime + ", contractTypeKey=" + contractTypeKey
				+ ", penaltyProportion=" + penaltyProportion + ", oldContractName=" + oldContractName + "]";
	}




	

}