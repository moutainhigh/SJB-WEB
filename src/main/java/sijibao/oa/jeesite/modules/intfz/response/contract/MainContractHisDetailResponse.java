package sijibao.oa.jeesite.modules.intfz.response.contract;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.contractnew.ContractFlowDetailPartyMainResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同归档详情返回对象
 * @author wxb
 * @time 2018-10-22 11:00:50
 */
@ApiModel(value="合同归档详情返回对象")
public class MainContractHisDetailResponse {
	@ApiModelProperty(value="主键ID")
	private String id;  //主键ID
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	@ApiModelProperty(value="合同模板id")
	private String configId;		// 合同模板id
	@ApiModelProperty(value="合同模板code")
	private String configCode;// 合同模板code
	@ApiModelProperty(value="模版版本号")
	private String version;		// 模版版本号
	@ApiModelProperty(value="主合同id")
	private String associationMainId;		// 主合同id
	@ApiModelProperty(value="主合同Name")
	private String associationMainName;		// 主合同name
	@ApiModelProperty(value="主合同code")
	private String associationMainCode;		// 主合同code
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	
	@ApiModelProperty(value="合同开始日期")
	private long contractStartTime;		// 合同开始日期
	@ApiModelProperty(value="合同结束日期")
	private long contractEndTime;		// 合同结束日期
	@ApiModelProperty(value="合同负责人id")
	private String contractLeaderId;		// 合同负责人id
	@ApiModelProperty(value="合同负责人name")
	private String contractLeaderName;		// 合同负责人Name
	@ApiModelProperty(value="签约人id")
	private String signLeaderId;		// 签约人id
	@ApiModelProperty(value="签约人Name")
	private String signLeaderName;		// 签约人Name
	@ApiModelProperty(value="归档合同状态")
	private String contractHisStatus;		// 归档合同状态
	@ApiModelProperty(value="归档合同状态name")
	private String contractHisStatusName;		// 归档合同状态Name
	@ApiModelProperty(value="合同类型")
	private String contractType;		// 合同类型
	@ApiModelProperty(value="合同类型name")
	private String contractTypeName;		// 合同类型name
	
	@ApiModelProperty(value="合同归档时间")
	private long createTime;		// 合同归档时间
	@ApiModelProperty(value="归档合同方式")
	private String contractHisMethod;		// 归档合同方式
	@ApiModelProperty(value="归档合同方式name")
	private String contractHisMethodName;		// 归档合同方式name
	
	@ApiModelProperty(value="合同保管人员")
	private String custodian;		// 合同保管人员
	
	
	
	@ApiModelProperty(value="附件信息")
	private List<ContractAttachmentResponse> contractAttachmentResponse ;
	@ApiModelProperty(value="合同方字段返回对象")
	private List<ContractFlowDetailPartyMainResponse> contractPartyList; //合同方字段返回对象
	@ApiModelProperty(value="备注")
	private String remarks;
	@ApiModelProperty(value="项目id")
	private List<String> projectIds;		//项目ids
	@ApiModelProperty(value="项目信息")
	private List<MainContractHisDetailProjectNewResponse> projectList;
	@ApiModelProperty(value="补充合同")
	private List<MainContractSuppResp> suppResp;
	@ApiModelProperty(value="回执状态name")
	private String receiptStatusName;		// 回执状态name
	@ApiModelProperty(value="回执提交人name")
	private String 	personName;//回执提交人name
	@ApiModelProperty(value="回执提交人手机号")
	private String personPhone;//回执提交人手机号
	@ApiModelProperty(value="回执提交时间")
	private long receiptTime; //回执提交时间
	@ApiModelProperty(value="按钮：0不显示，1显示")
	private String button;		//按钮：0不显示，1显示




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
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
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
	public String getContractHisStatus() {
		return contractHisStatus;
	}
	public void setContractHisStatus(String contractHisStatus) {
		this.contractHisStatus = contractHisStatus;
	}
	public String getContractHisStatusName() {
		return contractHisStatusName;
	}
	public void setContractHisStatusName(String contractHisStatusName) {
		this.contractHisStatusName = contractHisStatusName;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getContractTypeName() {
		return contractTypeName;
	}
	public void setContractTypeName(String contractTypeName) {
		this.contractTypeName = contractTypeName;
	}
	public List<ContractAttachmentResponse> getContractAttachmentResponse() {
		return contractAttachmentResponse;
	}
	public void setContractAttachmentResponse(List<ContractAttachmentResponse> contractAttachmentResponse) {
		this.contractAttachmentResponse = contractAttachmentResponse;
	}
	public List<ContractFlowDetailPartyMainResponse> getContractPartyList() {
		return contractPartyList;
	}
	public void setContractPartyList(List<ContractFlowDetailPartyMainResponse> contractPartyList) {
		this.contractPartyList = contractPartyList;
	}
	public List<String> getProjectIds() {
		return projectIds;
	}
	public void setProjectIds(List<String> projectIds) {
		this.projectIds = projectIds;
	}
	public List<MainContractSuppResp> getSuppResp() {
		return suppResp;
	}
	public void setSuppResp(List<MainContractSuppResp> suppResp) {
		this.suppResp = suppResp;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	
	public String getAssociationMainName() {
		return associationMainName;
	}
	public void setAssociationMainName(String associationMainName) {
		this.associationMainName = associationMainName;
	}
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getContractHisMethod() {
		return contractHisMethod;
	}
	public void setContractHisMethod(String contractHisMethod) {
		this.contractHisMethod = contractHisMethod;
	}
	public String getContractHisMethodName() {
		return contractHisMethodName;
	}
	public void setContractHisMethodName(String contractHisMethodName) {
		this.contractHisMethodName = contractHisMethodName;
	}
	public String getCustodian() {
		return custodian;
	}
	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}
	public String getAssociationMainCode() {
		return associationMainCode;
	}
	public void setAssociationMainCode(String associationMainCode) {
		this.associationMainCode = associationMainCode;
	}
	public List<MainContractHisDetailProjectNewResponse> getProjectList() {
		return projectList;
	}
	public void setProjectList(List<MainContractHisDetailProjectNewResponse> projectList) {
		this.projectList = projectList;
	}
	
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getReceiptStatusName() {
		return receiptStatusName;
	}

	public void setReceiptStatusName(String receiptStatusName) {
		this.receiptStatusName = receiptStatusName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonPhone() {
		return personPhone;
	}

	public void setPersonPhone(String personPhone) {
		this.personPhone = personPhone;
	}

	public long getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(long receiptTime) {
		this.receiptTime = receiptTime;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	@Override
	public String toString() {
		return "MainContractHisDetailResponse{" +
				"id='" + id + '\'' +
				", contractName='" + contractName + '\'' +
				", configId='" + configId + '\'' +
				", configCode='" + configCode + '\'' +
				", version='" + version + '\'' +
				", associationMainId='" + associationMainId + '\'' +
				", associationMainName='" + associationMainName + '\'' +
				", associationMainCode='" + associationMainCode + '\'' +
				", contractCode='" + contractCode + '\'' +
				", contractStartTime=" + contractStartTime +
				", contractEndTime=" + contractEndTime +
				", contractLeaderId='" + contractLeaderId + '\'' +
				", contractLeaderName='" + contractLeaderName + '\'' +
				", signLeaderId='" + signLeaderId + '\'' +
				", signLeaderName='" + signLeaderName + '\'' +
				", contractHisStatus='" + contractHisStatus + '\'' +
				", contractHisStatusName='" + contractHisStatusName + '\'' +
				", contractType='" + contractType + '\'' +
				", contractTypeName='" + contractTypeName + '\'' +
				", createTime=" + createTime +
				", contractHisMethod='" + contractHisMethod + '\'' +
				", contractHisMethodName='" + contractHisMethodName + '\'' +
				", custodian='" + custodian + '\'' +
				", contractAttachmentResponse=" + contractAttachmentResponse +
				", contractPartyList=" + contractPartyList +
				", remarks='" + remarks + '\'' +
				", projectIds=" + projectIds +
				", projectList=" + projectList +
				", suppResp=" + suppResp +
				", receiptStatusName='" + receiptStatusName + '\'' +
				", personName='" + personName + '\'' +
				", personPhone='" + personPhone + '\'' +
				", receiptTime=" + receiptTime +
				", button='" + button + '\'' +
				'}';
	}
}
