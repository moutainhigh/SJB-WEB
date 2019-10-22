package sijibao.oa.jeesite.modules.intfz.request.expense;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销申请流程处理对象
 * @author xby
 */
@ApiModel
public class ExpenseFlowHandleReq {

	@ApiModelProperty(value="报销申请ID")
	private String expenseFlowId;//流程申请ID
	
	public String getExpenseFlowId() {
		return expenseFlowId;
	}
	public void setExpenseFlowId(String expenseFlowId) {
		this.expenseFlowId = expenseFlowId;
	}
	@Override
	public String toString() {
		return "MainProjectApprovalFlowHandleReq [expenseFlowId=" + expenseFlowId + "]";
	}
	
}
