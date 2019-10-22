/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 资源申请办理Entity
 * @author xuby
 * @version 2018-05-29
 */
public class ResourcesHandleFlow extends ActEntity<ResourcesHandleFlow> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例ID
	private String procCode;		// 流程编号
	private String procName;		// 流程名称
	private String applyPerCode;		// 申请人编号
	private String applyPerName;		// 申请人名称
	private String resourcesType;	//资源类型
	private String resourcesTypeValue;	//资源类型
	private Date applyTime;		// 申请时间
	private Office office;		// 所属部门ID
	private String officeName;		// 所属部门名称
	private String postCode;		// 所属岗位编码
	private String postName;		// 所属岗位名称
	private String relationTheme;		// 关联主题
	private String handleType;		// 办理类型(主动发起/被动发起)
	private String handleTypeValue;		// 办理类型(主动发起/被动发起)
	private String projectId;		// 项目ID
	private String projectName;		// 项目名称
	private String projectPersonel;		// 项目负责人
	private Integer personelNum;		// 满足人数
	private String resourcesHandleStatus;		// 需求状态
	private String resourcesHandleStatusIn;		// 需求状态 in查询
	private String resourcesHandleStatusValue;		// 需求状态
	private Date flowFinishTime;		// 流程审批结束时间
	private Date beginApplyTime;		// 开始 申请时间
	private Date endApplyTime;		// 结束 申请时间
	private Date expectDate;		// 期望抵达日期
	private String timeLong;		// 预计时长
	private BigDecimal amountSum;		// 预算总金额
	
	private String targetAssign; //被指派人员
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public Date getBeginApplyTime() {
		return beginApplyTime;
	}

	public void setBeginApplyTime(Date beginApplyTime) {
		this.beginApplyTime = beginApplyTime;
	}

	public Date getEndApplyTime() {
		return endApplyTime;
	}

	public void setEndApplyTime(Date endApplyTime) {
		this.endApplyTime = endApplyTime;
	}

	public ResourcesHandleFlow() {
		super();
	}

	public ResourcesHandleFlow(String id){
		super(id);
	}

	@Length(min=0, max=64, message="流程实例ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
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
	
	@Length(min=0, max=32, message="申请人编号长度必须介于 0 和 32 之间")
	public String getApplyPerCode() {
		return applyPerCode;
	}

	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	
	@Length(min=0, max=100, message="申请人名称长度必须介于 0 和 100 之间")
	public String getApplyPerName() {
		return applyPerName;
	}

	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=100, message="所属部门名称长度必须介于 0 和 100 之间")
	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	@Length(min=0, max=32, message="所属岗位编码长度必须介于 0 和 32 之间")
	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	@Length(min=0, max=64, message="所属岗位名称长度必须介于 0 和 64 之间")
	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	@Length(min=0, max=32, message="关联主题长度必须介于 0 和 32 之间")
	public String getRelationTheme() {
		return relationTheme;
	}

	public void setRelationTheme(String relationTheme) {
		this.relationTheme = relationTheme;
	}
	
	@Length(min=0, max=8, message="办理类型(主动发起/被动发起)长度必须介于 0 和 8 之间")
	public String getHandleType() {
		return handleType;
	}

	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	
	
	public String getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}

	@Length(min=0, max=32, message="项目ID长度必须介于 0 和 32 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=300, message="项目名称长度必须介于 0 和 300 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getProjectPersonel() {
		return projectPersonel;
	}

	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}

	public Integer getPersonelNum() {
		return personelNum;
	}

	public void setPersonelNum(Integer personelNum) {
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

	public Date getFlowFinishTime() {
		return flowFinishTime;
	}

	public void setFlowFinishTime(Date flowFinishTime) {
		this.flowFinishTime = flowFinishTime;
	}

	public Date getExpectDate() {
		return expectDate;
	}

	public void setExpectDate(Date expectDate) {
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

	public String getHandleTypeValue() {
		return handleTypeValue;
	}

	public void setHandleTypeValue(String handleTypeValue) {
		this.handleTypeValue = handleTypeValue;
	}

	public String getTargetAssign() {
		return targetAssign;
	}

	public void setTargetAssign(String targetAssign) {
		this.targetAssign = targetAssign;
	}

	public String getResourcesHandleStatusIn() {
		return resourcesHandleStatusIn;
	}

	public void setResourcesHandleStatusIn(String resourcesHandleStatusIn) {
		this.resourcesHandleStatusIn = resourcesHandleStatusIn;
	}
	
}