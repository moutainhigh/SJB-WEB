package sijibao.oa.jeesite.modules.intfz.response.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工月报表明细查询返回对象
 * @author xuby
 * @date 2018-09-26
 */
@ApiModel(value="员工月报表明细查询返回对象")
public class EmpStatusMonthDetailReportResponse {
	
	/**
	 * 项目ID
	 */
	@ApiModelProperty(value="项目ID")
	private String projectId;
	
	/**
	 * 项目名称
	 */
	@ApiModelProperty(value="项目名称")
	private String projectName ;
	
	/**
	 * 项目节点ID
	 */
	@ApiModelProperty(value="项目节点ID")
	private String projectNodeId;
	
	/**
	 * 项目节点名称
	 */
	@ApiModelProperty(value="项目节点名称")
	private String projectNodeName;
	
	/**
	 * 项目中天数
	 */
	@ApiModelProperty(value="项目中的天数")
	private Integer projectInDays;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getProjectInDays() {
		return projectInDays;
	}

	public void setProjectInDays(Integer projectInDays) {
		this.projectInDays = projectInDays;
	}

	public String getProjectNodeId() {
		return projectNodeId;
	}

	public void setProjectNodeId(String projectNodeId) {
		this.projectNodeId = projectNodeId;
	}

	public String getProjectNodeName() {
		return projectNodeName;
	}

	public void setProjectNodeName(String projectNodeName) {
		this.projectNodeName = projectNodeName;
	}

	@Override
	public String toString() {
		return "EmpStatusMonthDetailReportResponse [projectId=" + projectId + ", projectName=" + projectName
				+ ", projectNodeId=" + projectNodeId + ", projectNodeName=" + projectNodeName + ", projectInDays="
				+ projectInDays + "]";
	}
	
}
