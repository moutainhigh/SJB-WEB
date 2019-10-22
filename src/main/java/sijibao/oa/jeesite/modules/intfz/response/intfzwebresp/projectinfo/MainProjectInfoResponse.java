package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目管理--列表返回对象
 * @author wuys
 *
 */
@ApiModel(value="项目管理--列表返回对象")
public class MainProjectInfoResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键id")
	private String id;				//主键id
	@ApiModelProperty(value="项目名称")
	private String projectName;//项目名称
	@ApiModelProperty(value="项目编码")
	private String projectCode;//项目Code
	@ApiModelProperty(value="项目类型名称")
	private String projectTypeName;//项目类型
	@ApiModelProperty(value="归属部门name")
	private String officeName;//归属部门name
	@ApiModelProperty(value="归属客户")
	private String custInfoId;//归属客户
	@ApiModelProperty(value="归属客户name")
	private String custInfoName;//归属客户name
	@ApiModelProperty(value="项目状态0待上线1已上线2已关闭")
	private String projectStateName;//项目状态名称
	@ApiModelProperty(value="上线时间格式yyyy-MM-dd")
	private long onLineDate = 0l;//上线时间格式yyyy-MM-dd
	@ApiModelProperty(value="项目负责人name")
	private String projectLeaderName;//项目负责人name
	@ApiModelProperty(value="更新时间")
	private long updateTime = 0l;//更新时间
	@ApiModelProperty(value="市场负责人name")
	private String marketLeaderName;//市场负责人name
	@ApiModelProperty(value="实施负责人name")
	private String impleLeaderName;//实施负责人name
	@ApiModelProperty(value="备注")
	private String remarks;//备注
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getCustInfoId() {
		return custInfoId;
	}
	public void setCustInfoId(String custInfoId) {
		this.custInfoId = custInfoId;
	}
	public long getOnLineDate() {
		return onLineDate;
	}
	public void setOnLineDate(long onLineDate) {
		this.onLineDate = onLineDate;
	}
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public String getProjectTypeName() {
		return projectTypeName;
	}
	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getCustInfoName() {
		return custInfoName;
	}
	public void setCustInfoName(String custInfoName) {
		this.custInfoName = custInfoName;
	}
	public String getProjectStateName() {
		return projectStateName;
	}
	public void setProjectStateName(String projectStateName) {
		this.projectStateName = projectStateName;
	}
	public String getProjectLeaderName() {
		return projectLeaderName;
	}
	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public String getImpleLeaderName() {
		return impleLeaderName;
	}
	public void setImpleLeaderName(String impleLeaderName) {
		this.impleLeaderName = impleLeaderName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Override
	public String toString() {
		return "ProjectInfoResponse [id=" + id + ", projectName=" + projectName + ", projectCode=" + projectCode
				+ ", projectTypeName=" + projectTypeName + ", officeName=" + officeName + ", custInfoId=" + custInfoId
				+ ", custInfoName=" + custInfoName + ", projectStateName=" + projectStateName + ", onLineDate="
				+ onLineDate + ", projectLeaderName=" + projectLeaderName + ", updateTime=" + updateTime
				+ ", marketLeaderName=" + marketLeaderName + ", impleLeaderName=" + impleLeaderName + ", remarks="
				+ remarks + "]";
	}
	
}
