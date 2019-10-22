/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.util.List;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程接收对象
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同管理流程接收对象")
public class ContractFlowRequest{
	
	@ApiModelProperty(value="主键ID")
	private String id; //主键ID
	
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="合同名称编码")
	private String contractNameCode;		// 合同名称编码
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="客户ID")
	private String custId; //客户ID
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="甲方名称")
	private String firstPartyName;		// 甲方名称
	
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="甲方统一社会信用代码")
	private String firstCreditCode;		// 甲方统一社会信用代码
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="甲方住所")
	private String firstAddress;		// 甲方住所
	
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="甲方法定代表人")
	private String firstLegalRepresentative;		// 甲方法定代表人
	
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="甲方联系人")
	private String firstLinkman;		// 甲方联系人
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="甲方联系人电话")
	private String firstLinkmanPhone;		// 甲方联系人电话
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="甲方联系人邮箱")
	private String firstLinkmanMail;		// 甲方联系人邮箱
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="乙方名称")
	private String secondPartyName;		// 乙方名称
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="乙方统一社会信用代码")
	private String secondCreditCode;		// 乙方统一社会信用代码
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="乙方住所")
	private String secondAddress;		// 乙方住所
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="乙方法定代表人")
	private String secondLegalRepresentative;		// 乙方法定代表人
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="乙方联系人")
	private String secondLinkman;		// 乙方联系人
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="乙方联系人电话")
	private String secondLinkmanPhone;		// 乙方联系人电话
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="乙方联系人邮箱")
	private String secondLinkmanMail;		// 乙方联系人邮箱
	
	@IntfzValid(min=0, max = 16, nullable=false)
	@ApiModelProperty(value="调度费比例")
	private String dispatchProportion;		// 调度费比例
	
	@IntfzValid(min=0, max = 16, nullable=false)
	@ApiModelProperty(value="违约金比例")
	private String penaltyProportion;		// 违约金比例
	
	@IntfzValid(min=0, max = 8, nullable=false)
	@ApiModelProperty(value="有效期(年)")
	private String validityDate;		// 有效期（年）
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="签约日期")
	private long contractDate;		// 签约日期
	
	@ApiModelProperty(value="快递公司")
	private String expressCompany;		// 快递公司
	
	@ApiModelProperty(value="快递单号")
	private String expressBill;		// 快递单号
	
	@ApiModelProperty(value="备注")
	private String remarks; 
	
	@ApiModelProperty(value="合同附件信息")
	private List<MainContractAttachmentRequest> contractAttachmentList;//附件信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
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

	public long getContractDate() {
		return contractDate;
	}

	public void setContractDate(long contractDate) {
		this.contractDate = contractDate;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressBill() {
		return expressBill;
	}

	public void setExpressBill(String expressBill) {
		this.expressBill = expressBill;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public List<MainContractAttachmentRequest> getContractAttachmentList() {
		return contractAttachmentList;
	}

	public void setContractAttachmentList(List<MainContractAttachmentRequest> contractAttachmentList) {
		this.contractAttachmentList = contractAttachmentList;
	}

	public String getContractNameCode() {
		return contractNameCode;
	}

	public void setContractNameCode(String contractNameCode) {
		this.contractNameCode = contractNameCode;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Override
	public String toString() {
		return "ContractFlowRequest [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", contractName=" + contractName + ", contractNameCode=" + contractNameCode + ", custId=" + custId
				+ ", firstPartyName=" + firstPartyName + ", firstCreditCode=" + firstCreditCode + ", firstAddress="
				+ firstAddress + ", firstLegalRepresentative=" + firstLegalRepresentative + ", firstLinkman="
				+ firstLinkman + ", firstLinkmanPhone=" + firstLinkmanPhone + ", firstLinkmanMail=" + firstLinkmanMail
				+ ", secondPartyName=" + secondPartyName + ", secondCreditCode=" + secondCreditCode + ", secondAddress="
				+ secondAddress + ", secondLegalRepresentative=" + secondLegalRepresentative + ", secondLinkman="
				+ secondLinkman + ", secondLinkmanPhone=" + secondLinkmanPhone + ", secondLinkmanMail="
				+ secondLinkmanMail + ", dispatchProportion=" + dispatchProportion + ", penaltyProportion="
				+ penaltyProportion + ", validityDate=" + validityDate + ", contractDate=" + contractDate
				+ ", expressCompany=" + expressCompany + ", expressBill=" + expressBill + ", remarks=" + remarks
				+ ", contractAttachmentList=" + contractAttachmentList + "]";
	}

}