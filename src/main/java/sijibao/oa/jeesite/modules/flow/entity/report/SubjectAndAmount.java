package sijibao.oa.jeesite.modules.flow.entity.report;

import java.math.BigDecimal;

/**
 * @author Jianghao Zhang
 */
public class SubjectAndAmount {
    private String id;//报销详情ID，即oa_expense_detail表主键
    private String firstSub;//一级科目名称
    private String secondSub;//二级科目名称
    private BigDecimal expenseAmt;//报销金额

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getExpenseAmt() {
        return expenseAmt;
    }

    public void setExpenseAmt(BigDecimal expenseAmt) {
        this.expenseAmt = expenseAmt;
    }
}
