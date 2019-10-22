package sijibao.oa.jeesite.modules.intfz.response.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * 待办任务条数返回结果实体
 * @author mbdn
 *
 */
public class MyApplyCountResponse {
	
	@ApiModelProperty(value="报销申请条数")
	private int expenseFlowApplyCount;
	
	@ApiModelProperty(value="接待申请条数")
	private int recpFlowApplyCount;
	
//	@ApiModelProperty(value="市场需求申请条数")
//	private int demandManagementMarketApplyCount;
//	
//	@ApiModelProperty(value="实施需求发起条数")
//	private int demandManagementImplementApplyCount;
	
	@ApiModelProperty(value="资源申请发起条数")
	private int resourcesApplyCount;
	
	@ApiModelProperty(value="资源办理发起条数")
	private int resourcesHandleCount;
	
	@ApiModelProperty(value="出差申请发起条数")
	private int travelApplyCount;
	
	@ApiModelProperty(value="合同申请申请发起条数")
	private int contractApplyCount;
	@ApiModelProperty(value="立项申请申请发起条数")
	private int projectApprovalCount;

	public int getExpenseFlowApplyCount() {
		return expenseFlowApplyCount;
	}

	public void setExpenseFlowApplyCount(int expenseFlowApplyCount) {
		this.expenseFlowApplyCount = expenseFlowApplyCount;
	}

	public int getRecpFlowApplyCount() {
		return recpFlowApplyCount;
	}

	public void setRecpFlowApplyCount(int recpFlowApplyCount) {
		this.recpFlowApplyCount = recpFlowApplyCount;
	}

//	public int getDemandManagementMarketApplyCount() {
//		return demandManagementMarketApplyCount;
//	}
//
//	public void setDemandManagementMarketApplyCount(int demandManagementMarketApplyCount) {
//		this.demandManagementMarketApplyCount = demandManagementMarketApplyCount;
//	}
//
//	public int getDemandManagementImplementApplyCount() {
//		return demandManagementImplementApplyCount;
//	}
//
//	public void setDemandManagementImplementApplyCount(int demandManagementImplementApplyCount) {
//		this.demandManagementImplementApplyCount = demandManagementImplementApplyCount;
//	}

	public int getResourcesApplyCount() {
		return resourcesApplyCount;
	}

	public void setResourcesApplyCount(int resourcesApplyCount) {
		this.resourcesApplyCount = resourcesApplyCount;
	}

	public int getResourcesHandleCount() {
		return resourcesHandleCount;
	}

	public void setResourcesHandleCount(int resourcesHandleCount) {
		this.resourcesHandleCount = resourcesHandleCount;
	}

	public int getProjectApprovalCount() {
		return projectApprovalCount;
	}

	public void setProjectApprovalCount(int projectApprovalCount) {
		this.projectApprovalCount = projectApprovalCount;
	}

	public int getTravelApplyCount() {
		return travelApplyCount;
	}

	public void setTravelApplyCount(int travelApplyCount) {
		this.travelApplyCount = travelApplyCount;
	}

	public int getContractApplyCount() {
		return contractApplyCount;
	}

	public void setContractApplyCount(int contractApplyCount) {
		this.contractApplyCount = contractApplyCount;
	}
	
}
