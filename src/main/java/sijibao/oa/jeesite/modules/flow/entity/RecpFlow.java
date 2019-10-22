/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 接待申请Entity
 * @author xuby
 * @version 2018-04-17
 */
public class RecpFlow extends ActEntity<RecpFlow> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String procCode;		// 流程编号
	private String procName;		// 流程名称
	private String applyPerCode;		// 申请人编号
	private String applyPerName;		// 申请人名称
	private Date applyTime;		// 申请时间
	private String recpTheme;		// 接待主题
	private String projectId;		// 项目编号
	private String projectName;		// 项目名称
	private String projectPersonel;  //项目负责人
	private Integer recpNum;		// 接待人数
	private Date recpTime;		// 预计接待时间：yyyy-MM-dd HH:mm
	private Integer recpParticNum;		// 陪客人数
	private BigDecimal budgetTotal;		// 预算费用总额
	private String recpStatus;   //报销状态
	private String recpStatusIn;   //报销状态查询
	private String recpStatusValue;   //报销状态
	private Office office;		// 所属部门
	private String saveFlag; //保存标识
	private List<DemandManagementBudget> demandBudgetList;
	private Date beginApplyTime;		// 开始 申请时间
	private Date endApplyTime;		// 结束 申请时间
	private String[] employees; //陪客人员
	private Date flowFinishTime; //审批结束时间

	private Integer related; //是否被接待报销关联
	private String producSide;//产品端

	public String getProducSide() {

		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public RecpFlow() {
		super();
	}

	public RecpFlow(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例ID长度必须介于 0 和 64 之间")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="申请时间不能为空")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	@Length(min=1, max=100, message="接待主题长度必须介于 1 和 100 之间")
	public String getRecpTheme() {
		return recpTheme;
	}

	public void setRecpTheme(String recpTheme) {
		this.recpTheme = recpTheme;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public Integer getRecpNum() {
		return recpNum;
	}

	public void setRecpNum(Integer recpNum) {
		this.recpNum = recpNum;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="预计接待时间：yyyy-MM-dd HH:mm不能为空")
	public Date getRecpTime() {
		return recpTime;
	}

	public void setRecpTime(Date recpTime) {
		this.recpTime = recpTime;
	}
	
	public Integer getRecpParticNum() {
		return recpParticNum;
	}

	public void setRecpParticNum(Integer recpParticNum) {
		this.recpParticNum = recpParticNum;
	}
	
	public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}

	public void setBudgetTotal(BigDecimal budgetTotal) {
		this.budgetTotal = budgetTotal;
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

	public String getRecpStatus() {
		return recpStatus;
	}

	public void setRecpStatus(String recpStatus) {
		this.recpStatus = recpStatus;
	}

	public String getRecpStatusValue() {
		return recpStatusValue;
	}

	public void setRecpStatusValue(String recpStatusValue) {
		this.recpStatusValue = recpStatusValue;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<DemandManagementBudget> getDemandBudgetList() {
		return demandBudgetList;
	}

	public void setDemandBudgetList(List<DemandManagementBudget> demandBudgetList) {
		this.demandBudgetList = demandBudgetList;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
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

	public String getProjectPersonel() {
		return projectPersonel;
	}

	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}

	public String[] getEmployees() {
		return employees;
	}

	public void setEmployees(String[] employees) {
		this.employees = employees;
	}

	public Date getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(Date flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}

	public String getRecpStatusIn() {
		return recpStatusIn;
	}

	public void setRecpStatusIn(String recpStatusIn) {
		this.recpStatusIn = recpStatusIn;
	}

	public Integer getRelated() {
		return related;
	}

	public void setRelated(Integer related) {
		this.related = related;
	}
}