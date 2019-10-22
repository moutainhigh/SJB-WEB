package sijibao.oa.jeesite.modules.intfz.response.employeestatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 人员状态变更记录返回对象
 * @author xuby
 * @date 2018-09-14
 */
@ApiModel(value="员工状态查询返回对象")
public class EmployeeStatusrecordResponse {
	
	@ApiModelProperty(value="主键ID")
	private String id;   //主键ID
	
	@ApiModelProperty(value="用户ID")
	private String userId;		// 用户ID
	
	@ApiModelProperty(value="人员状态")
	private String status;		// 人员状态
	
	@ApiModelProperty(value="人员状态文案")
	private String statusTxt;		// 人员状态文案
	
	@ApiModelProperty(value="人员动作")
	private String userAction;		// 人员动作
	
	@ApiModelProperty(value="人员动作文案")
	private String userActionTxt; //人员动作文案
	
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	
	@ApiModelProperty(value="项目名称")
	private String projectName; //项目名称
	
	@ApiModelProperty(value="项目节点ID")
	private String projectNodeId;		// 项目节点ID
	
	@ApiModelProperty(value="项目节点名称")
	private String projectNodeName; //项目节点名称
	
	@ApiModelProperty(value="基地ID")
	private String baseId;		// 基地ID
	
	@ApiModelProperty(value="基地名称")
	private String baseName;   //基地名称
	
	@ApiModelProperty(value="创建时间")
	private long createTime;		// 创建时间
	
	@ApiModelProperty(value="更新时间")
	private long updateTime;		// 更新时间
	
	@ApiModelProperty(value="备注")
	private String remarks;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusTxt() {
		return statusTxt;
	}

	public void setStatusTxt(String statusTxt) {
		this.statusTxt = statusTxt;
	}

	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	public String getUserActionTxt() {
		return userActionTxt;
	}

	public void setUserActionTxt(String userActionTxt) {
		this.userActionTxt = userActionTxt;
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

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "EmployeeStatusrecordResponse [id=" + id + ", userId=" + userId + ", status=" + status + ", statusTxt="
				+ statusTxt + ", userAction=" + userAction + ", userActionTxt=" + userActionTxt + ", projectId="
				+ projectId + ", projectName=" + projectName + ", projectNodeId=" + projectNodeId + ", projectNodeName="
				+ projectNodeName + ", baseId=" + baseId + ", baseName=" + baseName + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", remarks=" + remarks + "]";
	}
}
