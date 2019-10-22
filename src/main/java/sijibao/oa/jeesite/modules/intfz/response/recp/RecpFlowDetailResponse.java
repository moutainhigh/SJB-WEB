package sijibao.oa.jeesite.modules.intfz.response.recp;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.DemandManagementBudgetResponse;
import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接待详情对象
 * @author xby
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="接待详情对象")
public class RecpFlowDetailResponse {
	
	@ApiModelProperty(value="接待申请流程日志信息")
	private List<FlowLogResponse> flowLoglist;
	@ApiModelProperty(value="接待申请预算明细信息")
	private List<DemandManagementBudgetResponse> budgetDetailList;
	@ApiModelProperty(value="接待申请信息")
	private RecpFlowResponse recpFlowresponse;
	
	public RecpFlowDetailResponse(){}
	public RecpFlowDetailResponse(RecpFlowResponse recpFlowresponse, List<DemandManagementBudgetResponse> budgetDetailList,
			List<FlowLogResponse> flowLoglist) {
		super();
		this.recpFlowresponse = recpFlowresponse;
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
	public RecpFlowResponse getRecpFlowresponse() {
		return recpFlowresponse;
	}
	public void setRecpFlowresponse(RecpFlowResponse recpFlowresponse) {
		this.recpFlowresponse = recpFlowresponse;
	}
}
