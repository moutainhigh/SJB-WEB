package sijibao.oa.jeesite.modules.flow.entity.report;

import java.math.BigDecimal;
import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 部门费用报表实体
 * @author xby
 */
public class OfficeCostReport extends DataEntity<OfficeCostReport> { 
	
	private static final long serialVersionUID = 1L;
	
	private String officeId; //部门ID
	private String officeName; //部门名称
	private BigDecimal expenseAmt; //报销金额
	private Date taskFinishTime; //审批结束时间
	private String isDetail;   //是否有明细
	private String parentIds;  //父级ids
	
	private String flowFinishTime; //审批结束时间（YYYY-MM）
	private Date beginTime;    //开始搜索时间
	private Date endTime;      //结束搜索时间
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
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
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getFlowFinishTime() {
		return flowFinishTime;
	}
	public void setFlowFinishTime(String flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}
	
}
