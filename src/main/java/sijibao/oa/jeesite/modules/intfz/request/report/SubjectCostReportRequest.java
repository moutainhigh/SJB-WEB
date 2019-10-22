package sijibao.oa.jeesite.modules.intfz.request.report;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 科目费用报表接收对象
 * @author xby
 */
@ApiModel(value="科目费用报表接收对象")
public class SubjectCostReportRequest extends PageRequest{
	
	@ApiModelProperty(value="年份")
	private String year;  //年份
	@ApiModelProperty(value="一级科目编码(查询二级科目数据时必填)")
	private String firstCode; //一级科目编码
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getFirstCode() {
		return firstCode;
	}
	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}
	@Override
	public String toString() {
		return "SubjectCostReportRequest [year=" + year + ", firstCode=" + firstCode + "]";
	}
	
}
