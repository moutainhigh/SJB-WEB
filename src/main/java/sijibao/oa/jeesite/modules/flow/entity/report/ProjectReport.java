package sijibao.oa.jeesite.modules.flow.entity.report;

import java.math.BigDecimal;
import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 项目费用报表Entity
 * @author wanxb
 */
public class ProjectReport extends DataEntity<ProjectReport> {

	private static final long serialVersionUID = 1L;
	private String projectId;//项目id
	private String projectName;//项目名称
	private String projectState;//项目状态
	private BigDecimal expenseAmt; //报销金额
	private Date taskFinishTime; //审批结束时间
	private String year;  //年份
	
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
	public BigDecimal getExpenseAmt() {
		return expenseAmt;
	}
	public void setExpenseAmt(BigDecimal expenseAmt) {
		this.expenseAmt = expenseAmt;
	}
	public Date getTaskFinishTime() {
		return taskFinishTime;
	}
	public void setTaskFinishTime(Date taskFinishTime) {
		this.taskFinishTime = taskFinishTime;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Override
	public String toString() {
		return "ProjectReport [projectId=" + projectId + ", projectName=" + projectName + ", projectState="
				+ projectState + ", expenseAmt=" + expenseAmt + ", taskFinishTime=" + taskFinishTime + ", year=" + year
				+ "]";
	}
	
	
}	
