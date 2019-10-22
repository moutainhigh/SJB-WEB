package sijibao.oa.jeesite.modules.intfz.response.expense;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ExpenseHistoryPerMonth {

    @ApiModelProperty(value = "报销历史每月每科目列表")
    private List<ExpenseHistoryPerMonthPerSub> expenseHistoryPerMonthPerSubList;

    public List<ExpenseHistoryPerMonthPerSub> getExpenseHistoryPerMonthPerSubList() {
        return expenseHistoryPerMonthPerSubList;
    }

    public void setExpenseHistoryPerMonthPerSubList(List<ExpenseHistoryPerMonthPerSub> expenseHistoryPerMonthPerSubList) {
        this.expenseHistoryPerMonthPerSubList = expenseHistoryPerMonthPerSubList;
    }
}