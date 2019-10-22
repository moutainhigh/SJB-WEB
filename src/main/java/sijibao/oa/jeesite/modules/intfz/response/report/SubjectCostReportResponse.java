package sijibao.oa.jeesite.modules.intfz.response.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 科目费用报表返回对象
 * @author xby
 */
@ApiModel(value="科目费用报表返回对象")
public class SubjectCostReportResponse extends ReportMonthResponse {
	@ApiModelProperty(value="科目编码")
	private String subjectCode;
	@ApiModelProperty(value="科目名称")
	private String subjectName;
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	@Override
	public String toString() {
		return "SubjectCostReportResponse [subjectCode=" + subjectCode + ", subjectName=" + subjectName + "]";
	}
	 
}
