/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.resources;

import java.math.BigDecimal;
import java.util.List;

import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请request
 * @author xuby
 * @version 2018-05-30
 */
@ApiModel(value="资源申请对象")
public class ResourcesApplyRequest{
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="主键ID")
	private String id;  //主键ID
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="需求人数")
	private String demandPersonelNum;		// 需求人数
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="期望抵达日期")
	private long expectDate;		// 期望抵达日期
	
	@IntfzValid(min=0, max = 100, nullable=false)
	@ApiModelProperty(value="预计时长")
	private String timeLong;		// 预计时长
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="预算金额")
	private BigDecimal amountSum;		// 预算总金额
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="资源类型(人力资源/物品资源)")
	private String resourcesType;		// 资源类型(人力资源/物品资源)
	
	@IntfzValid(max = 4000)
	@ApiModelProperty(value="备注")
	private String remarks;  //备注
	private String producSide;//产品端
	@ApiModelProperty(value="指派人员")
	private List<ResourcesAssignRequest> assignList; //指派人员


	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public String getDemandPersonelNum() {
		return demandPersonelNum;
	}

	public void setDemandPersonelNum(String demandPersonelNum) {
		this.demandPersonelNum = demandPersonelNum;
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
	
	public String getResourcesType() {
		return resourcesType;
	}

	public void setResourcesType(String resourcesType) {
		this.resourcesType = resourcesType;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public BigDecimal getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(BigDecimal amountSum) {
		this.amountSum = amountSum;
	}

	public List<ResourcesAssignRequest> getAssignList() {
		return assignList;
	}

	public void setAssignList(List<ResourcesAssignRequest> assignList) {
		this.assignList = assignList;
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "ResourcesApplyRequest [id=" + id + ", procInsId=" + procInsId + ", projectId=" + projectId
				+ ", demandPersonelNum=" + demandPersonelNum + ", expectDate=" + expectDate + ", timeLong=" + timeLong
				+ ", amountSum=" + amountSum + ", resourcesType=" + resourcesType + ", remarks=" + remarks
				+ ", assignList=" + assignList + "]";
	}
}