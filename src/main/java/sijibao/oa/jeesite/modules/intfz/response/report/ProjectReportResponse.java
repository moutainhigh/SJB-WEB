package sijibao.oa.jeesite.modules.intfz.response.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目报表月份数据返回对象
 * @author xby
 *
 */
@ApiModel(value="项目报表月份返回对象")
public class ProjectReportResponse extends ReportMonthResponse{
	@ApiModelProperty(value="项目id")
	private String projectId;//项目id
	@ApiModelProperty(value="项目name")
	private String projectName;//项目name
	@ApiModelProperty(value="项目状态name")
	private String projectStateName;//项目状态name
	@ApiModelProperty(value="是否有明细")
	private String isDetail;   //是否有明细
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectStateName() {
		return projectStateName;
	}
	public void setProjectStateName(String projectStateName) {
		this.projectStateName = projectStateName;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getIsDetail() {
		return isDetail;
	}
	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}
	@Override
	public String toString() {
		return "ProjectReportResponse [projectId=" + projectId + ", projectName=" + projectName + ", projectStateName="
				+ projectStateName + ", isDetail=" + isDetail + "]";
	}
}
