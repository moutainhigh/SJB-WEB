package sijibao.oa.jeesite.modules.intfz.response.resources;


import java.math.BigDecimal;
import java.util.List;

import com.sijibao.oa.modules.intfz.response.intfzwebresp.ResourcesAssignResponse;

import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请办理返回对象信息
 * @author wanxb
 *
 */
public class ResourcesHandleFlowResponse {
	@ApiModelProperty(value="主键id")
	private String id;				//主键id
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	@ApiModelProperty(value="申请人编码")
	private String applyPerCode;		// 申请人编码
	@ApiModelProperty(value="资源类型")
	private String resourcesType;	//资源类型
	@ApiModelProperty(value="资源类型文本")
	private String resourcesTypeValue;	//资源类型
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	@ApiModelProperty(value="归属部门id")
	private String officeId;//归属部门id
	@ApiModelProperty(value="归属部门name")
	private String officeName;//归属部门name
	@ApiModelProperty(value="所属岗位code")
	private String postCode;//所属岗位code
	@ApiModelProperty(value="所属岗位name")
	private String postName;//所属岗位name
	@ApiModelProperty(value="关联主题")
	private String relationTheme;		// 关联主题
	@ApiModelProperty(value="关联主题名称")
	private String relationThemeName;		// 关联主题名称
	@ApiModelProperty(value="办理类型value(主动发起/被动发起)")
	private String handleTypeValue;		// 办理类型value(主动发起/被动发起)
	@ApiModelProperty(value="办理类型(主动发起/被动发起)")
	private String handleType;		// 办理类型(主动发起/被动发起)
	@ApiModelProperty(value="期望抵达日期")
	private long expectDate;		// 期望抵达日期
	@ApiModelProperty(value="预计时长")
	private String timeLong;		// 预计时长
	@ApiModelProperty(value="预算总金额")
	private BigDecimal amountSum;		// 预算总金额
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="项目负责人ID")
	private String projectPersonelId;		// 项目负责人ID
	@ApiModelProperty(value="项目负责人")
	private String projectPersonel;		// 项目负责人
	@ApiModelProperty(value="需求数量")
	private int personelNum;	//需求数量
	@ApiModelProperty(value="审批状态")
	private String resourcesHandleStatus;	//审批状态
	@ApiModelProperty(value="审批状态文本")
	private String resourcesHandleStatusValue;	//审批状态
	@ApiModelProperty(value="指派列表")
	private List<ResourcesAssignResponse> resourcesAssignResponseList;//指派列表
	@ApiModelProperty(value="当前环节是否可编辑信息(1:允许，0：不允许)")
	private int isDeit;      //是否可编辑信息
	@ApiModelProperty(value="当前环节是否可以指派人员(1:允许，0：不允许)")
	private int isAssign; //当前环节可以指派的人员信息
	@ApiModelProperty(value="是否可驳回:1是0否")
	private int isBack;			//当前环节是否可以驳回
	@ApiModelProperty(value="是否可撤回:1是0否")
	private int isCancel;       //当前环节是否可以撤回
	@ApiModelProperty(value="备注")
	private String remarks;
	@ApiModelProperty(value="编辑节点")
	private String modify; //编辑节点
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
	public String getResourcesTypeValue() {
		return resourcesTypeValue;
	}
	public void setResourcesTypeValue(String resourcesTypeValue) {
		this.resourcesTypeValue = resourcesTypeValue;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
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
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getRelationTheme() {
		return relationTheme;
	}
	public void setRelationTheme(String relationTheme) {
		this.relationTheme = relationTheme;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
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
	public String getProjectPersonelId() {
		return projectPersonelId;
	}
	public void setProjectPersonelId(String projectPersonelId) {
		this.projectPersonelId = projectPersonelId;
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
	public String getResourcesHandleStatusValue() {
		return resourcesHandleStatusValue;
	}
	public void setResourcesHandleStatusValue(String resourcesHandleStatusValue) {
		this.resourcesHandleStatusValue = resourcesHandleStatusValue;
	}
	public List<ResourcesAssignResponse> getResourcesAssignResponseList() {
		return resourcesAssignResponseList;
	}
	public void setResourcesAssignResponseList(List<ResourcesAssignResponse> resourcesAssignResponseList) {
		this.resourcesAssignResponseList = resourcesAssignResponseList;
	}
	public String getHandleTypeValue() {
		return handleTypeValue;
	}
	public void setHandleTypeValue(String handleTypeValue) {
		this.handleTypeValue = handleTypeValue;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public long getExpectDate() {
		return expectDate;
	}
	public void setExpectDate(long expectDate) {
		this.expectDate = expectDate;
	}
	public String getTimeLong() {
		return timeLong;
	}
	public void setTimeLong(String timeLong) {
		this.timeLong = timeLong;
	}
	public BigDecimal getAmountSum() {
		return amountSum;
	}
	public void setAmountSum(BigDecimal amountSum) {
		this.amountSum = amountSum;
	}
	public int getIsDeit() {
		return isDeit;
	}
	public void setIsDeit(int isDeit) {
		this.isDeit = isDeit;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getModify() {
		return modify;
	}
	public void setModify(String modify) {
		this.modify = modify;
	}
	public String getApplyPerCode() {
		return applyPerCode;
	}
	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	public String getProjectPersonel() {
		return projectPersonel;
	}
	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}
	public int getIsAssign() {
		return isAssign;
	}
	public void setIsAssign(int isAssign) {
		this.isAssign = isAssign;
	}
	public int getIsBack() {
		return isBack;
	}
	public void setIsBack(int isBack) {
		this.isBack = isBack;
	}
	public int getIsCancel() {
		return isCancel;
	}
	public void setIsCancel(int isCancel) {
		this.isCancel = isCancel;
	}
	public String getRelationThemeName() {
		return relationThemeName;
	}
	public void setRelationThemeName(String relationThemeName) {
		this.relationThemeName = relationThemeName;
	}
	@Override
	public String toString() {
		return "ResourcesHandleFlowDetailResponse [id=" + id + ", procCode=" + procCode + ", procName=" + procName
				+ ", applyPerName=" + applyPerName + ", applyPerCode=" + applyPerCode + ", resourcesType="
				+ resourcesType + ", resourcesTypeValue=" + resourcesTypeValue + ", applyTime=" + applyTime
				+ ", procInsId=" + procInsId + ", officeId=" + officeId + ", officeName=" + officeName + ", postCode="
				+ postCode + ", postName=" + postName + ", relationTheme=" + relationTheme + ", relationThemeName="
				+ relationThemeName + ", handleTypeValue=" + handleTypeValue + ", handleType=" + handleType
				+ ", expectDate=" + expectDate + ", timeLong=" + timeLong + ", amountSum=" + amountSum + ", projectId="
				+ projectId + ", projectName=" + projectName + ", projectPersonelId=" + projectPersonelId
				+ ", projectPersonel=" + projectPersonel + ", personelNum=" + personelNum + ", resourcesHandleStatus="
				+ resourcesHandleStatus + ", resourcesHandleStatusValue=" + resourcesHandleStatusValue
				+ ", resourcesAssignResponseList=" + resourcesAssignResponseList + ", isDeit=" + isDeit + ", isAssign="
				+ isAssign + ", isBack=" + isBack + ", isCancel=" + isCancel + ", remarks=" + remarks + ", modify="
				+ modify + "]";
	}
	
}
