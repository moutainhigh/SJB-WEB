package sijibao.oa.jeesite.modules.intfz.response.resources;


import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请办理返回对象信息
 * @author wanxb
 *
 */
public class ResourcesHandleFlowListResponse {
	@ApiModelProperty(value="主键id")
	private String id;				//主键id
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="申请人编号")
	private String applyPerCode;		// 申请人编号
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	@ApiModelProperty(value="资源类型")
	private String resourcesType;	//资源类型
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="项目负责人")
	private String projectPersonel;		// 项目负责人
	@ApiModelProperty(value="资源类型文本")
	private String resourcesTypeValue;	//资源类型
	@ApiModelProperty(value="办理类型")
	private String handleType; //办理类型
	@ApiModelProperty(value="办理类型文本")
	private String handleTypeValue; //办理类型
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="需求数量")
	private int personelNum;	//需求数量
	@ApiModelProperty(value="预算总金额")
	private BigDecimal amountSum;		// 预算总金额
	@ApiModelProperty(value="审批状态")
	private String resourcesHandleStatus;	//审批状态
	@ApiModelProperty(value="审批状态文本")
	private String resourcesHandleStatusValue;	//审批状态
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
	public String getResourcesTypeValue() {
		return resourcesTypeValue;
	}
	public void setResourcesTypeValue(String resourcesTypeValue) {
		this.resourcesTypeValue = resourcesTypeValue;
	}
	public String getResourcesHandleStatusValue() {
		return resourcesHandleStatusValue;
	}
	public void setResourcesHandleStatusValue(String resourcesHandleStatusValue) {
		this.resourcesHandleStatusValue = resourcesHandleStatusValue;
	}
	public String getApplyPerCode() {
		return applyPerCode;
	}
	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getHandleTypeValue() {
		return handleTypeValue;
	}
	public void setHandleTypeValue(String handleTypeValue) {
		this.handleTypeValue = handleTypeValue;
	}
	public String getProjectPersonel() {
		return projectPersonel;
	}
	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}
	public BigDecimal getAmountSum() {
		return amountSum;
	}
	public void setAmountSum(BigDecimal amountSum) {
		this.amountSum = amountSum;
	}
	@Override
	public String toString() {
		return "ResourcesHandleFlowResponse [id=" + id + ", procCode=" + procCode + ", procName=" + procName
				+ ", applyPerCode=" + applyPerCode + ", applyPerName=" + applyPerName + ", resourcesType="
				+ resourcesType + ", projectId=" + projectId + ", projectName=" + projectName + ", projectPersonel="
				+ projectPersonel + ", resourcesTypeValue=" + resourcesTypeValue + ", handleType=" + handleType
				+ ", handleTypeValue=" + handleTypeValue + ", applyTime=" + applyTime + ", personelNum=" + personelNum
				+ ", amountSum=" + amountSum + ", resourcesHandleStatus=" + resourcesHandleStatus
				+ ", resourcesHandleStatusValue=" + resourcesHandleStatusValue + "]";
	}
}
