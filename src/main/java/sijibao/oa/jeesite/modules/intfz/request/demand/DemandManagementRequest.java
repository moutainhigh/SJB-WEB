/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.demand;

import java.util.Arrays;
import java.util.List;

import com.sijibao.oa.common.persistence.ActEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 需求管理Entity
 * @author xuby
 * @version 2018-04-10
 */
public class DemandManagementRequest extends ActEntity<DemandManagementRequest> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键ID")
	private String id;           //ID，第一次申请不需要传递，重新申请时需要传递
	@ApiModelProperty(value="流程申请ID")
	private String procInsId;		// 流程实例ID
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="需求主题")
	private String demandName;		// 需求名称
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="需求类型")
	private String demandType;		// 需求类型
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	@ApiModelProperty(value="所属区域")
	private String[] areaCode;		// 所属区域
	@ApiModelProperty(value="需求人数")
	private String demandPersonelNum;		// 需求人数
	@ApiModelProperty(value="期望抵达日期")
	private String expectDate;		// 期望抵达日期
	@ApiModelProperty(value="预计时长")
	private String timeLong;		// 预计时长
	@ApiModelProperty(value="预算总金额")
	private String amountSum;		// 预算总金额
	
	private List<DemandManagementBudgetRequest> demandBudgetList; //预算明细数据
	public DemandManagementRequest() {
		super();
	}

	public DemandManagementRequest(String id){
		super(id);
	}

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

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String[] getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String[] areaCode) {
		this.areaCode = areaCode;
	}

	public String getDemandPersonelNum() {
		return demandPersonelNum;
	}

	public void setDemandPersonelNum(String demandPersonelNum) {
		this.demandPersonelNum = demandPersonelNum;
	}

	public String getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(String expectDate) {
		this.expectDate = expectDate;
	}

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

	public List<DemandManagementBudgetRequest> getDemandBudgetList() {
		return demandBudgetList;
	}

	public void setDemandBudgetList(List<DemandManagementBudgetRequest> demandBudgetList) {
		this.demandBudgetList = demandBudgetList;
	}
	public String getDemandName() {
		return demandName;
	}

	public void setDemandName(String demandName) {
		this.demandName = demandName;
	}

	@Override
	public String toString() {
		return "DemandManagementRequest [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", demandName=" + demandName + ", applyTime=" + applyTime + ", demandType=" + demandType
				+ ", projectId=" + projectId + ", areaCode=" + Arrays.toString(areaCode) + ", demandPersonelNum="
				+ demandPersonelNum + ", expectDate=" + expectDate + ", timeLong=" + timeLong + ", amountSum="
				+ amountSum + ", demandBudgetList=" + demandBudgetList + "]";
	}
	
}