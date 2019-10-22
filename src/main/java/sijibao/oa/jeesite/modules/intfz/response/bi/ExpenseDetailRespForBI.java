package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "费用报销明细表oa_expense_detail（全字段）")
public class ExpenseDetailRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;// 主键

    @ApiModelProperty(value = "流程编号")
    private String procCode; //流程编号

    @ApiModelProperty(value = "开始日期")
    private Date startDate; //开始日

    @ApiModelProperty(value = "起点")
    private String startPoint; //起点

    @ApiModelProperty(value = "结束日期")
    private Date endDate; //结束日期

    @ApiModelProperty(value = "终点")
    private String endPoint; //终点

    @ApiModelProperty(value = "一级科目")
    private String firstSub;//一级科目

    @ApiModelProperty(value = "二级科目")
    private String secondSub; //二级科目

    @ApiModelProperty(value = "人数")
    private Integer personNum;//人数

    @ApiModelProperty(value = "天数")
    private Integer dayNum; //天数

    @ApiModelProperty(value = "单据数量")
    private Integer billNum; //单据数量

    @ApiModelProperty(value = "报销金额")
    private BigDecimal expenseAmt; //报销金额

    @ApiModelProperty(value = "备注")
    private String remarks;//备注

    @ApiModelProperty(value = "明细行号")
    private Integer rowNum; //明细行号

    @ApiModelProperty(value = "最后更新时间")
    private Date updateTime; //最后更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
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

    public Integer getPersonNum() {
        return personNum;
    }

    public void setPersonNum(Integer personNum) {
        this.personNum = personNum;
    }

    public Integer getDayNum() {
        return dayNum;
    }

    public void setDayNum(Integer dayNum) {
        this.dayNum = dayNum;
    }

    public Integer getBillNum() {
        return billNum;
    }

    public void setBillNum(Integer billNum) {
        this.billNum = billNum;
    }

    public BigDecimal getExpenseAmt() {
        return expenseAmt;
    }

    public void setExpenseAmt(BigDecimal expenseAmt) {
        this.expenseAmt = expenseAmt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
