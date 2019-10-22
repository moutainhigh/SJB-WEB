package sijibao.oa.jeesite.modules.flow.entity.report;

import java.math.BigDecimal;
import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 科目费用报表Entity
 * @author xby
 */
public class SubjectCostReport extends DataEntity<SubjectCostReport> {

	private static final long serialVersionUID = 1L;
	private String subjectCode; //科目编码
	private String subjectName; //科目名称
	private BigDecimal expenseAmt; //报销金额
	private Date taskFinishTime; //审批结束时间
	private String isDetail;   //是否有明细
	
	private Date beginTime;    //开始搜索时间
	private Date endTime;      //结束搜索时间
	private String firstCode;  //一级科目编码
	private String projectId;  //项目ID
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
	public String getIsDetail() {
		return isDetail;
	}
	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getFirstCode() {
		return firstCode;
	}
	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}	
