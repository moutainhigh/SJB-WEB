package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 员工状态记录Entity
 * @author xuby
 * @version 2018-09-14
 */
public class EmployeeStatusrecord extends DataEntity<EmployeeStatusrecord> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户ID
	private String status;		// 人员状态
	private String userAction;		// 人员动作
	private String projectId;		// 项目ID
	private String projectName;		// 项目名称
	private String projectNodeId;		// 项目节点ID
	private String projectNodeName; //项目节点名称
	private String baseId;		// 基地ID
	private String reportStatus; //报表状态
	private String textQuery;  //项目或节点模糊匹配
	private Date queryDate; //查询日期
	private String userIdOrDeptId;//人员id或者部门id

	public EmployeeStatusrecord() {
		super();
	}

	public EmployeeStatusrecord(String id){
		super(id);
	}

	@NotNull(message="用户ID不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=8, message="人员状态长度必须介于 1 和 8 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=1, max=8, message="人员动作长度必须介于 1 和 8 之间")
	public String getUserAction() {
		return userAction;
	}

	public void setUserAction(String userAction) {
		this.userAction = userAction;
	}
	
	@Length(min=1, max=32, message="项目ID长度必须介于 1 和 32 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=32, message="项目节点ID长度必须介于 1 和 32 之间")
	public String getProjectNodeId() {
		return projectNodeId;
	}

	public void setProjectNodeId(String projectNodeId) {
		this.projectNodeId = projectNodeId;
	}
	
	@Length(min=1, max=32, message="基地ID长度必须介于 1 和 32 之间")
	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNodeName() {
		return projectNodeName;
	}

	public void setProjectNodeName(String projectNodeName) {
		this.projectNodeName = projectNodeName;
	}

	public String getTextQuery() {
		return textQuery;
	}

	public void setTextQuery(String textQuery) {
		this.textQuery = textQuery;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public String getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getUserIdOrDeptId() {
		return userIdOrDeptId;
	}

	public void setUserIdOrDeptId(String userIdOrDeptId) {
		this.userIdOrDeptId = userIdOrDeptId;
	}

}