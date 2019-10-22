package sijibao.oa.jeesite.modules.intfz.response.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同逾期报表返回对象
 * @author huangkai
 * @date 2019-03-14
 */
@ApiModel(value="合同逾期报表返回对象")
public class ContractOverdueResponse {
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
	@ApiModelProperty(value="所属部门名称")
	private String officeName; //部门名称
	@ApiModelProperty(value="合同编号")
	private String contractCode;		// 合同编号
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	@ApiModelProperty(value="甲方名称")
	private String firstPartyName;		// 甲方名称
	@ApiModelProperty(value="乙方名称")
	private String secondPartyName;		// 乙方名称
	@ApiModelProperty(value="丙方名称")
	private String thirdMemberName;		//丙方名称
	@ApiModelProperty(value="合同状态")
	private String contractFlowStatus;		// 合同状态
	@ApiModelProperty(value="开户时间")
	private long openAccountTime ;//开户时间
	@ApiModelProperty(value="合同负责人id")
	private String contractLeaderId;		// 合同负责人id
	@ApiModelProperty(value="合同负责人名称")
	private String contractLeaderName;		// 合同负责人名称
	@ApiModelProperty(value="逾期天数")
	private String overdueDays;		// 逾期天数

	@Override
	public String toString() {
		return "ContractOverdueResponse{" +
				"procInsId='" + procInsId + '\'' +
				", procCode='" + procCode + '\'' +
				", procName='" + procName + '\'' +
				", applyPerCode='" + applyPerCode + '\'' +
				", applyPerName='" + applyPerName + '\'' +
				", officeName='" + officeName + '\'' +
				", contractCode='" + contractCode + '\'' +
				", contractName='" + contractName + '\'' +
				", firstPartyName='" + firstPartyName + '\'' +
				", secondPartyName='" + secondPartyName + '\'' +
				", thirdMemberName='" + thirdMemberName + '\'' +
				", contractFlowStatus='" + contractFlowStatus + '\'' +
				", openAccountTime=" + openAccountTime +
				", contractLeaderId='" + contractLeaderId + '\'' +
				", contractLeaderName='" + contractLeaderName + '\'' +
				", overdueDays='" + overdueDays + '\'' +
				'}';
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
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

	public String getSecondPartyName() {
		return secondPartyName;
	}

	public void setSecondPartyName(String secondPartyName) {
		this.secondPartyName = secondPartyName;
	}

	public String getThirdMemberName() {
		return thirdMemberName;
	}

	public void setThirdMemberName(String thirdMemberName) {
		this.thirdMemberName = thirdMemberName;
	}

	public String getContractFlowStatus() {
		return contractFlowStatus;
	}

	public void setContractFlowStatus(String contractFlowStatus) {
		this.contractFlowStatus = contractFlowStatus;
	}

	public long getOpenAccountTime() {
		return openAccountTime;
	}

	public void setOpenAccountTime(long openAccountTime) {
		this.openAccountTime = openAccountTime;
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


	public String getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}
}
