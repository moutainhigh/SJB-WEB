package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "费用报销流程表oa_expense_flow（全字段）")
public class ExpenseFlowRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;//主键

    @ApiModelProperty(value = "流程实例ID")
    private String procInsId;//流程实例ID

    @ApiModelProperty(value = "流程编号")
    private String procCode;//流程编号

    @ApiModelProperty(value = "流程名称")
    private String procName;//流程名称

    @ApiModelProperty(value = "申请人编号")
    private String applyPerCode;//申请人编号

    @ApiModelProperty(value = "申请人名称")
    private String applyPerName;//申请人名称

    @ApiModelProperty(value = "申请时间")
    private Date applyTime;//申请时间

    @ApiModelProperty(value = "所属部门ID")
    private String officeId;//所属部门ID

    @ApiModelProperty(value = "所属部门名称")
    private String officeName;//所属部门名称

    @ApiModelProperty(value = "票据数量")
    private Integer billNum;//票据数量

    @ApiModelProperty(value = "费用合计")
    private BigDecimal expenseTotal;//费用合计

    @ApiModelProperty(value = "项目ID")
    private String projectId;//项目ID

    @ApiModelProperty(value = "项目名称")
    private String projectName;//项目名称

    @ApiModelProperty(value = "创建者")
    private String createBy;//创建者

    @ApiModelProperty(value = "创建时间")
    private Date createDate;//创建时间

    @ApiModelProperty(value = "更新者")
    private String updateBy;//更新者

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;//更新时间

    @ApiModelProperty(value = "备注")
    private String remarks;//备注

    @ApiModelProperty(value = "删除标记")
    private String delFlag;//删除标记

    @ApiModelProperty(value = "报销状态")
    private String expenseStatus;//报销状态

    @ApiModelProperty(value = "发票所属城市")
    private String taxCityCode;//发票所属城市

    @ApiModelProperty(value = "提前打款金额")
    private BigDecimal bringAmount;//提前打款金额

    @ApiModelProperty(value = "提前打款处理状态")
    private String bringRemitStatus;//提前打款处理状态

    @ApiModelProperty(value = "流程审批结束时间")
    private Date flowFiniskTime;//流程审批结束时间

    @ApiModelProperty(value = "报销类型，对应字典类型oa_expense_type")
    private String applyType;//报销类型：1常规报销2接待报销3资产报销

    @ApiModelProperty(value = "接待申请流程编号")
    private String recpProcCode;//接待申请流程编号

    @ApiModelProperty(value = "接待客户情况")
    private String customerSituation;//接待客户情况

    @ApiModelProperty(value = "项目负责人")
    private String projectPersonel;//项目负责人

    @ApiModelProperty(value = "接待人数")
    private Integer recpParticNum;//接待人数

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

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getApplyPerCode() {
        return applyPerCode;
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

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Integer getBillNum() {
        return billNum;
    }

    public void setBillNum(Integer billNum) {
        this.billNum = billNum;
    }

    public BigDecimal getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(BigDecimal expenseTotal) {
        this.expenseTotal = expenseTotal;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getExpenseStatus() {
        return expenseStatus;
    }

    public void setExpenseStatus(String expenseStatus) {
        this.expenseStatus = expenseStatus;
    }

    public String getTaxCityCode() {
        return taxCityCode;
    }

    public void setTaxCityCode(String taxCityCode) {
        this.taxCityCode = taxCityCode;
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

    public Date getFlowFiniskTime() {
        return flowFiniskTime;
    }

    public void setFlowFiniskTime(Date flowFiniskTime) {
        this.flowFiniskTime = flowFiniskTime;
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

    public String getCustomerSituation() {
        return customerSituation;
    }

    public void setCustomerSituation(String customerSituation) {
        this.customerSituation = customerSituation;
    }

    public String getProjectPersonel() {
        return projectPersonel;
    }

    public void setProjectPersonel(String projectPersonel) {
        this.projectPersonel = projectPersonel;
    }

    public Integer getRecpParticNum() {
        return recpParticNum;
    }

    public void setRecpParticNum(Integer recpParticNum) {
        this.recpParticNum = recpParticNum;
    }

}
