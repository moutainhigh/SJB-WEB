package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目维护管理返回对象信息
 * @author wanxb
 *
 */
@ApiModel(value="项目维护管理--列表返回对象")
public class MainProjectMaintenanceResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="项目id")
	private String projectId;		// 项目名称
	
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="项目状态name")
	private String projectMaintenanceStateName;//项目状态name
	@ApiModelProperty(value="备注")
	private String remarks;//备注
	@ApiModelProperty(value="状态变更时间:yyyy-MM-dd HH:mm:ss")
	private long changeTime = 0l;// 状态变更时间:yyyy-MM-dd HH:mm:ss
	@ApiModelProperty(value="维护时间:yyyy-MM-dd HH:mm:ss")
	private long createTime = 0l;//维护时间
	@ApiModelProperty(value="创建人")
	private String createBy;//创建人
	
	
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
	public long getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(long changeTime) {
		this.changeTime = changeTime;
	}
	
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProjectMaintenanceStateName() {
		return projectMaintenanceStateName;
	}
	public void setProjectMaintenanceStateName(String projectMaintenanceStateName) {
		this.projectMaintenanceStateName = projectMaintenanceStateName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	@Override
	public String toString() {
		return "ProjectMaintenanceResponse [projectId=" + projectId + ", projectName=" + projectName
				+ ", projectMaintenanceStateName=" + projectMaintenanceStateName + ", remarks=" + remarks
				+ ", changeTime=" + changeTime + ", createTime=" + createTime + ", createBy=" + createBy + "]";
	}
}
