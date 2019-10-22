package sijibao.oa.jeesite.modules.intfz.response.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工状态月报表返回对象
 * @author xuby
 * @date 2018-09-26
 */
@ApiModel(value="员工状态月报表返回对象")
public class EmpStatusMonthReportResponse {
	
	/**
	 * 部门名称
	 */
	@ApiModelProperty(value="部门名称")
	private String officeName;
	
	/**
	 * 岗位名称
	 */
	@ApiModelProperty(value="岗位名称")
	private String postName;
	
	/**
	 * 员工名称
	 */
	@ApiModelProperty(value="员工名称")
	private String userName;
	
	/**
	 * 员工ID
	 */
	@ApiModelProperty(value="员工ID")
	private String userId;
	
	/**
	 * 项目中天数
	 */
	@ApiModelProperty(value="项目中天数")
	private Integer projectInDays;
	
	/**
	 * 待命中天数
	 */
	@ApiModelProperty(value="待命中天数")
	private Integer inOutDays;

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getProjectInDays() {
		return projectInDays;
	}

	public void setProjectInDays(Integer projectInDays) {
		this.projectInDays = projectInDays;
	}

	public Integer getInOutDays() {
		return inOutDays;
	}

	public void setInOutDays(Integer inOutDays) {
		this.inOutDays = inOutDays;
	}

	@Override
	public String toString() {
		return "EmpStatusMonthReportResponse [officeName=" + officeName + ", postName=" + postName + ", userName="
				+ userName + ", userId=" + userId + ", projectInDays=" + projectInDays + ", inOutDays=" + inOutDays
				+ "]";
	}
	
}
