/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 项目维护管理Entity
 * @author wanxb
 * @version 2018-06-29
 */
public class ProjectMaintenance extends DataEntity<ProjectMaintenance> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目id
	private String projectName;		// 项目名称
	private String projectState;		// 项目状态
	private String projectMaintenanceState;//维护项目状态
	private Date onLineDate;//上线时间格式yyyy-MM-dd
	private Date changeTime;		// 状态变更时间:yyyy-MM-dd HH:mm:ss
	
	public ProjectMaintenance() {
		super();
	}

	public ProjectMaintenance(String id){
		super(id);
	}

	@Length(min=1, max=32, message="项目id长度必须介于 1 和 32 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=90, message="项目名称长度必须介于 1 和 90 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	
	

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="状态变更时间:yyyy-MM-dd HH:mm:ss不能为空")
	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public String getProjectMaintenanceState() {
		return projectMaintenanceState;
	}

	public void setProjectMaintenanceState(String projectMaintenanceState) {
		this.projectMaintenanceState = projectMaintenanceState;
	}

	public Date getOnLineDate() {
		return onLineDate;
	}

	public void setOnLineDate(Date onLineDate) {
		this.onLineDate = onLineDate;
	}

	@Override
	public String toString() {
		return "ProjectMaintenance [projectId=" + projectId + ", projectName=" + projectName + ", projectState="
				+ projectState + ", projectMaintenanceState=" + projectMaintenanceState + ", onLineDate=" + onLineDate
				+ ", changeTime=" + changeTime + "]";
	}

}