package sijibao.oa.jeesite.modules.intfz.response.report;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.common.PageResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目费用报表查询结果
 * @author wanxb
 */
@ApiModel(value="科目费用报表查询结果")
public class ProjectReportListResponse {
	@ApiModelProperty(value="分页列表数据")
	private PageResponse<List<ProjectReportResponse>> pageResponse;  //分页列表数据
	@ApiModelProperty(value="金额汇总数据")
	private ReportMonthSumResponse amountMonthSum;  //金额汇总
	
	public ProjectReportListResponse(PageResponse<List<ProjectReportResponse>> pageResponse,
			ReportMonthSumResponse amountMonthSum) {
		this.pageResponse = pageResponse;
		this.amountMonthSum = amountMonthSum;
	}

	public PageResponse<List<ProjectReportResponse>> getPageResponse() {
		return pageResponse;
	}

	public void setPageResponse(PageResponse<List<ProjectReportResponse>> pageResponse) {
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
		return "ProjectReportListResponse [pageResponse=" + pageResponse + ", amountMonthSum=" + amountMonthSum + "]";
	}

}
