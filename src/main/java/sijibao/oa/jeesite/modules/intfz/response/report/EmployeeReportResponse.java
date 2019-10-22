package sijibao.oa.jeesite.modules.intfz.response.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报表月份数据返回对象
 * @author xby
 *
 */
@ApiModel(value="报表月份返回对象")
public class EmployeeReportResponse extends ReportMonthResponse{
	
	@ApiModelProperty(value="员工name")
	private String employeeName;//员工name
	@ApiModelProperty(value="是否有明细")
	private String isDetail;   //是否有明细

	@ApiModelProperty(value="部门名称")
	private String officeName;//部门名称

	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getIsDetail() {
		return isDetail;
	}
	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}


	@Override
	public String toString() {
		return "EmployeeReportResponse [employeeName=" + employeeName + ", isDetail=" + isDetail + "]";
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
}
