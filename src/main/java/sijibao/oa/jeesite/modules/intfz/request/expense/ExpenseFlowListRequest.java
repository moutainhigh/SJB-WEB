package sijibao.oa.jeesite.modules.intfz.request.expense;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销单据查询处理对象
 * @author huangkai
 */
@ApiModel(value="报销单据查询处理对象")
public class ExpenseFlowListRequest {

	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="流程状态:1审批结束2审批中")
	private String expenseStatus;//流程状态
	@ApiModelProperty(value="时间类型:1提交时间2报销明细开始时间3报销明细结束时间")
	private String timeType;
	@ApiModelProperty(value="开始时间(格式：yyyy-MM-dd)")
	private long timeStart=0l;
	@ApiModelProperty(value="结束时间(格式：yyyy-MM-dd)")
	private long timeEnd=0l;
	@ApiModelProperty(value="部门编号")
	private String officeId;
	@ApiModelProperty(value="部门名称")
	private String officeName;
	@ApiModelProperty(value="项目ID")
	private String projectId;
	@ApiModelProperty(value="项目名称")
	private String projectName;
	@ApiModelProperty(value="科目类型: 1一级科目2二级科目")
	private String subType;
	@ApiModelProperty(value="科目编号")
	private String[] subNum;
	@ApiModelProperty(value="金额区间(元) 起始")
	private BigDecimal expenseAmtStart;
	@ApiModelProperty(value="金额区间(元) 结束")
	private BigDecimal expenseAmtEnd;

	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getExpenseStatus() {
		return expenseStatus;
	}
	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getTimeType() { return timeType; }
	public void setTimeType(String timeType) { this.timeType = timeType; }
	public long getTimeStart() { return timeStart; }
	public void setTimeStart(long timeStart) { this.timeStart = timeStart; }
	public long getTimeEnd() { return timeEnd; }
	public void setTimeEnd(long timeEnd) { this.timeEnd = timeEnd; }
	public String getSubType() { return subType; }
	public void setSubType(String subType) { this.subType = subType; }
	public BigDecimal getExpenseAmtStart() { return expenseAmtStart; }
	public void setExpenseAmtStart(BigDecimal expenseAmtStart) { this.expenseAmtStart = expenseAmtStart; }
	public BigDecimal getExpenseAmtEnd() { return expenseAmtEnd; }
	public void setExpenseAmtEnd(BigDecimal expenseAmtEnd) { this.expenseAmtEnd = expenseAmtEnd; }


	@Override
	public String toString() {
		return "ExpenseFlowListReq [pageNo=" + pageNo + ", pageSize=" + pageSize + ", expenseStatus=" + expenseStatus
				+ ", timeType=" + timeType + ", timeStart="
				+ timeStart + ", timeEnd=" + timeEnd + ", officeId=" + officeId + ", officeName="
				+ officeName + ", projectId=" + projectId + ", subType=" + subType
				+ ", expenseAmtStart=" + expenseAmtStart + ", expenseAmtEnd=" + expenseAmtEnd +"]";
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String[] getSubNum() {
		return subNum;
	}

	public void setSubNum(String[] subNum) {
		this.subNum = subNum;
	}
}
