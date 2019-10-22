/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 合同逾期Entity
 * @author huangkai
 * @version 2019-03-04
 */
public class ContractOverdue extends ActEntity<ContractOverdue> {

	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String procCode;		// 流程编号
	private String procName;		// 流程名称
	private String applyPerCode;		// 申请人编号
	private String applyPerName;		// 申请人名称
	private Office office;		// 所属部门ID
	private String officeName;		// 所属部门名称
	private String contractCode;		// 合同编号
	private String contractName;		// 合同名称
	private String contractNameCode;		// 合同名称编码
	private String firstMemberName;		// 甲方名称
	private String secondMemberName;		// 乙方名称
	private String thirdMemberName;		//丙方名称
	private String contractFlowStatus;		// 合同状态
	private Date openAccountTime ;//开户时间
	private String contractLeaderId;		// 合同负责人id
	private String contractLeaderName;		// 合同负责人名称
	private String overdueType;		// 查询类型(1=逾期4-7天, 2=逾期8-11天, 3=逾期11天以上)
	private String overdueDays;		//逾期天数
	private String openAccountTimes ;//开户时间
	private String contractFlowId ;//合同流程id

	public ContractOverdue() {
		super();
	}

	public ContractOverdue(String id){
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
	
	@Length(min=1, max=32, message="申请人编号长度必须介于 1 和 32 之间")
	public String getApplyPerCode() {
		return applyPerCode;
	}

	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	
	@Length(min=1, max=100, message="申请人名称长度必须介于 1 和 100 之间")
	public String getApplyPerName() {
		return applyPerName;
	}

	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}

	@NotNull(message="所属部门ID不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=1, max=100, message="所属部门名称长度必须介于 1 和 100 之间")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
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
	


	public String getContractFlowStatus() {
		return contractFlowStatus;
	}

	public void setContractFlowStatus(String contractFlowStatus) {
		this.contractFlowStatus = contractFlowStatus;
	}


	public String getContractNameCode() {
		return contractNameCode;
	}

	public void setContractNameCode(String contractNameCode) {
		this.contractNameCode = contractNameCode;
	}


	public String getThirdMemberName() {
		return thirdMemberName;
	}

	public void setThirdMemberName(String thirdMemberName) {
		this.thirdMemberName = thirdMemberName;
	}

	public Date getOpenAccountTime() {
		return openAccountTime;
	}

	public void setOpenAccountTime(Date openAccountTime) {
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

	public String getOverdueType() {
		return overdueType;
	}

	public void setOverdueType(String overdueType) {
		this.overdueType = overdueType;
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

	public String getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(String overdueDays) {
		this.overdueDays = overdueDays;
	}


	public String getOpenAccountTimes() {
		return openAccountTimes;
	}

	public void setOpenAccountTimes(String openAccountTimes) {
		this.openAccountTimes = openAccountTimes;
	}

	public String getContractFlowId() {
		return contractFlowId;
	}

	public void setContractFlowId(String contractFlowId) {
		this.contractFlowId = contractFlowId;
	}
}