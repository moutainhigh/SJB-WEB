package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import java.util.Date;
import java.util.List;

/**
 * 合同详情展示返回对象
 * @author xby
 * @version 2018-11-12
 */
public class ContractFlowDetailInfoNewMainResponse{
	
	private String id; //主键ID
	
	private String procInsId;		// 流程实例ID
	
	private String procCode;		// 流程编号
	
	private String procName;		// 流程名称
	
	private String[] projectIds; //项目ID
	
	private List<ContractFlowDetailProjectNewMainResponse> projectList;
	
	private String applyPerCode;		// 申请人编号
	
	private String applyPerName;		// 申请人名称
	
	private Date applyTime;		// 申请时间

	private String officeId;		// 所属部门ID
	
	private String officeName;		// 所属部门名称
	
	private String associationMainId = "";		// 主合同id
	
	private String associationMainName = "";		// 主合同名称
	
	private String associationMainCode = ""; //主合同编号
	
	private String contractCode;		// 合同编号
	
	private String contractName;		// 合同名称
	
	private String configId;		// 合同名称模版ID
	
	private String configCode;		// 合同名称模版code
	
	private String contractType; //合同类型
	
	private String contractTypeTxt; //合同类型文案
	
	private long contractStartTime; //合同开始日期
	
	private long contractEndTime; //合同结束日期
	
	private String contractLeaderId; //合同负责人ID
	
	private String contractLeaderName; //合同负责人名称
	
	private String signLeaderId; //合同签约人ID
	
	private String signLeaderName; //合同签约人名称
	
	private String expressCompany;		// 快递公司
	
	private String expressBill;		// 快递单号
	
	private String remarks; 
	
	private long createTime;		// 提交时间
	
	private String contractFlowStatus;		// 审批状态
	
	private String contractFlowStatusTxt;		// 审批状态文案
	
	private long flowFinishTime;		// 流程审批结束时间
	
	private List<ContractFlowDetailPartyMainResponse> contractPartyList; //合同方字段返回对象
	
	private List<ContractAttachmentNewResponse> contractAttachmentList;//附件信息
	
	private int isBack;			//当前环节是否可以驳回
	private int isCancel;       //当前环节是否可以撤回
	private int isUploadFile;   //是否可以上传扫描件
	private String modify;      //编辑节点
	
	public ContractFlowDetailInfoNewMainResponse(){}
	
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
	public List<ContractFlowDetailPartyMainResponse> getContractPartyList() {
		return contractPartyList;
	}
	public void setContractPartyList(List<ContractFlowDetailPartyMainResponse> contractPartyList) {
		this.contractPartyList = contractPartyList;
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

	public String[] getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(String[] projectIds) {
		this.projectIds = projectIds;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractTypeTxt() {
		return contractTypeTxt;
	}

	public void setContractTypeTxt(String contractTypeTxt) {
		this.contractTypeTxt = contractTypeTxt;
	}

	public String getAssociationMainId() {
		return associationMainId;
	}

	public void setAssociationMainId(String associationMainId) {
		this.associationMainId = associationMainId;
	}

	public String getAssociationMainName() {
		return associationMainName;
	}

	public void setAssociationMainName(String associationMainName) {
		this.associationMainName = associationMainName;
	}

	public String getAssociationMainCode() {
		return associationMainCode;
	}

	public void setAssociationMainCode(String associationMainCode) {
		this.associationMainCode = associationMainCode;
	}

	public List<ContractFlowDetailProjectNewMainResponse> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ContractFlowDetailProjectNewMainResponse> projectList) {
		this.projectList = projectList;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	
}
