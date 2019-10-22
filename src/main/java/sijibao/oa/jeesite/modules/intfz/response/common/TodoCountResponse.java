package sijibao.oa.jeesite.modules.intfz.response.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * 待办任务条数返回结果实体
 * @author mbdn
 *
 */
public class TodoCountResponse {
	
	@ApiModelProperty(value="报销待办任务条数")
	private int expenseFlowTodoCount;
	
	@ApiModelProperty(value="接待申请待办任务条数")
	private int recpFlowTodoCount;
	
	@ApiModelProperty(value="出差申请待办任务条数")
	private int travelFlowTodoCount;
	
	@ApiModelProperty(value="资源申请待办任务条数")
	private int resourcesApplyTodoCount;
	
	@ApiModelProperty(value="资源办理待办任务条数")
	private int resourcesHandleTodoCount;
	
	@ApiModelProperty(value="合同申请待办任务条数")
	private int contractFlowTodoCount;

	@ApiModelProperty(value="立项申请待办任务条数")
	private int projectApprovalCount;

	public int getProjectApprovalCount() {
		return projectApprovalCount;
	}

	public void setProjectApprovalCount(int projectApprovalCount) {
		this.projectApprovalCount = projectApprovalCount;
	}

	public int getExpenseFlowTodoCount() {
		return expenseFlowTodoCount;
	}

	public void setExpenseFlowTodoCount(int expenseFlowTodoCount) {
		this.expenseFlowTodoCount = expenseFlowTodoCount;
	}

	public int getRecpFlowTodoCount() {
		return recpFlowTodoCount;
	}

	public void setRecpFlowTodoCount(int recpFlowTodoCount) {
		this.recpFlowTodoCount = recpFlowTodoCount;
	}

	public int getTravelFlowTodoCount() {
		return travelFlowTodoCount;
	}

	public void setTravelFlowTodoCount(int travelFlowTodoCount) {
		this.travelFlowTodoCount = travelFlowTodoCount;
	}

	public int getResourcesApplyTodoCount() {
		return resourcesApplyTodoCount;
	}

	public void setResourcesApplyTodoCount(int resourcesApplyTodoCount) {
		this.resourcesApplyTodoCount = resourcesApplyTodoCount;
	}

	public int getResourcesHandleTodoCount() {
		return resourcesHandleTodoCount;
	}

	public void setResourcesHandleTodoCount(int resourcesHandleTodoCount) {
		this.resourcesHandleTodoCount = resourcesHandleTodoCount;
	}

	public int getContractFlowTodoCount() {
		return contractFlowTodoCount;
	}

	public void setContractFlowTodoCount(int contractFlowTodoCount) {
		this.contractFlowTodoCount = contractFlowTodoCount;
	}
	
}
