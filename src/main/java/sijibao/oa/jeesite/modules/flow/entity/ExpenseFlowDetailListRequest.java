package sijibao.oa.jeesite.modules.flow.entity;

import java.util.List;

public class ExpenseFlowDetailListRequest {
	private List<ExpenseDetail> expenseDetail;
	private List<ExpenseAttachment> expenseAttachments;
	
	public List<ExpenseDetail> getExpenseDetail() {
		return expenseDetail;
	}

	public void setExpenseDetail(List<ExpenseDetail> expenseDetail) {
		this.expenseDetail = expenseDetail;
	}

	public List<ExpenseAttachment> getExpenseAttachments() {
		return expenseAttachments;
	}

	public void setExpenseAttachments(List<ExpenseAttachment> expenseAttachments) {
		this.expenseAttachments = expenseAttachments;
	}
}
