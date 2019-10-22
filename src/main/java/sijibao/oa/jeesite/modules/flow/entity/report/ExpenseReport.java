package sijibao.oa.jeesite.modules.flow.entity.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 个人报销导出报表 数据对象
 *
 * @author Jianghao Zhang
 */
public class ExpenseReport {

    private String id;
    private String procCode;        // 流程编号
    private Date applyTime;         // 申请时间（报销日期）
    private String applyType;   // 报销类型名称
    private String projectName;     // 项目名称
    private String projectPersonel; // 项目负责人
    private List<SubjectAndAmount> subjectAndAmountList;// 科目及金额列表
    private BigDecimal expenseTotal;// 费用合计

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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPersonel() {
        return projectPersonel;
    }

    public void setProjectPersonel(String projectPersonel) {
        this.projectPersonel = projectPersonel;
    }

    public List<SubjectAndAmount> getSubjectAndAmountList() {
        return subjectAndAmountList;
    }

    public void setSubjectAndAmountList(List<SubjectAndAmount> subjectAndAmountList) {
        this.subjectAndAmountList = subjectAndAmountList;
    }

    public BigDecimal getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(BigDecimal expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

}
