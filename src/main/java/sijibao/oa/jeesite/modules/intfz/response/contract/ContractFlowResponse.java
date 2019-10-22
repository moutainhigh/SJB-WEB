/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.contract;

import java.util.Date;
import java.util.List;

import com.sijibao.oa.modules.flow.entity.ContractFlow;
import com.sijibao.oa.modules.sys.utils.DictUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程返回对象
 * @author xby
 * @version 2018-07-12
 */
@ApiModel(value="合同管理流程返回对象")
public class ContractFlowResponse{
	
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
	private Date applyTime;		// 申请时间
	
	@ApiModelProperty(value="所属部门ID")
	private String officeId;		// 所属部门ID
	
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		// 所属部门名称
	
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	
	@ApiModelProperty(value="合同名称编码")
	private String contractNameCode;		// 合同名称编码
	
	@ApiModelProperty(value="客户ID")
	private String custId;       //客户ID
	
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
	private long contractDate;		// 签约日期
	
	@ApiModelProperty(value="快递公司")
	private String expressCompany;		// 快递公司
	
	@ApiModelProperty(value="快递单号")
	private String expressBill;		// 快递单号
	
	@ApiModelProperty(value="备注")
	private String remarks; 
	
	@ApiModelProperty(value="市场负责人ID")
	private String marketLeaderId;		// 市场负责人id
	
	@ApiModelProperty(value="市场负责人ID")
	private String marketLeaderName;		// 市场负责人名称
	
	@ApiModelProperty(value="提交时间")
	private long createTime;		// 提交时间
	
	@ApiModelProperty(value="审批状态")
	private String contractFlowStatus;		// 审批状态
	
	@ApiModelProperty(value="审批状态文案")
	private String contractFlowStatusTxt;		// 审批状态
	
	@ApiModelProperty(value="审批结束时间")
	private long flowFinishTime;		// 流程审批结束时间
	
	@ApiModelProperty(value="合同附件信息")
	private List<ContractAttachmentResponse> contractAttachmentList;//附件信息
	
	@ApiModelProperty(value="是否可驳回:1是0否")
	private int isBack;			//当前环节是否可以驳回
	@ApiModelProperty(value="是否可撤回:1是0否")
	private int isCancel;       //当前环节是否可以撤回
	@ApiModelProperty(value="是否可上传扫描件:1是0否")
	private int isUploadFile;   //是否可以上传扫描件
	@ApiModelProperty(value="编辑节点")
	private String modify;      //编辑节点
	public ContractFlowResponse(ContractFlow contractFlow){
		
		this.id = contractFlow.getId(); //主键ID
		
		this.procInsId = contractFlow.getProcInsId(); // 流程实例ID
		
		this.procCode = contractFlow.getProcCode();		// 流程编号
		
		this.procName = contractFlow.getProcName();		// 流程名称
		
		this.applyPerCode = contractFlow.getApplyPerCode();		// 申请人编号
		
		this.applyPerName = contractFlow.getApplyPerName();		// 申请人名称
		
		this.applyTime = contractFlow.getApplyTime();		// 申请时间
		
		this.officeId = contractFlow.getOffice().getId();		// 所属部门ID
		
		this.officeName = contractFlow.getOfficeName();		// 所属部门名称
		
		this.contractCode = contractFlow.getContractCode();		// 合同编号
		
		this.contractName = contractFlow.getContractName();		// 合同名称
		
		this.contractNameCode = contractFlow.getContractNameCode();   //合同名称编码
		
		this.custId = contractFlow.getCustId();    //客户ID
		
		this.firstPartyName = contractFlow.getFirstPartyName();		// 甲方名称
		
		this.firstCreditCode = contractFlow.getFirstCreditCode();		// 甲方统一社会信用代码
		
		this.firstAddress = contractFlow.getFirstAddress();		// 甲方住所
		
		this.firstLegalRepresentative = contractFlow.getFirstLegalRepresentative();		// 甲方法定代表人
		
		this.firstLinkman = contractFlow.getFirstLinkman();		// 甲方联系人
		
		this.firstLinkmanPhone = contractFlow.getFirstLinkmanPhone();		// 甲方联系人电话
		
		this.firstLinkmanMail = contractFlow.getFirstLinkmanMail();		// 甲方联系人邮箱
		
		this.secondPartyName = contractFlow.getSecondPartyName();		// 乙方名称
		
		this.secondCreditCode = contractFlow.getSecondCreditCode();		// 乙方统一社会信用代码
		
		this.secondAddress = contractFlow.getSecondAddress();		// 乙方住所
		
		this.secondLegalRepresentative = contractFlow.getSecondLegalRepresentative();		// 乙方法定代表人
		
		this.secondLinkman = contractFlow.getSecondLinkman();		// 乙方联系人
		
		this.secondLinkmanPhone = contractFlow.getSecondLinkmanPhone();		// 乙方联系人电话
		
		this.secondLinkmanMail = contractFlow.getSecondLinkmanMail();		// 乙方联系人邮箱
		
		this.dispatchProportion = contractFlow.getDispatchProportion();		// 调度费比例
		
		this.penaltyProportion = contractFlow.getPenaltyProportion();		// 违约金比例
		
		this.validityDate = contractFlow.getValidityDate();		// 有效期（年）
		
		this.contractDate = contractFlow.getContractDate().getTime();		// 签约日期
		
		this.expressCompany = contractFlow.getExpressCompany();		// 快递公司
		
		this.expressBill = contractFlow.getExpressBill();		// 快递单号
		
		this.remarks = contractFlow.getRemarks(); //备注 
		
		this.marketLeaderId = contractFlow.getMarketLeaderId();		// 市场负责人id
		
		this.marketLeaderName = contractFlow.getMarketLeaderName();		// 市场负责人名称
		
		if(contractFlow.getCreateTime() != null){
			this.createTime = contractFlow.getCreateTime().getTime();		// 提交时间
		}
		
		this.contractFlowStatus = contractFlow.getContractFlowStatus();		// 审批状态
		
		this.contractFlowStatusTxt = DictUtils.getDictLabel(contractFlow.getContractFlowStatus(), "expense_status","");		// 审批状态文案
		
		if(contractFlow.getFlowFinishTime() != null){
			this.flowFinishTime = contractFlow.getFlowFinishTime().getTime();		// 流程审批结束时间
		}
	}
	
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

	public List<ContractAttachmentResponse> getContractAttachmentList() {
		return contractAttachmentList;
	}

	public void setContractAttachmentList(List<ContractAttachmentResponse> contractAttachmentList) {
		this.contractAttachmentList = contractAttachmentList;
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

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
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

	public String getMarketLeaderId() {
		return marketLeaderId;
	}

	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}

	public String getMarketLeaderName() {
		return marketLeaderName;
	}

	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
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

	public long getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(long flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}

	public String getContractNameCode() {
		return contractNameCode;
	}

	public void setContractNameCode(String contractNameCode) {
		this.contractNameCode = contractNameCode;
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

	public String getContractFlowStatusTxt() {
		return contractFlowStatusTxt;
	}

	public void setContractFlowStatusTxt(String contractFlowStatusTxt) {
		this.contractFlowStatusTxt = contractFlowStatusTxt;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	@Override
	public String toString() {
		return "ContractFlowResponse [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode + ", procName="
				+ procName + ", applyPerCode=" + applyPerCode + ", applyPerName=" + applyPerName + ", applyTime="
				+ applyTime + ", officeId=" + officeId + ", officeName=" + officeName + ", contractCode=" + contractCode
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
				+ ", marketLeaderId=" + marketLeaderId + ", marketLeaderName=" + marketLeaderName + ", createTime="
				+ createTime + ", contractFlowStatus=" + contractFlowStatus + ", contractFlowStatusTxt="
				+ contractFlowStatusTxt + ", flowFinishTime=" + flowFinishTime + ", contractAttachmentList="
				+ contractAttachmentList + ", isBack=" + isBack + ", isCancel=" + isCancel + ", isUploadFile="
				+ isUploadFile + ", modify=" + modify + "]";
	}

}