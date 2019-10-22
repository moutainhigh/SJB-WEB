package sijibao.oa.jeesite.modules.intfz.response.report;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.common.PageResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部门费用报表查询结果
 * @author xby
 */
@ApiModel(value="部门费用报表查询结果")
public class OfficeListReportResponse {
	@ApiModelProperty(value="分页列表数据")
	private PageResponse<List<OfficeCostReportResponse>> pageResponse;  //分页列表数据
	
	@ApiModelProperty(value="金额汇总数据")
	private ReportMonthSumResponse amountMonthSum;  //金额汇总
	
	public OfficeListReportResponse(PageResponse<List<OfficeCostReportResponse>> pageResponse,
			ReportMonthSumResponse amountMonthSum) {
		this.pageResponse = pageResponse;
		this.amountMonthSum = amountMonthSum;
	}

	public PageResponse<List<OfficeCostReportResponse>> getPageResponse() {
		return pageResponse;
	}

	public void setPageResponse(PageResponse<List<OfficeCostReportResponse>> pageResponse) {
		this.pageResponse = pageResponse;
	}

	public ReportMonthSumResponse getAmountMonthSum() {
		return amountMonthSum;
	}

	public void setAmountMonthSum(ReportMonthSumResponse amountMonthSum) {
		this.amountMonthSum = amountMonthSum;
	}

	@Override
	public String toString() {
		return "OfficeListReportResponse [pageResponse=" + pageResponse + ", amountMonthSum=" + amountMonthSum + "]";
	}
	
}
