package sijibao.oa.jeesite.modules.intfz.request.report;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工费用报表接收对象
 * @author xby
 */
@ApiModel(value="员工费用报表接收对象")
public class EmployeeReportRequest extends PageRequest{
	@ApiModelProperty(value="员工name")
	private String employeeName;//员工name
	@ApiModelProperty(value="年份")
	private String year;  //年份
	@ApiModelProperty(value="部门id")
	private String officeId;//部门id
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	@Override
	public String toString() {
		return "EmployeeReportRequest [employeeName=" + employeeName + ", year=" + year + ", officeId=" + officeId
				+ "]";
	}
}
