/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 开户管理Entity
 * @author wanxb
 * @version 2018-07-02
 */
public class OpenAccountFlow extends ActEntity<OpenAccountFlow> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String procCode;		// 流程编号
	private String procName;		// 流程名称
	private String applyPerCode;		// 申请人编号
	private String applyPerName;		// 申请人名称
	private Date applyTime;		// 申请时间
	private Office office;		// 所属部门ID
	private String officeName;		// 所属部门名称
	private String postCode;		// 所属岗位编码
	private String postName;		// 所属岗位名称
	private String openAccountCode;		// 开户编号
	private String contractCode;		// 合同编号
	private String contractName;		// 合同名称
	private String firstPartyName;		// 甲方名称
	private User marketLeader;		// 市场负责人id
	private String openAccountStatus;		// 开户状态:  0：配置中，1：可使用
	private String Status;		// 流程状态
	private Date flowFinishTime;		//流程审批结束时间
	
	private List<OpenAccountAttachment> openAccountAttachment;//附件信息
	private String marketLeaderName;//搜索市场负责人name
	private Date updateTimeStart ;//搜索开始时间
	private Date updateTimeEnd ;//搜索结束时间
	private String code;//搜索编码
	public String getApplyPerCode() {
		return applyPerCode;
	}


	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}


	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}


	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}


	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}


	public String getMarketLeaderName() {
		return marketLeaderName;
	}

	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
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

	public OpenAccountFlow() {
		super();
	}

	public OpenAccountFlow(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=16, message="流程编号长度必须介于 0 和 16 之间")
	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	
	@Length(min=0, max=300, message="流程名称长度必须介于 0 和 300 之间")
	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}
	
	@Length(min=1, max=64, message="开户编号长度必须介于 1 和 64 之间")
	public String getOpenAccountCode() {
		return openAccountCode;
	}

	public void setOpenAccountCode(String openAccountCode) {
		this.openAccountCode = openAccountCode;
	}
	
	@Length(min=1, max=64, message="合同编号长度必须介于 1 和 64 之间")
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@Length(min=1, max=64, message="合同名称长度必须介于 1 和 64 之间")
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	@Length(min=1, max=64, message="甲方名称长度必须介于 1 和 64 之间")
	public String getFirstPartyName() {
		return firstPartyName;
	}

	public void setFirstPartyName(String firstPartyName) {
		this.firstPartyName = firstPartyName;
	}
	
	
	public User getMarketLeader() {
		return marketLeader;
	}


	public void setMarketLeader(User marketLeader) {
		this.marketLeader = marketLeader;
	}


	@Length(min=1, max=8, message="开户状态长度必须介于 1 和 8 之间")
	public String getOpenAccountStatus() {
		return openAccountStatus;
	}

	public void setOpenAccountStatus(String openAccountStatus) {
		this.openAccountStatus = openAccountStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="创建时间不能为空")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Date getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(Date flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}

	public List<OpenAccountAttachment> getOpenAccountAttachment() {
		return openAccountAttachment;
	}

	public void setOpenAccountAttachment(List<OpenAccountAttachment> openAccountAttachment) {
		this.openAccountAttachment = openAccountAttachment;
	}
	

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	@Override
	public String toString() {
		return "OpenAccountFlow [procInsId=" + procInsId + ", procCode=" + procCode + ", procName=" + procName
				+ ", applyPerCode=" + applyPerCode + ", applyPerName=" + applyPerName + ", applyTime=" + applyTime
				+ ", office=" + office + ", officeName=" + officeName + ", postCode=" + postCode + ", postName="
				+ postName + ", openAccountCode=" + openAccountCode + ", contractCode=" + contractCode
				+ ", contractName=" + contractName + ", firstPartyName=" + firstPartyName + ", marketLeader="
				+ marketLeader + ", openAccountStatus=" + openAccountStatus + ", Status=" + Status + ", flowFinishTime="
				+ flowFinishTime + ", openAccountAttachment=" + openAccountAttachment + ", marketLeaderName="
				+ marketLeaderName + ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd
				+ ", code=" + code + "]";
	}


}