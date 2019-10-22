package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq;

import io.swagger.annotations.ApiModelProperty;

public class BringRemitHandleRequest {
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="申请人编号")
	private String applyPerCode;		// 申请人编号
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人编号
	@ApiModelProperty(value="开始 申请时间")
	private long beginApplyTime=0l;		// 开始 申请时间
	@ApiModelProperty(value="结束 申请时间")
	private long endApplyTime=0l;		// 结束 申请时间
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	@ApiModelProperty(value="报销状态")
	private String expenseStatus;   //报销状态
	
	
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
	public String getProcCode() {
		return procCode;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getApplyPerCode() {
		return applyPerCode;
	}
	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	public long getBeginApplyTime() {
		return beginApplyTime;
	}
	public void setBeginApplyTime(long beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}
	public long getEndApplyTime() {
		return endApplyTime;
	}
	public void setEndApplyTime(long endApplyTime) {
		this.endApplyTime = endApplyTime;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getExpenseStatus() {
		return expenseStatus;
	}
	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	public String getApplyPerName() {
		return applyPerName;
	}
	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}
	@Override
	public String toString() {
		return "BringRemitHandleRequest [pageNo=" + pageNo + ", pageSize=" + pageSize + ", procCode=" + procCode
				+ ", procName=" + procName + ", applyPerCode=" + applyPerCode + ", applyPerName=" + applyPerName
				+ ", beginApplyTime=" + beginApplyTime + ", endApplyTime=" + endApplyTime + ", projectId=" + projectId
				+ ", expenseStatus=" + expenseStatus + "]";
	}
}
