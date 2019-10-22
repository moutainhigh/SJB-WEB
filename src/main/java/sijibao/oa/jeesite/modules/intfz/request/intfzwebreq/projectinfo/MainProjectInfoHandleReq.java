package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目管理服务--查询列表对象
 * @author wanxb
 *
 */
@ApiModel(value="项目管理服务--查询列表对象")
public class MainProjectInfoHandleReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="项目名称")
	private String projectName;//项目名称
	@ApiModelProperty(value="项目类型0公司项目1市场项目")
	private String projectType;//项目类型0公司项目1市场项目
	@ApiModelProperty(value="项目状态")
	private String projectState;//项目状态
	@ApiModelProperty(value="归属部门")
	private String officeId;//归属部门
	
	@ApiModelProperty(value="负责人name")
	private String leaderName;//负责人name
	@ApiModelProperty(value="负责人类型")
	private String leaderType;//负责人类型
	
	@ApiModelProperty(value="时间类型")
	private String timeType;//时间类型
	@ApiModelProperty(value="开始搜索时间（更新时间）")
	private long applyTimeStart = 0l;//开始搜索时间
	@ApiModelProperty(value="结束搜索时间（更新时间）")
	private long applyTimeEnd = 0l;//结束搜索时间
	@ApiModelProperty(value="企业编号")
	private String holderCode;//企业编号

	public String getHolderCode() {
		return holderCode;
	}

	public void setHolderCode(String holderCode) {
		this.holderCode = holderCode;
	}
	
	
	public String getProjectState() {
		return projectState;
	}
	public void setProjectState(String projectState) {
		this.projectState = projectState;
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
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getTimeType() {
		return timeType;
	}
	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}
	public String getLeaderName() {
		return leaderName;
	}
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}
	public String getLeaderType() {
		return leaderType;
	}
	public void setLeaderType(String leaderType) {
		this.leaderType = leaderType;
	}
	@Override
	public String toString() {
		return "MainProjectInfoHandleReq [pageNo=" + pageNo + ", pageSize=" + pageSize + ", projectName=" + projectName
				+ ", projectType=" + projectType + ", projectState=" + projectState + ", officeId=" + officeId
				+ ", leaderName=" + leaderName + ", leaderType=" + leaderType + ", timeType=" + timeType
				+ ", applyTimeStart=" + applyTimeStart + ", applyTimeEnd=" + applyTimeEnd + "]";
	}
	
}
