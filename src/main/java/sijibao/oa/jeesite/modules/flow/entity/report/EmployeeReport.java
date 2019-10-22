package sijibao.oa.jeesite.modules.flow.entity.report;

import java.math.BigDecimal;
import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 员工费用报表Entity
 * @author wanxb
 */
public class EmployeeReport extends DataEntity<EmployeeReport> {

	private static final long serialVersionUID = 1L;
	private String employeeName;//员工name 
	private String employeeLoginName;//员工登入名
	private BigDecimal expenseAmt; //报销金额
	private Date taskFinishTime; //审批结束时间
	private String year;  //年份
	private Office office;//部门
	private String isDetail;   //是否有明细
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
	public Office getOffice() {
		return office;
	}
	public void setOffice(Office office) {
		this.office = office;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String getEmployeeLoginName() {
		return employeeLoginName;
	}
	public void setEmployeeLoginName(String employeeLoginName) {
		this.employeeLoginName = employeeLoginName;
	}
	public String getIsDetail() {
		return isDetail;
	}
	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}
	@Override
	public String toString() {
		return "EmployeeReport [employeeName=" + employeeName + ", employeeLoginName=" + employeeLoginName
				+ ", expenseAmt=" + expenseAmt + ", taskFinishTime=" + taskFinishTime + ", year=" + year + ", office="
				+ office + ", isDetail=" + isDetail + "]";
	}
	
}	
