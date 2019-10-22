/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.resources;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请办理Request
 * @author xuby
 * @version 2018-05-29
 */
@ApiModel(value="资源办理对象")
public class ResourcesHandleFlowRequest{
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="主键ID")
	private String id;              //主键ID
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="关联主题")
	private String relationTheme;		// 关联主题
	
	@IntfzValid(min=0, max = 8, nullable=false)
	@ApiModelProperty(value="办理类型(主动发起/被动发起)")
	private String handleType;		// 办理类型(主动发起/被动发起)
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="满足人数")
	private int personelNum;		// 满足人数
	
	@ApiModelProperty(value="指派人员")
	private List<ResourcesAssignRequest> assignList; //指派人员
	
//	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="资源类型")
	private String resourcesType; //资源类型
	
	@IntfzValid(max = 4000)
	@ApiModelProperty(value="备注")
	private String remarks; //备注
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="期望抵达日期")
	private long expectDate = 0l;		// 期望抵达日期
	
	@IntfzValid(min=0, max = 100, nullable=true)
	@ApiModelProperty(value="预计时长")
	private String timeLong;		// 预计时长
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="预算金额")
	private BigDecimal amountSum;		// 预算总金额
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	@Length(min=0, max=64, message="流程实例ID长度必须介于 0 和 64 之间")
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@Length(min=0, max=16, message="流程编号长度必须介于 0 和 16 之间")
	public String getProcCode() {
		return procCode;
	}

	public void setProcCode(String procCode) {
		this.procCode = procCode;
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
	
	@Length(min=0, max=32, message="项目ID长度必须介于 0 和 32 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=5, message="满足人数长度必须介于 0 和 5 之间")
	public int getPersonelNum() {
		return personelNum;
	}

	public void setPersonelNum(int personelNum) {
		this.personelNum = personelNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ResourcesAssignRequest> getAssignList() {
		return assignList;
	}

	public void setAssignList(List<ResourcesAssignRequest> assignList) {
		this.assignList = assignList;
	}

	public String getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	@Override
	public String toString() {
		return "ResourcesHandleFlowRequest [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", relationTheme=" + relationTheme + ", handleType=" + handleType + ", projectId=" + projectId
				+ ", personelNum=" + personelNum + ", assignList=" + assignList + ", resourcesType=" + resourcesType
				+ ", remarks=" + remarks + ", expectDate=" + expectDate + ", timeLong=" + timeLong + ", amountSum="
				+ amountSum + "]";
	}
}