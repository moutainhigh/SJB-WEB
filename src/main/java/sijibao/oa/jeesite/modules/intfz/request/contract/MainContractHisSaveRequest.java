/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.contract;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同归档保存请求对象
 * @author wanxb
 * @version 2018-10-22 14:32:45
 */
@ApiModel(value="合同归档保存请求对象")
public class MainContractHisSaveRequest {
	
	@ApiModelProperty(value="主键ID")
	private String id;  //主键ID
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	@ApiModelProperty(value="合同模板id")
	private String contractNameId;		// 合同模板id
	@ApiModelProperty(value="项目ids")
	private List<String> projectIds;//项目ids
	@ApiModelProperty(value="模版版本号")
	private String version;		// 模版版本号
	@ApiModelProperty(value="主合同id")
	private String associationMainId;		// 主合同id
	@ApiModelProperty(value="主合同Name")
	private String associationMainName;		// 主合同name
	private String contractCode;		// 合同编号
	@ApiModelProperty(value="甲方名称")
	private String firstMemberName;		// 甲方名称
	@ApiModelProperty(value="甲方统一社会信用代码（个人为身份证）")
	private String firstCreditCode;		// 甲方统一社会信用代码（个人为身份证）
	@ApiModelProperty(value="甲方住所")
	private String firstAddress;		// 甲方住所
	@ApiModelProperty(value="甲方法定代表人（甲方为企业有）")
	private String firstLegalRepresentative;		// 甲方法定代表人（甲方为企业有）
	@ApiModelProperty(value="甲方联系方式（甲方为个人有）")
	private String firstLinkMethod;	// 甲方联系方式（甲方为个人有）
	@ApiModelProperty(value="乙方名称")
	private String secondMemberName;		// 乙方名称
	@ApiModelProperty(value="乙方统一社会信用代码（个人为身份证）")
	private String secondCreditCode;		// 乙方统一社会信用代码（个人为身份证）
	@ApiModelProperty(value="乙方住所")
	private String secondAddress;		// 乙方住所
	@ApiModelProperty(value="乙方法定代表人（乙方为企业有）")
	private String secondLegalRepresentative;		// 乙方法定代表人（乙方为企业有）
	@ApiModelProperty(value="乙方联系方式（乙方为个人有）")
	private String secondLinkMethod;		// 乙方联系方式（乙方为个人有）
	@ApiModelProperty(value="丙方名称")
	private String thirdMemberName;		// 丙方名称
	@ApiModelProperty(value="丙方统一社会信用代码（个人为身份证）")
	private String thirdCreditCode;		// 丙方统一社会信用代码（个人为身份证）
	@ApiModelProperty(value="丙方住所")
	private String thirdAddress;		// 丙方住所
	@ApiModelProperty(value="丙方法定代表人（丙方为企业有）")
	private String thirdLegalRepresentative;		// 丙方法定代表人（丙方为企业有）
	@ApiModelProperty(value="丙方联系方式（丙方为个人有）")
	private String thirdLinkMethod;		// 丙方联系方式（丙方为个人有）
	@ApiModelProperty(value="合同开始日期")
	private long contractStartTime = 0l;		// 合同开始日期
	@ApiModelProperty(value="合同结束日期")
	private long contractEndTime = 0l;		// 合同结束日期
	@ApiModelProperty(value="合同负责人id")
	private String contractLeaderId;		// 合同负责人id
	@ApiModelProperty(value="签约人id")
	private String signLeaderId;		// 签约人id
	@ApiModelProperty(value="归档合同状态:1有效，2过期")
	private String contractHisStatus;		// 归档合同状态
	@ApiModelProperty(value="合同类型：1业务合同，2日常合同")
	private String contractType;		// 合同类型:1业务合同，2日常合同
	@ApiModelProperty(value="归档合同方式:1自动归档，2手动归档")
	private String contractHisMethod;		// 归档合同方式:1自动归档，2手动归档

	@ApiModelProperty(value="备注")
	private String remarks;		//
	@ApiModelProperty(value="附件信息")
	private List<MainContractAttachmentRequest> contractAttachmentRequest ;
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}


	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAssociationMainId() {
		return associationMainId;
	}

	public void setAssociationMainId(String associationMainId) {
		this.associationMainId = associationMainId;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getFirstMemberName() {
		return firstMemberName;
	}

	public void setFirstMemberName(String firstMemberName) {
		this.firstMemberName = firstMemberName;
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

	public String getFirstLinkMethod() {
		return firstLinkMethod;
	}

	public void setFirstLinkMethod(String firstLinkMethod) {
		this.firstLinkMethod = firstLinkMethod;
	}

	public String getSecondMemberName() {
		return secondMemberName;
	}

	public void setSecondMemberName(String secondMemberName) {
		this.secondMemberName = secondMemberName;
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

	public String getSecondLinkMethod() {
		return secondLinkMethod;
	}

	public void setSecondLinkMethod(String secondLinkMethod) {
		this.secondLinkMethod = secondLinkMethod;
	}

	public String getThirdMemberName() {
		return thirdMemberName;
	}

	public void setThirdMemberName(String thirdMemberName) {
		this.thirdMemberName = thirdMemberName;
	}

	public String getThirdCreditCode() {
		return thirdCreditCode;
	}

	public void setThirdCreditCode(String thirdCreditCode) {
		this.thirdCreditCode = thirdCreditCode;
	}

	public String getThirdAddress() {
		return thirdAddress;
	}

	public void setThirdAddress(String thirdAddress) {
		this.thirdAddress = thirdAddress;
	}

	public String getThirdLegalRepresentative() {
		return thirdLegalRepresentative;
	}

	public void setThirdLegalRepresentative(String thirdLegalRepresentative) {
		this.thirdLegalRepresentative = thirdLegalRepresentative;
	}

	public String getThirdLinkMethod() {
		return thirdLinkMethod;
	}

	public void setThirdLinkMethod(String thirdLinkMethod) {
		this.thirdLinkMethod = thirdLinkMethod;
	}


	public long getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(long contractStartTime) {
		this.contractStartTime = contractStartTime;
	}

	public long getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(long contractEndTime) {
		this.contractEndTime = contractEndTime;
	}



	public String getContractLeaderId() {
		return contractLeaderId;
	}

	public void setContractLeaderId(String contractLeaderId) {
		this.contractLeaderId = contractLeaderId;
	}

	public String getSignLeaderId() {
		return signLeaderId;
	}

	public void setSignLeaderId(String signLeaderId) {
		this.signLeaderId = signLeaderId;
	}

	public String getContractHisStatus() {
		return contractHisStatus;
	}

	public void setContractHisStatus(String contractHisStatus) {
		this.contractHisStatus = contractHisStatus;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractHisMethod() {
		return contractHisMethod;
	}

	public void setContractHisMethod(String contractHisMethod) {
		this.contractHisMethod = contractHisMethod;
	}

	public List<MainContractAttachmentRequest> getContractAttachmentRequest() {
		return contractAttachmentRequest;
	}

	public void setContractAttachmentRequest(List<MainContractAttachmentRequest> contractAttachmentRequest) {
		this.contractAttachmentRequest = contractAttachmentRequest;
	}


	public String getAssociationMainName() {
		return associationMainName;
	}

	public void setAssociationMainName(String associationMainName) {
		this.associationMainName = associationMainName;
	}
	
	
	public List<String> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<String> projectIds) {
		this.projectIds = projectIds;
	}

	public String getContractNameId() {
		return contractNameId;
	}

	public void setContractNameId(String contractNameId) {
		this.contractNameId = contractNameId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "MainContractHisSaveRequest [id=" + id + ", contractName=" + contractName + ", contractNameId="
				+ contractNameId + ", projectIds=" + projectIds + ", version=" + version + ", associationMainId="
				+ associationMainId + ", associationMainName=" + associationMainName + ", contractCode=" + contractCode
				+ ", firstMemberName=" + firstMemberName + ", firstCreditCode=" + firstCreditCode + ", firstAddress="
				+ firstAddress + ", firstLegalRepresentative=" + firstLegalRepresentative + ", firstLinkMethod="
				+ firstLinkMethod + ", secondMemberName=" + secondMemberName + ", secondCreditCode=" + secondCreditCode
				+ ", secondAddress=" + secondAddress + ", secondLegalRepresentative=" + secondLegalRepresentative
				+ ", secondLinkMethod=" + secondLinkMethod + ", thirdMemberName=" + thirdMemberName
				+ ", thirdCreditCode=" + thirdCreditCode + ", thirdAddress=" + thirdAddress
				+ ", thirdLegalRepresentative=" + thirdLegalRepresentative + ", thirdLinkMethod=" + thirdLinkMethod
				+ ", contractStartTime=" + contractStartTime + ", contractEndTime=" + contractEndTime
				+ ", contractLeaderId=" + contractLeaderId + ", signLeaderId=" + signLeaderId + ", contractHisStatus="
				+ contractHisStatus + ", contractType=" + contractType + ", contractHisMethod=" + contractHisMethod
				+ ", remarks=" + remarks + ", contractAttachmentRequest=" + contractAttachmentRequest + "]";
	}







	
}