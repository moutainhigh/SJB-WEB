package sijibao.oa.jeesite.modules.intfz.request.resources;

import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请办理请求对象
 * @author wanxb
 *
 */
public class ResourcesHandleFlowListReq {
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="主键id")
	private String id;
	@ApiModelProperty(value="开始申请时间")
	private long start = 0l;//开始申请时间
	@ApiModelProperty(value="结束申请时间")
	private long end = 0l;//结束申请时间
	@ApiModelProperty(value="流程编号")
	private String procCode; //流程编号
	@ApiModelProperty(value="流程名称")
	private String procName; //流程名称
	@ApiModelProperty(value="申请状态")
	private String resourcesHandleStatus; //申请状态
	@ApiModelProperty(value="项目ID")
	private String projectId; //项目ID
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
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
	public String getResourcesHandleStatus() {
		return resourcesHandleStatus;
	}
	public void setResourcesHandleStatus(String resourcesHandleStatus) {
		this.resourcesHandleStatus = resourcesHandleStatus;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	@Override
	public String toString() {
		return "ResourcesHandleFlowHandleReq [pageNo=" + pageNo + ", pageSize=" + pageSize + ", id=" + id + ", start="
				+ start + ", end=" + end + ", procCode=" + procCode + ", procName=" + procName
				+ ", resourcesHandleStatus=" + resourcesHandleStatus + "]";
	}
}
