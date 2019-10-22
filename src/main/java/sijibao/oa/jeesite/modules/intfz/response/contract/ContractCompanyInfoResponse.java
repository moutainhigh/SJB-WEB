/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同公司信息信息返回对象
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同公司信息返回对象")
public class ContractCompanyInfoResponse{
	@ApiModelProperty(value="公司信息编码")
	private String contractCompanyCode ;//公司信息编码
	@ApiModelProperty(value="合同名称")
	private String contractName;//合同名称
	@ApiModelProperty(value="乙方名称缩写")
	private String secondPartyNameKey;		// 乙方名称缩写
	@ApiModelProperty(value="乙方名称")
	private String secondPartyName;		// 乙方名称
	@ApiModelProperty(value="乙方统一社会信用代码")
	private String secondCreditCode;		// 乙方统一社会信用代码
	@ApiModelProperty(value="乙方住所")
	private String secondAddress;		// 乙方住所
	@ApiModelProperty(value="乙方法定代表人")
	private String secondLegalRepresentative;		// 乙方法定代表人
	@ApiModelProperty(value="乙方联系人")
	private String secondLinkman;		// 乙方联系人
	@ApiModelProperty(value="乙方联系人电话")
	private String secondLinkmanPhone;		// 乙方联系人电话
	@ApiModelProperty(value="乙方联系人邮箱")
	private String secondLinkmanMail;		// 乙方联系人邮箱
	@ApiModelProperty(value="有效期")
	private String validityTime;//有效期
	@ApiModelProperty(value="合同类型缩写")
	private String contractTypeKey;//合同类型缩写
	@ApiModelProperty(value="合同类型")
	private String contractType;//合同类型
	@ApiModelProperty(value="违约金比例")
	private String penaltyProportion;//违约金比例
	
	public String getSecondPartyName() {
		return secondPartyName;
	}
	public void setSecondPartyName(String secondPartyName) {
		this.secondPartyName = secondPartyName;
	}
	public String getSecondCreditCode() {
		return secondCreditCode;
	}
	public void setSecondCreditCode(String secondCreditCode) {
		this.secondCreditCode = secondCreditCode;
	}
	public String getSecondAddress() {
		return secondAddress;
	}
	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}
	public String getSecondLegalRepresentative() {
		return secondLegalRepresentative;
	}
	public void setSecondLegalRepresentative(String secondLegalRepresentative) {
		this.secondLegalRepresentative = secondLegalRepresentative;
	}
	public String getSecondLinkman() {
		return secondLinkman;
	}
	public void setSecondLinkman(String secondLinkman) {
		this.secondLinkman = secondLinkman;
	}
	public String getSecondLinkmanPhone() {
		return secondLinkmanPhone;
	}
	public void setSecondLinkmanPhone(String secondLinkmanPhone) {
		this.secondLinkmanPhone = secondLinkmanPhone;
	}
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
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getPenaltyProportion() {
		return penaltyProportion;
	}
	public void setPenaltyProportion(String penaltyProportion) {
		this.penaltyProportion = penaltyProportion;
	}
	
	public String getSecondPartyNameKey() {
		return secondPartyNameKey;
	}
	public void setSecondPartyNameKey(String secondPartyNameKey) {
		this.secondPartyNameKey = secondPartyNameKey;
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
		return "ContractCompanyInfoResponse [contractCompanyCode=" + contractCompanyCode + ", contractName="
				+ contractName + ", secondPartyNameKey=" + secondPartyNameKey + ", secondPartyName=" + secondPartyName
				+ ", secondCreditCode=" + secondCreditCode + ", secondAddress=" + secondAddress
				+ ", secondLegalRepresentative=" + secondLegalRepresentative + ", secondLinkman=" + secondLinkman
				+ ", secondLinkmanPhone=" + secondLinkmanPhone + ", secondLinkmanMail=" + secondLinkmanMail
				+ ", validityTime=" + validityTime + ", contractTypeKey=" + contractTypeKey + ", contractType="
				+ contractType + ", penaltyProportion=" + penaltyProportion + "]";
	}
	
}