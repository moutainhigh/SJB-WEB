package sijibao.oa.jeesite.modules.intfz.response.travel;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.DemandManagementBudgetResponse;
import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 出差详情对象
 * @author xby
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="出差详情对象")
public class TravelFlowDetailResponse {
	
	@ApiModelProperty(value="出差申请流程日志信息")
	private List<FlowLogResponse> flowLoglist;
	@ApiModelProperty(value="出差申请预算明细信息")
	private List<DemandManagementBudgetResponse> budgetDetailList;
	@ApiModelProperty(value="出差申请信息")
	private TravelFlowResponse travelFlowresponse;
	
	public TravelFlowDetailResponse(){}
	public TravelFlowDetailResponse(TravelFlowResponse travelFlowresponse, List<DemandManagementBudgetResponse> budgetDetailList,
			List<FlowLogResponse> flowLoglist) {
		super();
		this.travelFlowresponse = travelFlowresponse;
		this.budgetDetailList = budgetDetailList;
		this.flowLoglist = flowLoglist;
	}
	public List<FlowLogResponse> getFlowLoglist() {
		return flowLoglist;
	}
	public void setFlowLoglist(List<FlowLogResponse> flowLoglist) {
		this.flowLoglist = flowLoglist;
	}
	public List<DemandManagementBudgetResponse> getBudgetDetailList() {
		return budgetDetailList;
	}
	public void setBudgetDetailList(List<DemandManagementBudgetResponse> budgetDetailList) {
		this.budgetDetailList = budgetDetailList;
	}
	public TravelFlowResponse getTravelFlowresponse() {
		return travelFlowresponse;
	}
	public void setTravelFlowresponse(TravelFlowResponse travelFlowresponse) {
		this.travelFlowresponse = travelFlowresponse;
	}
	
}
