package sijibao.oa.jeesite.modules.intfz.response.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同归档列表返回对象
 * @author wxb
 * @time 2018-10-22 11:00:50
 */
@ApiModel(value="合同归档列表返回对象")
public class MainContractHisResponse {
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
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	@ApiModelProperty(value="甲方名称")
	private String firstMemberName;		// 甲方名称
	@ApiModelProperty(value="乙方名称")
	private String secondMemberName;		// 乙方名称
	@ApiModelProperty(value="丙方名称")
	private String thirdMemberName;		// 丙方名称
	@ApiModelProperty(value="合同开始日期")
	private long contractStartTime;		// 合同开始日期
	@ApiModelProperty(value="合同结束日期")
	private long contractEndTime;		// 合同结束日期
	@ApiModelProperty(value="合同归档时间")
	private long createTime;		// 合同归档时间
	@ApiModelProperty(value="合同负责人id")
	private String contractLeaderId;		// 合同负责人id
	@ApiModelProperty(value="合同负责人name")
	private String contractLeaderName;		// 合同负责人Name
	@ApiModelProperty(value="归档合同状态")
	private String contractHisStatus;		// 归档合同状态
	@ApiModelProperty(value="归档合同状态name")
	private String contractHisStatusName;		// 归档合同状态Name
	@ApiModelProperty(value="归档合同方式")
	private String contractHisMethod;		// 归档合同方式
	@ApiModelProperty(value="归档合同方式name")
	private String contractHisMethodName;		// 归档合同方式name
	@ApiModelProperty(value="补充协议份数")
	private int supplementaryCount;		// 补充协议份数
	@ApiModelProperty(value="是否能续签：0是，1否")
	private String contractHisRenewal;		// 是否能续签：0是，1否
	@ApiModelProperty(value="是否能废弃：0是，1否")
	private String delFlag;		// 是否能废弃：0是，1否
	@ApiModelProperty(value="回执状态name")
	private String receiptStatusName;		// 回执状态name
	
	
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
	public String getContractHisMethod() {
		return contractHisMethod;
	}
	public void setContractHisMethod(String contractHisMethod) {
		this.contractHisMethod = contractHisMethod;
	}
	public int getSupplementaryCount() {
		return supplementaryCount;
	}
	public void setSupplementaryCount(int supplementaryCount) {
		this.supplementaryCount = supplementaryCount;
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
	public String getContractHisMethodName() {
		return contractHisMethodName;
	}
	public void setContractHisMethodName(String contractHisMethodName) {
		this.contractHisMethodName = contractHisMethodName;
	}
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getContractHisRenewal() {
		return contractHisRenewal;
	}
	public void setContractHisRenewal(String contractHisRenewal) {
		this.contractHisRenewal = contractHisRenewal;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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

	@Override
	public String toString() {
		return "MainContractHisResponse{" +
				"id='" + id + '\'' +
				", contractName='" + contractName + '\'' +
				", configId='" + configId + '\'' +
				", configCode='" + configCode + '\'' +
				", version='" + version + '\'' +
				", associationMainId='" + associationMainId + '\'' +
				", contractCode='" + contractCode + '\'' +
				", firstMemberName='" + firstMemberName + '\'' +
				", secondMemberName='" + secondMemberName + '\'' +
				", thirdMemberName='" + thirdMemberName + '\'' +
				", contractStartTime=" + contractStartTime +
				", contractEndTime=" + contractEndTime +
				", createTime=" + createTime +
				", contractLeaderId='" + contractLeaderId + '\'' +
				", contractLeaderName='" + contractLeaderName + '\'' +
				", contractHisStatus='" + contractHisStatus + '\'' +
				", contractHisStatusName='" + contractHisStatusName + '\'' +
				", contractHisMethod='" + contractHisMethod + '\'' +
				", contractHisMethodName='" + contractHisMethodName + '\'' +
				", supplementaryCount=" + supplementaryCount +
				", contractHisRenewal='" + contractHisRenewal + '\'' +
				", delFlag='" + delFlag + '\'' +
				", receiptStatusName='" + receiptStatusName + '\'' +
				'}';
	}
}
