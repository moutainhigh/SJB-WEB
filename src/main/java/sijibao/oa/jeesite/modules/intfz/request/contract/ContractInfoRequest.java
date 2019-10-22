/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同信息Entity
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同信息接收对象")
public class ContractInfoRequest{
	
	@ApiModelProperty(value="主键ID")
	private String id;  //主键ID
	
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	
	@ApiModelProperty(value="甲方名称")
	private String firstPartyName;		// 甲方名称
	
	@ApiModelProperty(value="甲方统一社会信用代码")
	private String firstCreditCode;		// 甲方统一社会信用代码
	
	@ApiModelProperty(value="甲方住所")
	private String firstAddress;		// 甲方住所
	
	@ApiModelProperty(value="甲方法定代表人")
	private String firstLegalRepresentative;		// 甲方法定代表人
	
	@ApiModelProperty(value="甲方联系人")
	private String firstLinkman;		// 甲方联系人
	
	@ApiModelProperty(value="甲方联系人电话")
	private String firstLinkmanPhone;		// 甲方联系人电话
	
	@ApiModelProperty(value="甲方联系人邮箱")
	private String firstLinkmanMail;		// 甲方联系人邮箱
	
	@ApiModelProperty(value="乙方名称")
	private String secondPartyName;		// 乙方名称
	
	@ApiModelProperty(value="乙方同意社会信用代码")
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
	
	@ApiModelProperty(value="调度费比例")
	private String dispatchProportion;		// 调度费比例
	
	@ApiModelProperty(value="违约金比例")
	private String penaltyProportion;		// 违约金比例
	
	@ApiModelProperty(value="有效期(年)")
	private String validityDate;		// 有效期（年）
	
	@ApiModelProperty(value="签约日期")
	private Date contractDate;		// 签约日期

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getFirstPartyName() {
		return firstPartyName;
	}

	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}

	public String getFirstCreditCode() {
		return firstCreditCode;
	}

	public void setFirstCreditCode(String firstCreditCode) {
		this.firstCreditCode = firstCreditCode;
	}

	public String getFirstAddress() {
		return firstAddress;
	}

	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}

	public String getFirstLegalRepresentative() {
		return firstLegalRepresentative;
	}

	public void setFirstLegalRepresentative(String firstLegalRepresentative) {
		this.firstLegalRepresentative = firstLegalRepresentative;
	}

	public String getFirstLinkman() {
		return firstLinkman;
	}

	public void setFirstLinkman(String firstLinkman) {
		this.firstLinkman = firstLinkman;
	}

	public String getFirstLinkmanPhone() {
		return firstLinkmanPhone;
	}

	public void setFirstLinkmanPhone(String firstLinkmanPhone) {
		this.firstLinkmanPhone = firstLinkmanPhone;
	}

	public String getFirstLinkmanMail() {
		return firstLinkmanMail;
	}

	public void setFirstLinkmanMail(String firstLinkmanMail) {
		this.firstLinkmanMail = firstLinkmanMail;
	}

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

	public String getDispatchProportion() {
		return dispatchProportion;
	}

	public void setDispatchProportion(String dispatchProportion) {
		this.dispatchProportion = dispatchProportion;
	}

	public String getPenaltyProportion() {
		return penaltyProportion;
	}

	public void setPenaltyProportion(String penaltyProportion) {
		this.penaltyProportion = penaltyProportion;
	}

	public String getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	@Override
	public String toString() {
		return "ContractInfoRequest [id=" + id + ", contractCode=" + contractCode + ", contractName=" + contractName
				+ ", firstPartyName=" + firstPartyName + ", firstCreditCode=" + firstCreditCode + ", firstAddress="
				+ firstAddress + ", firstLegalRepresentative=" + firstLegalRepresentative + ", firstLinkman="
				+ firstLinkman + ", firstLinkmanPhone=" + firstLinkmanPhone + ", firstLinkmanMail=" + firstLinkmanMail
				+ ", secondPartyName=" + secondPartyName + ", secondCreditCode=" + secondCreditCode + ", secondAddress="
				+ secondAddress + ", secondLegalRepresentative=" + secondLegalRepresentative + ", secondLinkman="
				+ secondLinkman + ", secondLinkmanPhone=" + secondLinkmanPhone + ", secondLinkmanMail="
				+ secondLinkmanMail + ", dispatchProportion=" + dispatchProportion + ", penaltyProportion="
				+ penaltyProportion + ", validityDate=" + validityDate + ", contractDate=" + contractDate + "]";
	}
}