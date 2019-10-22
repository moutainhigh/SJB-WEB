/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程返回对象
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同管理流程返回对象")
public class ContractFlowNewMainResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="主键ID")
	private String id; //主键ID
	
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	
	@ApiModelProperty(value="申请人编号")
	private String applyPerCode;		// 申请人编号
	
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	
	@ApiModelProperty(value="申请人时间")
	private long applyTime;		// 申请时间
	
	@ApiModelProperty(value="所属部门ID")
	private String officeId;		// 所属部门ID
	
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		// 所属部门名称
	
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	
	@ApiModelProperty(value="合同名称模版ID")
	private String configId;		// 合同名称模版ID
	
	@ApiModelProperty(value="合同名称模版code")
	private String configCode;		// 合同名称模版code
	
	@ApiModelProperty(value="甲方名称")
	private String firstMemberName;		// 甲方名称
	
	@ApiModelProperty(value="甲方统一社会信用代码")
	private String firstCreditCode;		// 甲方统一社会信用代码
	
	@ApiModelProperty(value="甲方住所")
	private String firstAddress;		// 甲方住所
	
	@ApiModelProperty(value="甲方法定代表人")
	private String firstLegalRepresentative;		// 甲方法定代表人
	
	@ApiModelProperty(value="甲方联系方式")
	private String firstLinkMethod;		// 甲方联系方式
	
	@ApiModelProperty(value="乙方名称")
	private String secondMemberName;		// 乙方名称
	
	@ApiModelProperty(value="乙方同意社会信用代码")
	private String secondCreditCode;		// 乙方统一社会信用代码
	
	@ApiModelProperty(value="乙方住所")
	private String secondAddress;		// 乙方住所
	
	@ApiModelProperty(value="乙方法定代表人")
	private String secondLegalRepresentative;		// 乙方法定代表人
	
	@ApiModelProperty(value="乙方联系方式")
	private String secondLinkMethod;		// 乙方联系方式
	
	@ApiModelProperty(value="丙方名称")
	private String thirdMemberName;		// 乙方名称
	
	@ApiModelProperty(value="丙方同意社会信用代码")
	private String thirdCreditCode;		// 乙方统一社会信用代码
	
	@ApiModelProperty(value="丙方住所")
	private String thirdAddress;		// 乙方住所
	
	@ApiModelProperty(value="丙方法定代表人")
	private String thirdLegalRepresentative;		// 乙方法定代表人
	
	@ApiModelProperty(value="丙方联系方式")
	private String thirdLinkMethod;		// 乙方联系方式
	
	@ApiModelProperty(value="开始日期")
	private long contractStartTime; //合同开始日期
	
	@ApiModelProperty(value="结束日期")
	private long contractEndTime; //合同结束日期
	
	@ApiModelProperty(value="合同负责人ID")
	private String contractLeaderId; //合同负责人ID
	
	@ApiModelProperty(value="合同负责人名称")
	private String contractLeaderName; //合同负责人名称
	
	@ApiModelProperty(value="合同签约人ID")
	private String signLeaderId; //合同签约人ID
	
	@ApiModelProperty(value="合同签约人名称")
	private String signLeaderName; //合同签约人名称
	
	@ApiModelProperty(value="快递公司")
	private String expressCompany;		// 快递公司
	
	@ApiModelProperty(value="快递单号")
	private String expressBill;		// 快递单号
	
	@ApiModelProperty(value="备注")
	private String remarks; 
	
	@ApiModelProperty(value="提交时间")
	private long createTime;		// 提交时间
	
	@ApiModelProperty(value="审批状态")
	private String contractFlowStatus;		// 审批状态
	
	@ApiModelProperty(value="审批状态文案")
	private String contractFlowStatusTxt;		// 审批状态
	
	@ApiModelProperty(value="审批结束时间")
	private long flowFinishTime;		// 流程审批结束时间
	
	@ApiModelProperty(value="合同附件信息")
	private List<ContractAttachmentNewResponse> contractAttachmentList;//附件信息
	
	@ApiModelProperty(value="是否可驳回:1是0否")
	private int isBack;			//当前环节是否可以驳回
	@ApiModelProperty(value="是否可撤回:1是0否")
	private int isCancel;       //当前环节是否可以撤回
	@ApiModelProperty(value="是否可上传扫描件:1是0否")
	private int isUploadFile;   //是否可以上传扫描件
	@ApiModelProperty(value="编辑节点")
	private String modify;      //编辑节点
	
	public ContractFlowNewMainResponse(){}
	
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

	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public String getApplyPerCode() {
		return applyPerCode;
	}

	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}

	public String getApplyPerName() {
		return applyPerName;
	}

	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}

	public long getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(long applyTime) {
		this.applyTime = applyTime;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
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

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getContractFlowStatus() {
		return contractFlowStatus;
	}

	public void setContractFlowStatus(String contractFlowStatus) {
		this.contractFlowStatus = contractFlowStatus;
	}

	public String getContractFlowStatusTxt() {
		return contractFlowStatusTxt;
	}

	public void setContractFlowStatusTxt(String contractFlowStatusTxt) {
		this.contractFlowStatusTxt = contractFlowStatusTxt;
	}

	public long getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(long flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}

	public List<ContractAttachmentNewResponse> getContractAttachmentList() {
		return contractAttachmentList;
	}

	public void setContractAttachmentList(List<ContractAttachmentNewResponse> contractAttachmentList) {
		this.contractAttachmentList = contractAttachmentList;
	}

	public int getIsBack() {
		return isBack;
	}

	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}

	public int getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(int isCancel) {
		this.isCancel = isCancel;
	}

	public int getIsUploadFile() {
		return isUploadFile;
	}

	public void setIsUploadFile(int isUploadFile) {
		this.isUploadFile = isUploadFile;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public String getContractLeaderName() {
		return contractLeaderName;
	}

	public void setContractLeaderName(String contractLeaderName) {
		this.contractLeaderName = contractLeaderName;
	}

	public String getSignLeaderId() {
		return signLeaderId;
	}

	public void setSignLeaderId(String signLeaderId) {
		this.signLeaderId = signLeaderId;
	}

	public String getSignLeaderName() {
		return signLeaderName;
	}

	public void setSignLeaderName(String signLeaderName) {
		this.signLeaderName = signLeaderName;
	}

	public String getFirstMemberName() {
		return firstMemberName;
	}

	public void setFirstMemberName(String firstMemberName) {
		this.firstMemberName = firstMemberName;
	}

	public String getSecondMemberName() {
		return secondMemberName;
	}

	public void setSecondMemberName(String secondMemberName) {
		this.secondMemberName = secondMemberName;
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

	public String getFirstLinkMethod() {
		return firstLinkMethod;
	}

	public void setFirstLinkMethod(String firstLinkMethod) {
		this.firstLinkMethod = firstLinkMethod;
	}

	public String getSecondLinkMethod() {
		return secondLinkMethod;
	}

	public void setSecondLinkMethod(String secondLinkMethod) {
		this.secondLinkMethod = secondLinkMethod;
	}

	public String getThirdLinkMethod() {
		return thirdLinkMethod;
	}

	public void setThirdLinkMethod(String thirdLinkMethod) {
		this.thirdLinkMethod = thirdLinkMethod;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	@Override
	public String toString() {
		return "ContractFlowNewMainResponse [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", procName=" + procName + ", applyPerCode=" + applyPerCode + ", applyPerName=" + applyPerName
				+ ", applyTime=" + applyTime + ", officeId=" + officeId + ", officeName=" + officeName
				+ ", contractCode=" + contractCode + ", contractName=" + contractName + ", configId=" + configId
				+ ", configCode=" + configCode + ", firstMemberName=" + firstMemberName + ", firstCreditCode="
				+ firstCreditCode + ", firstAddress=" + firstAddress + ", firstLegalRepresentative="
				+ firstLegalRepresentative + ", firstLinkMethod=" + firstLinkMethod + ", secondMemberName="
				+ secondMemberName + ", secondCreditCode=" + secondCreditCode + ", secondAddress=" + secondAddress
				+ ", secondLegalRepresentative=" + secondLegalRepresentative + ", secondLinkMethod=" + secondLinkMethod
				+ ", thirdMemberName=" + thirdMemberName + ", thirdCreditCode=" + thirdCreditCode + ", thirdAddress="
				+ thirdAddress + ", thirdLegalRepresentative=" + thirdLegalRepresentative + ", thirdLinkMethod="
				+ thirdLinkMethod + ", contractStartTime=" + contractStartTime + ", contractEndTime=" + contractEndTime
				+ ", contractLeaderId=" + contractLeaderId + ", contractLeaderName=" + contractLeaderName
				+ ", signLeaderId=" + signLeaderId + ", signLeaderName=" + signLeaderName + ", expressCompany="
				+ expressCompany + ", expressBill=" + expressBill + ", remarks=" + remarks + ", createTime="
				+ createTime + ", contractFlowStatus=" + contractFlowStatus + ", contractFlowStatusTxt="
				+ contractFlowStatusTxt + ", flowFinishTime=" + flowFinishTime + ", contractAttachmentList="
				+ contractAttachmentList + ", isBack=" + isBack + ", isCancel=" + isCancel + ", isUploadFile="
				+ isUploadFile + ", modify=" + modify + "]";
	}

	
}