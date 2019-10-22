package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp;


import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请办理返回对象信息
 * @author wanxb
 *
 */
public class ResourcesApplyFlowResponse {
	@ApiModelProperty(value="主键id")
	private String id;				//主键id
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	@ApiModelProperty(value="项目名称")
	private String projectName;	//项目名称
	@ApiModelProperty(value="资源类型")
	private String resourcesType;	//资源类型
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="需求数量")
	private int personelNum;	//需求数量
	@ApiModelProperty(value="审批状态")
	private String resourcesHandleStatus;	//审批状态
	
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getApplyPerName() {
		return applyPerName;
	}
	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}
	public String getResourcesType() {
		return resourcesType;
	}
	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public int getPersonelNum() {
		return personelNum;
	}
	public void setPersonelNum(int personelNum) {
		this.personelNum = personelNum;
	}
	public String getResourcesHandleStatus() {
		return resourcesHandleStatus;
	}
	public void setResourcesHandleStatus(String resourcesHandleStatus) {
		this.resourcesHandleStatus = resourcesHandleStatus;
	}
	@Override
	public String toString() {
		return "ResourcesApplyFlowResponse [id=" + id + ", procCode=" + procCode + ", procName=" + procName
				+ ", applyPerName=" + applyPerName + ", projectName=" + projectName + ", resourcesType=" + resourcesType
				+ ", applyTime=" + applyTime + ", personelNum=" + personelNum + ", resourcesHandleStatus="
				+ resourcesHandleStatus + "]";
	}
	

}
