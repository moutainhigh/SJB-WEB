package sijibao.oa.jeesite.modules.intfz.request.projectapproval;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 立项申请列表查询
 * @author xby
 */
@ApiModel(value="立项申请请求对象")
public class MainProjectApprovalFlowListReq extends PageRequest{

	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="流程编号")
	private String procCode;//流程编号
	@ApiModelProperty(value="项目名称")
	private String projectName;//项目名称
	@ApiModelProperty(value="部门或名称")
	private String officeOrName;//部门或名称
	@ApiModelProperty(value="申请开始时间")
	private long beginApplyTime;//申请开始时间
	@ApiModelProperty(value="申请结束时间")
	private long endApplyTime;//申请结束时间
	@ApiModelProperty(value="申请人姓名(模糊匹配)")
	private String applyName;
	@ApiModelProperty(value="部门编号")
	private String officeId;
	@ApiModelProperty(value="部门名称(模糊匹配)")
	private String officeName;
	@ApiModelProperty(value="开始时间")
	private long applyTimeStart;//申请开始时间
	@ApiModelProperty(value="结束时间")
	private long applyTimeEnd;//申请结束时间

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

	@ApiModelProperty(value="立项状态:1审批结束2审批中3单据被驳回4单据保存")
	private String projectApprovalStatus;//立项状态

	public String getProjectApprovalStatus() {
		return projectApprovalStatus;
	}

	public void setProjectApprovalStatus(String projectApprovalStatus) {
		this.projectApprovalStatus = projectApprovalStatus;
	}

	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOfficeOrName() {
		return officeOrName;
	}

	public void setOfficeOrName(String officeOrName) {
		this.officeOrName = officeOrName;
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

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

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

	@Override
	public String toString() {
		return "MainProjectApprovalFlowListReq{" +
				"pageNo=" + pageNo +
				", pageSize=" + pageSize +
				", procCode='" + procCode + '\'' +
				", projectName='" + projectName + '\'' +
				", officeOrName='" + officeOrName + '\'' +
				", beginApplyTime=" + beginApplyTime +
				", endApplyTime=" + endApplyTime +
				'}';
	}
}
