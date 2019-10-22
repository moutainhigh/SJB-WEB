package sijibao.oa.jeesite.modules.intfz.response.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工状态日报表返回对象
 * @author xuby
 * @date 2018-09-16
 */
@ApiModel(value="员工状态日报表返回对象")
public class EmpStatusDayReportResponse {
	
	@ApiModelProperty(value="部门名称")
	private String officeName; //部门名称
	
	@ApiModelProperty(value="岗位名称")
	private String postName; //岗位名称
	
	@ApiModelProperty(value="员工名称")
	private String userName; //员工名称
	
	@ApiModelProperty(value="员工ID")
	private String userId; //员工ID
	
	@ApiModelProperty(value="人员状态值")
	private String empStatus; //人员状态值
	
	@ApiModelProperty(value="人员状态文案")
	private String empStatusTxt; //人员状态文案
	
	@ApiModelProperty(value="项目名称")
	private String projectName; //项目名称
	
	@ApiModelProperty(value="项目ID")
	private String projectId; //项目ID
	
	@ApiModelProperty(value="节点ID")
	private String nodeId; //节点ID
	
	@ApiModelProperty(value="节点名称")
	private String nodeName; //节点名称
	
	@ApiModelProperty(value="基地")
	private String baseValue; //基地
	
	@ApiModelProperty(value="基地名称")
	private String baseValueTxt; //基地名称
	
	@ApiModelProperty(value="备注")
	private String remarks; //备注
	
	@ApiModelProperty(value="提交时间")
	private long datetime; //提交时间
	
	@ApiModelProperty(value="人员动作")
	private String userAction; //人员动作
	
	@ApiModelProperty(value="人员动作文案")
	private String userActionTxt; //人员动作文案

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

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getEmpStatusTxt() {
		return empStatusTxt;
	}

	public void setEmpStatusTxt(String empStatusTxt) {
		this.empStatusTxt = empStatusTxt;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(String baseValue) {
		this.baseValue = baseValue;
	}

	public String getBaseValueTxt() {
		return baseValueTxt;
	}

	public void setBaseValueTxt(String baseValueTxt) {
		this.baseValueTxt = baseValueTxt;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public long getDatetime() {
		return datetime;
	}

	public void setDatetime(long datetime) {
		this.datetime = datetime;
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

	@Override
	public String toString() {
		return "EmpStatusDayReportResponse [officeName=" + officeName + ", postName=" + postName + ", userName="
				+ userName + ", userId=" + userId + ", empStatus=" + empStatus + ", empStatusTxt=" + empStatusTxt
				+ ", projectName=" + projectName + ", projectId=" + projectId + ", nodeId=" + nodeId + ", nodeName="
				+ nodeName + ", baseValue=" + baseValue + ", baseValueTxt=" + baseValueTxt + ", remarks=" + remarks
				+ ", datetime=" + datetime + ", userAction=" + userAction + ", userActionTxt=" + userActionTxt + "]";
	}
}
