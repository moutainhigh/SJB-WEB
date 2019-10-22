package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

/**
 * 项目归档合同关联表返回对象
 * @author wxb
 * @time 2018-10-22 11:00:50
 */
public class MainProjectContractHisResponse{
	/**
	 * 
	 */
	private String id;  //主键ID
	private String contractHisId;
	private String contractCode;//合同编号
	private String contractName;		// 合同名称
	private long contractStartTime;		// 合同开始日期
	private long contractEndTime;		// 合同开始日期
	private String contractLeaderId;		// 合同负责人id
	private String contractLeader;
	private String contractHisStatus;
	private String contractHisStatusName;
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
	public String getContractLeader() {
		return contractLeader;
	}
	public void setContractLeader(String contractLeader) {
		this.contractLeader = contractLeader;
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
	
	public String getContractHisId() {
		return contractHisId;
	}
	public void setContractHisId(String contractHisId) {
		this.contractHisId = contractHisId;
	}
	@Override
	public String toString() {
		return "MainProjectContractHisResponse [id=" + id + ", contractHisId=" + contractHisId + ", contractCode="
				+ contractCode + ", contractName=" + contractName + ", contractStartTime=" + contractStartTime
				+ ", contractEndTime=" + contractEndTime + ", contractLeaderId=" + contractLeaderId
				+ ", contractLeader=" + contractLeader + ", contractHisStatus=" + contractHisStatus
				+ ", contractHisStatusName=" + contractHisStatusName + "]";
	}
	
	
	
	
}
