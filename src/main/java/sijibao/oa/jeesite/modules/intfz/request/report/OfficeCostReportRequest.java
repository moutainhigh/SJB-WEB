package sijibao.oa.jeesite.modules.intfz.request.report;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * 部门费用报表请求接收实体
 * @author xby
 */
public class OfficeCostReportRequest extends PageRequest{
	@ApiModelProperty(value="年份")
	private String year;  //年份

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "OfficeCostReportRequest [year=" + year + "]";
	}
}
