package sijibao.oa.jeesite.modules.intfz.response.report;

import java.util.List;

import com.sijibao.oa.modules.intfz.response.common.PageResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 科目费用报表查询结果
 * @author xby
 */
@ApiModel(value="科目费用报表查询结果")
public class SubjectListReportResponse {
	@ApiModelProperty(value="分页列表数据")
	private PageResponse<List<SubjectCostReportResponse>> pageResponse;  //分页列表数据
	
	@ApiModelProperty(value="金额汇总数据")
	private ReportMonthSumResponse amountMonthSum;  //金额汇总
	
	@ApiModelProperty(value="年份")
	private String year; //年份
	
	public SubjectListReportResponse(PageResponse<List<SubjectCostReportResponse>> pageResponse,
			ReportMonthSumResponse amountMonthSum,String year) {
		this.pageResponse = pageResponse;
		this.amountMonthSum = amountMonthSum;
		this.year = year;
	}

	public PageResponse<List<SubjectCostReportResponse>> getPageResponse() {
		return pageResponse;
	}

	public void setPageResponse(PageResponse<List<SubjectCostReportResponse>> pageResponse) {
		this.pageResponse = pageResponse;
	}

	public ReportMonthSumResponse getAmountMonthSum() {
		return amountMonthSum;
	}

	public void setAmountMonthSum(ReportMonthSumResponse amountMonthSum) {
		this.amountMonthSum = amountMonthSum;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "SubjectListReportResponse [pageResponse=" + pageResponse + ", amountMonthSum=" + amountMonthSum
				+ ", year=" + year + "]";
	}
}
