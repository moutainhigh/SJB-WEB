package sijibao.oa.jeesite.modules.intfz.request.expense;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 流程申请处理对象
 * @author Petter
 */
@ApiModel
public class FlowHandleReq {

	@ApiModelProperty(value="流程申请ID")
	private String expenseFlowId;//流程申请ID
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="流程状态:1审批结束2审批中3单据被驳回4单据保存")
	private String expenseStatus;//流程状态
	@ApiModelProperty(value="申请人姓名(模糊匹配)")
	private String applyName;
	@ApiModelProperty(value="流程编号(模糊匹配)")
	private String procCode;
	@ApiModelProperty(value="流程名称(模糊匹配)")
	private String procName;
	@ApiModelProperty(value="申请时间开始(格式：yyyy-MM-dd)")
	private long applyTimeStart=0l;
	@ApiModelProperty(value="申请时间结束(格式：yyyy-MM-dd)")
	private long applyTimeEnd=0l;
	@ApiModelProperty(value="部门编号")
	private String officeId;
	@ApiModelProperty(value="部门名称(模糊匹配)")
	private String officeName;
	@ApiModelProperty(value="业务ID")
	private String businessId;
	@ApiModelProperty(value="单据类型:1:市场,2:实施,3:报销,4:接待申请,5:出差申请,6:资源申请,7:资源办理")
	private String billType;
	@ApiModelProperty(value="项目ID")
	private String projectId;
	public String getExpenseFlowId() {
		return expenseFlowId;
	}
	public void setExpenseFlowId(String expenseFlowId) {
		this.expenseFlowId = expenseFlowId;
	}
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
	public String getApplyName() {
		return applyName;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}
	public String getProcCode() {
		return procCode;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public long getApplyTimeStart() {
		return applyTimeStart;
	}
	public void setApplyTimeStart(long applyTimeStart) {
		this.applyTimeStart = applyTimeStart;
	}
	public long getApplyTimeEnd() {
		return applyTimeEnd;
	}
	public void setApplyTimeEnd(long applyTimeEnd) {
		this.applyTimeEnd = applyTimeEnd;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
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
	@Override
	public String toString() {
		return "FlowHandleReq [expenseFlowId=" + expenseFlowId + ", pageNo=" + pageNo + ", pageSize=" + pageSize
				+ ", expenseStatus=" + expenseStatus + ", applyName=" + applyName + ", procCode=" + procCode
				+ ", procName=" + procName + ", applyTimeStart=" + applyTimeStart + ", applyTimeEnd=" + applyTimeEnd
				+ ", officeId=" + officeId + ", officeName=" + officeName + ", businessId=" + businessId + ", billType="
				+ billType + ", projectId=" + projectId + "]";
	}
}
