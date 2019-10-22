/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 出差申请Entity
 * @author xuby
 * @version 2018-05-23
 */
public class TravelFlow extends ActEntity<TravelFlow> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String procCode;		// 流程编号
	private String procName;		// 流程名称
	private String applyPerCode;		// 申请人编号
	private String applyPerName;		// 申请人名称
	private Office office;		// 所属部门ID
	private String officeName;		// 所属部门名称
	private Date applyTime;		// 申请时间
	private String relationTheme;		// 关联主题
	private String projectId;		// 项目编号
	private String projectName;		// 项目名称
	private String projectPersonel;		// 项目负责人
	private String travelExpenseTypes; // 出差报销类型value列表拼接字符串
	private String entourages; // 随行人员id列表拼接字符串
	private int incidenceTimes; //被报销申请流程关联的次数
	private BigDecimal budgetTotal;		// 预算费用总额
	private String travelStatus;		// 单据状态
	private String travelStatusIn;		// 单据状态 in 查询
	private String travelStatusValue;		// 单据状态
	private Date flowFinishTime;		// 审批结束时间
	private Date beginApplyTime;		// 开始 申请时间
	private Date endApplyTime;		// 结束 申请时间
	private boolean filterExhausted;//非持久化字段，是否过滤掉被报销流程关联次数已达上限的出差申请
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public TravelFlow() {
		super();
	}

	public TravelFlow(String id){
		super(id);
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
	
	@Length(min=1, max=300, message="流程名称长度必须介于 1 和 300 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="申请时间不能为空")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@Length(min=1, max=100, message="关联主题长度必须介于 1 和 100 之间")
	public String getRelationTheme() {
		return relationTheme;
	}

	public void setRelationTheme(String relationTheme) {
		this.relationTheme = relationTheme;
	}
	
	@Length(min=1, max=30, message="项目编号长度必须介于 1 和 30 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=100, message="项目名称长度必须介于 1 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=1, max=32, message="项目负责人长度必须介于 1 和 32 之间")
	public String getProjectPersonel() {
		return projectPersonel;
	}

	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}

	public String getTravelExpenseTypes() {
		return travelExpenseTypes;
	}

	public void setTravelExpenseTypes(String travelExpenseTypes) {
		this.travelExpenseTypes = travelExpenseTypes;
	}

	public String getEntourages() {
		return entourages;
	}

	public int getIncidenceTimes() {
		return incidenceTimes;
	}

	public void setIncidenceTimes(int incidenceTimes) {
		this.incidenceTimes = incidenceTimes;
	}

	public void setEntourages(String entourages) {
		this.entourages = entourages;
	}

	public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}

	public void setBudgetTotal(BigDecimal budgetTotal) {
		this.budgetTotal = budgetTotal;
	}
	
	@Length(min=0, max=8, message="单据状态长度必须介于 0 和 8 之间")
	public String getTravelStatus() {
		return travelStatus;
	}

	public void setTravelStatus(String travelStatus) {
		this.travelStatus = travelStatus;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(Date flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getTravelStatusValue() {
		return travelStatusValue;
	}

	public void setTravelStatusValue(String travelStatusValue) {
		this.travelStatusValue = travelStatusValue;
	}

	public String getTravelStatusIn() {
		return travelStatusIn;
	}

	public void setTravelStatusIn(String travelStatusIn) {
		this.travelStatusIn = travelStatusIn;
	}

	public boolean isFilterExhausted() {
		return filterExhausted;
	}

	public void setFilterExhausted(boolean filterExhausted) {
		this.filterExhausted = filterExhausted;
	}
}