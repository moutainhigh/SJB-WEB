package sijibao.oa.jeesite.modules.intfz.response.expense;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.common.FlowLogResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销详情对象
 * @author xby
 * @time 2017年12月27日 下午3:04:46
 */
@ApiModel(value="报销详情对象")
public class ExpenseFlowDetailResponse {
	
	@ApiModelProperty(value="报销基本信息")
	private ExpenseFlowResponse detail;
	@ApiModelProperty(value="报销费用明细信息")
	private List<ExpenseDetailResponse> flowDetailList;
	@ApiModelProperty(value="报销流程日志信息")
	private List<FlowLogResponse> flowLoglist;
	
	@ApiModelProperty(value="合并相同科目的明细费用")
	private List<AmtDetailResponse> amtList;

	public ExpenseFlowDetailResponse(){}
	public ExpenseFlowDetailResponse(ExpenseFlowResponse detail, List<ExpenseDetailResponse> flowDetailList,
			List<FlowLogResponse> flowLoglist, List<AmtDetailResponse> amtList) {
		super();
		this.detail = detail;
		this.flowDetailList = flowDetailList;
		this.flowLoglist = flowLoglist;
		this.amtList = amtList;
	}
	public ExpenseFlowResponse getDetail() {
		return detail;
	}
	public void setDetail(ExpenseFlowResponse detail) {
		this.detail = detail;
	}
	public List<ExpenseDetailResponse> getFlowDetailList() {
		return flowDetailList;
	}
	public void setFlowDetailList(List<ExpenseDetailResponse> flowDetailList) {
		this.flowDetailList = flowDetailList;
	}
	public List<FlowLogResponse> getFlowLoglist() {
		return flowLoglist;
	}
	public void setFlowLoglist(List<FlowLogResponse> flowLoglist) {
		this.flowLoglist = flowLoglist;
	}
	public List<AmtDetailResponse> getAmtList() {
		return amtList;
	}
	public void setAmtList(List<AmtDetailResponse> amtList) {
		this.amtList = amtList;
	}
	
}
