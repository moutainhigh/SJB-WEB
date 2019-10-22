package sijibao.oa.jeesite.modules.flow.entity.report;

import java.math.BigDecimal;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 月度金额汇总
 * @author xby
 */
public class MonthAmountSumReport extends DataEntity<MonthAmountSumReport> {
	
	private static final long serialVersionUID = 1L;
	
	private String taskFinishTime; //时间
	private BigDecimal expenseAmt; //金额
	public String getTaskFinishTime() {
		return taskFinishTime;
	}
	public void setTaskFinishTime(String taskFinishTime) {
		this.taskFinishTime = taskFinishTime;
	}
	public BigDecimal getExpenseAmt() {
		return expenseAmt;
	}
	public void setExpenseAmt(BigDecimal expenseAmt) {
		this.expenseAmt = expenseAmt;
	}
	
}
