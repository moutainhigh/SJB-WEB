package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq;


import io.swagger.annotations.ApiModelProperty;

/**
 * 查询报销关联主题信息接收参数 
 * @author xuby
 */
public class QueryFlowRevenceRequest {
	@ApiModelProperty(value="报销类型 (1:常规报销,2:接待报销,3:出差申请)")
	private String applyType; //报销类型 (1:常规报销,2:接待报销) 
	@ApiModelProperty(value="申请时间-开始")
	private long beginApplyTime = 0l;		// 开始 申请时间
	@ApiModelProperty(value="申请时间-结束")
	private long endApplyTime = 0l;		// 结束 申请时间
	@ApiModelProperty(value="项目名称")
	private String projectName; //项目名称
	@ApiModelProperty(value="流程名称")
	private String procName; //流程名称
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
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
	@Override
	public String toString() {
		return "QueryFlowRevenceRequest [applyType=" + applyType + ", beginApplyTime=" + beginApplyTime
				+ ", endApplyTime=" + endApplyTime + ", projectName=" + projectName + ", procName=" + procName
				+ ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
	}
	
}
