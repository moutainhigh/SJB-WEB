package sijibao.oa.jeesite.modules.intfz.request.employeestatus;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工状态保存请求对象
 * @author Administrator
 *
 */
@ApiModel(value="员工状态保存请求对象")
public class EmployeeStatusSaveReq {
	
	/**
	 * 人员动作
	 */
	@ApiModelProperty(value="人员动作")
	private String userAction;
	
	/**
	 * 项目ID
	 */
	@ApiModelProperty(value="项目ID")
	private String projectId;
	
	/**
	 * 项目节点ID
	 */
	@ApiModelProperty(value="项目节点ID")
	private String projectNodeId;
	
	/**
	 * 基地ID
	 */
	@ApiModelProperty(value="节点ID")
	private String baseId;
	
	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remarks;
	
	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectNodeId() {
		return projectNodeId;
	}

	public void setProjectNodeId(String projectNodeId) {
		this.projectNodeId = projectNodeId;
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "EmployeeStatusSaveReq [userAction=" + userAction + ", projectId=" + projectId + ", projectNodeId="
				+ projectNodeId + ", baseId=" + baseId + ", remarks=" + remarks + "]";
	}
	
}
