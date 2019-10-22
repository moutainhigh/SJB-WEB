package sijibao.oa.jeesite.modules.intfz.response.report;

import io.swagger.annotations.ApiModelProperty;

/**
 * 部门费用报表返回对象信息
 * @author xby
 */
public class OfficeCostReportResponse  extends ReportMonthResponse {
	
	@ApiModelProperty(value="部门ID")
	private String officeId;
	@ApiModelProperty(value="部门名称")
	private String officeName;
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	@Override
	public String toString() {
		return "OfficeCostReportResponse [officeId=" + officeId + ", officeName=" + officeName + "]";
	}
}
