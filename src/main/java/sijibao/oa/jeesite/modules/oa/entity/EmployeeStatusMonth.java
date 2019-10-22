package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 员工状态月度汇总Entity
 * @author xuby
 * @version 2018-09-20
 */
public class EmployeeStatusMonth extends DataEntity<EmployeeStatusMonth> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// 用户ID
	private String status;		// 用户状态
	private String projectId;		// 项目ID
	private String projectName;     //项目名称
	private String nodeId;      //项目节点ID
	private String nodeName; //项目节点名称
	private String baseId;		// 基地ID
	private String days;		// 保持天数
	private String datetime;		// 统计年月
	private Date beginCreateTime;		// 开始 创建时间
	private Date endCreateTime;		// 结束 创建时间
	private Date beginUpdateTime;		// 开始 更新时间
	private Date endUpdateTime;		// 结束 更新时间
	private String monthStatus;     //月数据状态
	
	private Integer projectInDays; //项目中天数
	private Integer inOutDays; //待命中天数
	
	public EmployeeStatusMonth() {
		super();
	}

	public EmployeeStatusMonth(String id){
		super(id);
	}

	@NotNull(message="用户ID不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=8, message="用户状态长度必须介于 1 和 8 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=32, message="项目ID长度必须介于 0 和 32 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=1, max=32, message="基地ID长度必须介于 1 和 32 之间")
	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	
	@Length(min=1, max=11, message="保持天数长度必须介于 1 和 11 之间")
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}
	
	@Length(min=0, max=16, message="统计年月长度必须介于 0 和 16 之间")
	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	public Date getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
		
	public Date getBeginUpdateTime() {
		return beginUpdateTime;
	}

	public void setBeginUpdateTime(Date beginUpdateTime) {
		this.beginUpdateTime = beginUpdateTime;
	}
	
	public Date getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(Date endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getMonthStatus() {
		return monthStatus;
	}

	public void setMonthStatus(String monthStatus) {
		this.monthStatus = monthStatus;
	}

	@Override
	public String toString() {
		return "EmployeeStatusMonth [user=" + user + ", status=" + status + ", projectId=" + projectId
				+ ", projectName=" + projectName + ", nodeId=" + nodeId + ", nodeName=" + nodeName + ", baseId="
				+ baseId + ", days=" + days + ", datetime=" + datetime + ", beginCreateTime=" + beginCreateTime
				+ ", endCreateTime=" + endCreateTime + ", beginUpdateTime=" + beginUpdateTime + ", endUpdateTime="
				+ endUpdateTime + ", monthStatus=" + monthStatus + ", projectInDays=" + projectInDays + ", inOutDays="
				+ inOutDays + "]";
	}
	
}