package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目维护保存对象
 * @author wanxb
 */
@ApiModel(value="项目维护保存对象")
public class MainProjectMaintenanceSaveReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键ID")
	private String id; //主键ID
	@ApiModelProperty(value="项目id")
	private String projectId;		// 项目id
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="项目状态")
	private String projectState;// 项目状态
	@ApiModelProperty(value="备注")
	private String remarks;// 备注
	@ApiModelProperty(value="状态变更时间:yyyy-MM-dd HH:mm:ss")
	private long changeTime = 0l;// 状态变更时间:yyyy-MM-dd HH:mm:ss
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}
	@Override
	public String toString() {
		return "ProjectMaintenanceSaveReq [id=" + id + ", projectId=" + projectId + ", projectName=" + projectName
				+ ", projectState=" + projectState + ", remarks=" + remarks + ", changeTime=" + changeTime + "]";
	}
}
