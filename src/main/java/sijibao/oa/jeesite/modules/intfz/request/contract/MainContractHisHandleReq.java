package sijibao.oa.jeesite.modules.intfz.request.contract;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同归档列表请求对象
 * @author wanxb
 * 2018-10-22 14:33:02
 */
@ApiModel(value="合同归档列表请求对象")
public class MainContractHisHandleReq extends PageRequest{

	@ApiModelProperty(value="模糊搜索字段")
	private String faint;//模糊搜索字段
	@ApiModelProperty(value="合同id")
	private String contractId;//合同id
	@ApiModelProperty(value="合同负责人id")
	private String contractLeaderId;//合同负责人id
	@ApiModelProperty(value="合同负责人name")
	private String contractLeaderName;//合同负责人name
	@ApiModelProperty(value="合同开始日期")
	private long startTime = 0l;		// 合同开始日期
	@ApiModelProperty(value="合同结束日期")
	private long endTime = 0l;		// 合同结束日期
	@ApiModelProperty(value="归档合同状态")
	private String contractHisStatus;		// 归档合同状态
	@ApiModelProperty(value="合同类型")
	private String contractType;		// 合同类型
	@ApiModelProperty(value="时间类型")
	private String dateType;		// 时间类型
	@ApiModelProperty(value="数据权限：1有")
	private String sqlFlag;		// 数据权限：1有
	public String getFaint() {
		return faint;
	}
	public void setFaint(String faint) {
		this.faint = faint;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContractLeaderId() {
		return contractLeaderId;
	}
	public void setContractLeaderId(String contractLeaderId) {
		this.contractLeaderId = contractLeaderId;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
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

	public String getDateType() {
		return dateType;
	}

	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	public String getSqlFlag() {
		return sqlFlag;
	}

	public void setSqlFlag(String sqlFlag) {
		this.sqlFlag = sqlFlag;
	}

	public String getContractLeaderName() {
		return contractLeaderName;
	}

	public void setContractLeaderName(String contractLeaderName) {
		this.contractLeaderName = contractLeaderName;
	}

	@Override
	public String toString() {
		return "MainContractHisHandleReq{" +
				"faint='" + faint + '\'' +
				", contractId='" + contractId + '\'' +
				", contractLeaderId='" + contractLeaderId + '\'' +
				", contractLeaderName='" + contractLeaderName + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", contractHisStatus='" + contractHisStatus + '\'' +
				", contractType='" + contractType + '\'' +
				", dateType='" + dateType + '\'' +
				", sqlFlag='" + sqlFlag + '\'' +
				'}';
	}
}
