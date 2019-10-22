package sijibao.oa.jeesite.modules.intfz.request.report;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目费用报表接收对象
 * @author xby
 */
@ApiModel(value="项目费用报表接收对象")
public class ProjectReportRequest extends PageRequest{
	@ApiModelProperty(value="员工name")
	private String projectName;//员工name
	@ApiModelProperty(value="年份")
	private String year;  //年份
	@ApiModelProperty(value="项目状态")
	private String projectState;//项目状态
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}
	@Override
	public String toString() {
		return "ProjectReportRequest [projectName=" + projectName + ", year=" + year + ", projectState=" + projectState
				+ "]";
	}
}
