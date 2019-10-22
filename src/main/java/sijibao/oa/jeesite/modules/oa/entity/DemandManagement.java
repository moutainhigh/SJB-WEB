/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 需求管理Entity
 * @author xuby
 * @version 2018-03-28
 */
public class DemandManagement extends ActEntity<DemandManagement> {
	
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
	private String demandName;		// 需求名称
	private String demandType;		// 需求类型
	private String projectId;		// 项目ID
	private String projectName;		// 项目名称
	private String areaCode;		// 区域编码
	private String areaName;		// 区域名称
	private String demandPersonelNum;		// 需求人数
	private Date expectDate;		// 期望抵达日期
	private String timeLong;		// 预计时长
	private String amountSum;		// 预算总金额
	private String demandStatus;		// 需求状态
	private String demandStatusIn;		// 需求状态查询
	private String demandStatusTxt;		// 需求状态文本
	private String billType;  //单据类型(1:市场发起申请,2:实施发起需求)
	private String demandTypeName;		// 需求类型名称
	private String saveFlag; //保存操作标识
	private String[] employees; //受理人员
	private Date beginApplyTime;		// 开始 申请时间
	private Date endApplyTime;		// 结束 申请时间
	private Date flowFinishTime; //审批结束时间
	
	private List<DemandManagementBudget> demandBudgetList;
	public DemandManagement() {
		super();
	}

	public DemandManagement(String id){
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
	
	@Length(min=0, max=32, message="申请人编号长度必须介于 0 和 32 之间")
	public String getApplyPerCode() {
		return applyPerCode;
	}

	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	
	@Length(min=0, max=100, message="申请人名称长度必须介于 0 和 100 之间")
	public String getApplyPerName() {
		return applyPerName;
	}

	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=100, message="所属部门名称长度必须介于 0 和 100 之间")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Length(min=0, max=32, message="所属岗位编码长度必须介于 0 和 32 之间")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	@Length(min=0, max=64, message="所属岗位名称长度必须介于 0 和 64 之间")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	@Length(min=0, max=300, message="需求名称长度必须介于 0 和 300 之间")
	public String getDemandName() {
		return demandName;
	}

	public void setDemandName(String demandName) {
		this.demandName = demandName;
	}
	
	@Length(min=0, max=8, message="需求类型长度必须介于 0 和 8 之间")
	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}
	
	@Length(min=0, max=32, message="项目ID长度必须介于 0 和 32 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=300, message="项目名称长度必须介于 0 和 300 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=32, message="区域编码长度必须介于 0 和 32 之间")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	@Length(min=0, max=32, message="区域名称长度必须介于 0 和 32 之间")
	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	@Length(min=0, max=5, message="需求人数长度必须介于 0 和 5 之间")
	public String getDemandPersonelNum() {
		return demandPersonelNum;
	}

	public void setDemandPersonelNum(String demandPersonelNum) {
		this.demandPersonelNum = demandPersonelNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}
	
	@Length(min=0, max=5, message="预计时长长度必须介于 0 和 5 之间")
	public String getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(String timeLong) {
		this.timeLong = timeLong;
	}
	
	public String getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(String amountSum) {
		this.amountSum = amountSum;
	}
	
	@Length(min=0, max=8, message="需求状态长度必须介于 0 和 8 之间")
	public String getDemandStatus() {
		return demandStatus;
	}

	public void setDemandStatus(String demandStatus) {
		this.demandStatus = demandStatus;
	}

	public String getDemandTypeName() {
		return demandTypeName;
	}

	public void setDemandTypeName(String demandTypeName) {
		this.demandTypeName = demandTypeName;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String[] getEmployees() {
		return employees;
	}

	public void setEmployees(String[] employees) {
		this.employees = employees;
	}

	public List<DemandManagementBudget> getDemandBudgetList() {
		return demandBudgetList;
	}

	public void setDemandBudgetList(List<DemandManagementBudget> demandBudgetList) {
		this.demandBudgetList = demandBudgetList;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getDemandStatusTxt() {
		return demandStatusTxt;
	}

	public void setDemandStatusTxt(String demandStatusTxt) {
		this.demandStatusTxt = demandStatusTxt;
	}

	public Date getBeginApplyTime() {
		return beginApplyTime;
	}

	public void setBeginApplyTime(Date beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}

	public Date getEndApplyTime() {
		return endApplyTime;
	}

	public void setEndApplyTime(Date endApplyTime) {
		this.endApplyTime = endApplyTime;
	}

	public String getDemandStatusIn() {
		return demandStatusIn;
	}

	public void setDemandStatusIn(String demandStatusIn) {
		this.demandStatusIn = demandStatusIn;
	}

	public Date getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(Date flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}
	
}