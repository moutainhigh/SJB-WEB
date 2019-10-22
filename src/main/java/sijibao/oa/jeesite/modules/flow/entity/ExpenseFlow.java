/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 费用报销Entity
 * @author Petter
 * @version 2017-12-24
 */
public class ExpenseFlow extends ActEntity<ExpenseFlow> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String procCode;		// 流程编号
	private String procName;		// 流程名称
	private String applyPerCode;		// 申请人编号
	private String applyPerName;		// 申请人名称
	private Date applyTime;		// 申请时间
    private String costCenterId; //成本中心部门ID
    private String costCenterName; //成本中心部门名称
	private Office office;		// 申请人所属部门
	private String billNum;		// 票据数量
	private BigDecimal expenseTotal;		// 费用合计
	private String projectId;		// 项目ID
	private String projectName;		// 项目名称
	private String projectPersonel;  //项目负责人
	private Date beginApplyTime;		// 开始 申请时间
	private Date endApplyTime;		// 结束 申请时间
	private String expenseStatus;   //报销状态
	private String expenseStatusValue;   //报销状态
	private String taxCityCode; //发票所属城市
	private String taxCityName; //发票所属城市名称
	private String saveFlag; //保存操作标识
	private String expenseStatusIn; //in 查询
	private String expenseStatusNotIn; //not in 查询
	private ExpenseAttachment expenseAttachment; //报销附件
	private BigDecimal bringAmount; //提前打款金额
	private String bringRemitStatus; //提前打款状态(0:未处理，1：已处理)
	private Date flowFinishTime; //审批结束时间
	private String applyType;  //报销类型（键值对映射配置于字典中，按类型“oa_expense_type”搜索）
	private String applyTypeName; //报销类型名称
	private String recpProcCode; //接待申请流程编号
	private String recpProcName; //接待申请主题
    private String customerSituation; //接待客户情况
	private String[] employees;  //陪客人员
	private Integer recpParticNum;		// 陪客人数
	
	private String currentTaskAssignee; //当前审批环节
	private String bringRemitOrg; //提前打款所属机构
	private String detailOrderBy; //明细排序


	private String timeType;//时间类型:1提交时间2报销明细开始时间3报销明细结束时间
	private Date startTime;//开始时间(格式：yyyy-MM-dd)
	private Date endTime;//结束时间(格式：yyyy-MM-dd)")
	private String officeId;//部门编号
	private String firstSub;		// 一级科目
	private String secondSub;		// 二级科目
	private BigDecimal expenseAmtStart;//金额区间(元) 起始
	private BigDecimal expenseAmtEnd;//金额区间(元) 结束
	private String producSide;//产品端





	public ExpenseFlow() {
		super();
	}

	public ExpenseFlow(String id){
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
	
	@JsonFormat(pattern = "yyyy-MM-dd")
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
	
	public String getBillNum() {
		return billNum;
	}

	public void setBillNum(String billNum) {
		this.billNum = billNum;
	}
	
	public BigDecimal getExpenseTotal() {
		return expenseTotal;
	}

	public void setExpenseTotal(BigDecimal expenseTotal) {
		this.expenseTotal = expenseTotal;
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

	public String getExpenseStatus() {
		return expenseStatus;
	}

	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}

	public String getExpenseStatusValue() {
		return expenseStatusValue;
	}

	public void setExpenseStatusValue(String expenseStatusValue) {
		this.expenseStatusValue = expenseStatusValue;
	}

	public String getTaxCityCode() {
		return taxCityCode;
	}

	public void setTaxCityCode(String taxCityCode) {
		this.taxCityCode = taxCityCode;
	}

	public String getTaxCityName() {
		return taxCityName;
	}

	public void setTaxCityName(String taxCityName) {
		this.taxCityName = taxCityName;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public String getExpenseStatusIn() {
		return expenseStatusIn;
	}

	public void setExpenseStatusIn(String expenseStatusIn) {
		this.expenseStatusIn = expenseStatusIn;
	}

	public String getExpenseStatusNotIn() {
		return expenseStatusNotIn;
	}

	public void setExpenseStatusNotIn(String expenseStatusNotIn) {
		this.expenseStatusNotIn = expenseStatusNotIn;
	}

	public ExpenseAttachment getExpenseAttachment() {
		return expenseAttachment;
	}

	public void setExpenseAttachment(ExpenseAttachment expenseAttachment) {
		this.expenseAttachment = expenseAttachment;
	}

	public BigDecimal getBringAmount() {
		return bringAmount;
	}

	public void setBringAmount(BigDecimal bringAmount) {
		this.bringAmount = bringAmount;
	}

	public String getBringRemitStatus() {
		return bringRemitStatus;
	}

	public void setBringRemitStatus(String bringRemitStatus) {
		this.bringRemitStatus = bringRemitStatus;
	}

	public String getCurrentTaskAssignee() {
		return currentTaskAssignee;
	}

	public void setCurrentTaskAssignee(String currentTaskAssignee) {
		this.currentTaskAssignee = currentTaskAssignee;
	}
	
	public String getBringRemitOrg() {
		return bringRemitOrg;
	}

	public void setBringRemitOrg(String bringRemitOrg) {
		this.bringRemitOrg = bringRemitOrg;
	}
	public Date getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(Date flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getRecpProcCode() {
		return recpProcCode;
	}

	public void setRecpProcCode(String recpProcCode) {
		this.recpProcCode = recpProcCode;
	}

	public String[] getEmployees() {
		return employees;
	}

	public void setEmployees(String[] employees) {
		this.employees = employees;
	}

	public String getProjectPersonel() {
		return projectPersonel;
	}

	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}

	public String getRecpProcName() {
		return recpProcName;
	}

	public void setRecpProcName(String recpProcName) {
		this.recpProcName = recpProcName;
	}

    public String getCustomerSituation() {
        return customerSituation;
    }

    public void setCustomerSituation(String customerSituation) {
        this.customerSituation = customerSituation;
    }

    public Integer getRecpParticNum() {
		return recpParticNum;
	}

	public void setRecpParticNum(Integer recpParticNum) {
		this.recpParticNum = recpParticNum;
	}

	public String getApplyTypeName() {
		return applyTypeName;
	}

	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}
	
	public String getDetailOrderBy() {
		return detailOrderBy;
	}

	public void setDetailOrderBy(String detailOrderBy) {
		this.detailOrderBy = detailOrderBy;
	}

    public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public BigDecimal getExpenseAmtStart() {
		return expenseAmtStart;
	}

	public void setExpenseAmtStart(BigDecimal expenseAmtStart) {
		this.expenseAmtStart = expenseAmtStart;
	}

	public BigDecimal getExpenseAmtEnd() {
		return expenseAmtEnd;
	}

	public void setExpenseAmtEnd(BigDecimal expenseAmtEnd) {
		this.expenseAmtEnd = expenseAmtEnd;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFirstSub() {
		return firstSub;
	}

	public void setFirstSub(String firstSub) {
		this.firstSub = firstSub;
	}

	public String getSecondSub() {
		return secondSub;
	}

	public void setSecondSub(String secondSub) {
		this.secondSub = secondSub;
	}

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

    public String getCostCenterId() {
        return costCenterId;
    }

    public void setCostCenterId(String costCenterId) {
        this.costCenterId = costCenterId;
    }

    public String getCostCenterName() {
        return costCenterName;
    }

    public void setCostCenterName(String costCenterName) {
        this.costCenterName = costCenterName;
    }

    @Override
    public String toString() {
        return "ExpenseFlow{" +
                "procInsId='" + procInsId + '\'' +
                ", procCode='" + procCode + '\'' +
                ", procName='" + procName + '\'' +
                ", applyPerCode='" + applyPerCode + '\'' +
                ", applyPerName='" + applyPerName + '\'' +
                ", applyTime=" + applyTime +
                ", officeId=" + office.getId() +
                ", officeName=" + office.getName() +
                ", costCenterId=" + costCenterId +
                ", costCenterName=" + costCenterName +
                ", billNum='" + billNum + '\'' +
                ", expenseTotal=" + expenseTotal +
                ", projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectPersonel='" + projectPersonel + '\'' +
                ", beginApplyTime=" + beginApplyTime +
                ", endApplyTime=" + endApplyTime +
                ", expenseStatus='" + expenseStatus + '\'' +
                ", expenseStatusValue='" + expenseStatusValue + '\'' +
                ", taxCityCode='" + taxCityCode + '\'' +
                ", taxCityName='" + taxCityName + '\'' +
                ", saveFlag='" + saveFlag + '\'' +
                ", expenseStatusIn='" + expenseStatusIn + '\'' +
                ", expenseStatusNotIn='" + expenseStatusNotIn + '\'' +
                ", expenseAttachment=" + expenseAttachment +
                ", bringAmount=" + bringAmount +
                ", bringRemitStatus='" + bringRemitStatus + '\'' +
                ", flowFinishTime=" + flowFinishTime +
                ", applyType='" + applyType + '\'' +
                ", applyTypeName='" + applyTypeName + '\'' +
                ", recpProcCode='" + recpProcCode + '\'' +
                ", recpProcName='" + recpProcName + '\'' +
                ", customerSituation='" + customerSituation + '\'' +
                ", employees=" + Arrays.toString(employees) +
                ", recpParticNum=" + recpParticNum +
                ", currentTaskAssignee='" + currentTaskAssignee + '\'' +
                ", bringRemitOrg='" + bringRemitOrg + '\'' +
                ", detailOrderBy='" + detailOrderBy + '\'' +
                ", timeType='" + timeType + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", officeId='" + officeId + '\'' +
                ", firstSub='" + firstSub + '\'' +
                ", secondSub='" + secondSub + '\'' +
                ", expenseAmtStart=" + expenseAmtStart +
                ", expenseAmtEnd=" + expenseAmtEnd +
                '}';
    }
}