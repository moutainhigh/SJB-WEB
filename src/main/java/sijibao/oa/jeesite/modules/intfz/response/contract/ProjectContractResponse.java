package sijibao.oa.jeesite.modules.intfz.response.contract;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同管理流程详情对象
 * @author wxb
 * @time 2018-10-22 11:00:50
 */
@ApiModel(value="合同管理流程详情对象")
public class ProjectContractResponse {
	
	@ApiModelProperty(value="归档合同id")
	private String ContractHisId;  //归档合同id
	@ApiModelProperty(value="合同名称")
	private String contractName;		// 合同名称
	@ApiModelProperty(value="合同开始日期")
	private Date contractStartTime;		// 合同开始日期
	@ApiModelProperty(value="合同结束日期")
	private Date contractEndTime;		// 合同结束日期
	@ApiModelProperty(value="合同负责人id")
	private String contractLeaderId;		// 合同负责人id
	@ApiModelProperty(value="合同负责人name")
	private String contractLeaderName;		// 合同负责人Name
	@ApiModelProperty(value="归档合同状态")
	private String contractHisStatus;		// 归档合同状态
	@ApiModelProperty(value="归档合同状态name")
	private String contractHisStatusName;		// 归档合同状态Name
	public String getContractHisId() {
		return ContractHisId;
	}
	public void setContractHisId(String contractHisId) {
		ContractHisId = contractHisId;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public Date getContractStartTime() {
		return contractStartTime;
	}
	public void setContractStartTime(Date contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	public Date getContractEndTime() {
		return contractEndTime;
	}
	public void setContractEndTime(Date contractEndTime) {
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
	@Override
	public String toString() {
		return "ProjectContractResponse [ContractHisId=" + ContractHisId + ", contractName=" + contractName
				+ ", contractStartTime=" + contractStartTime + ", contractEndTime=" + contractEndTime
				+ ", contractLeaderId=" + contractLeaderId + ", contractLeaderName=" + contractLeaderName
				+ ", contractHisStatus=" + contractHisStatus + ", contractHisStatusName=" + contractHisStatusName + "]";
	}
	
	
}
