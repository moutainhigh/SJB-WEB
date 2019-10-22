package sijibao.oa.jeesite.modules.intfz.response.expense;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 报销历史每月列表
 */
public class ExpenseHistoryResponse {
    @ApiModelProperty(value = "报销历史每月列表")
    private List<ExpenseHistoryPerMonth> expenseHistoryPerMonthList;

    public List<ExpenseHistoryPerMonth> getExpenseHistoryPerMonthList() {
        return expenseHistoryPerMonthList;
    }

    public void setExpenseHistoryPerMonthList(List<ExpenseHistoryPerMonth> expenseHistoryPerMonthList) {
        this.expenseHistoryPerMonthList = expenseHistoryPerMonthList;
    }
}