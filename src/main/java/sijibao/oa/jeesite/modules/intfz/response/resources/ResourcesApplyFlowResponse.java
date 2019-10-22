/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.resources;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.sijibao.oa.common.persistence.ActEntity;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.modules.flow.entity.ResourcesApply;
import com.sijibao.oa.modules.intfz.response.intfzwebresp.ResourcesAssignResponse;
import com.sijibao.oa.modules.sys.utils.DictUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * 资源申请Entity
 * @author xuby
 * @version 2018-05-23
 */
public class ResourcesApplyFlowResponse extends ActEntity<ResourcesApplyFlowResponse> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="主键ID")
	private String id; //主键ID
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	@ApiModelProperty(value="流程编号")
	private String procCode;		// 流程编号
	@ApiModelProperty(value="流程名称")
	private String procName;		// 流程名称
	@ApiModelProperty(value="申请人编号")
	private String applyPerCode;		// 申请人编号
	@ApiModelProperty(value="申请人名称")
	private String applyPerName;		// 申请人名称
	@ApiModelProperty(value="申请人岗位")
	private String postName; //申请人岗位
	@ApiModelProperty(value="所属部门名称")
	private String officeName;		//所属部门
	@ApiModelProperty(value="申请时间")
	private String applyTime;		// 申请时间
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目ID
	@ApiModelProperty(value="项目名称")
	private String projectName;		// 项目名称
	@ApiModelProperty(value="项目负责人")
	private String projectPersonel;  //项目负责人
	@ApiModelProperty(value="预算费用总额")
	private BigDecimal amountSum;		// 预算费用总额
	@ApiModelProperty(value="需求人数")
	private String demandPersonelNum;		// 需求人数
	@ApiModelProperty(value="期望抵达日期")
	private long expectDate;		// 期望抵达日期
	@ApiModelProperty(value="预计时长")
	private String timeLong;		// 预计时长
	@ApiModelProperty(value="资源类型")
	private String resourcesType;		// 资源类型(人力资源/物品资源)
	@ApiModelProperty(value="资源类型")
	private String resourcesTypeValue;		// 资源类型文本(人力资源/物品资源)
	@ApiModelProperty(value="申请状态")
	private String resourcesStatus;   //申请状态
	@ApiModelProperty(value="申请状态")
	private String resourcesStatusValue;   //申请状态
	@ApiModelProperty(value="当前环节是否可编辑信息(1:允许，0：不允许)")
	private int isDeit;      //是否可编辑信息
	@ApiModelProperty(value="是否当前人:1是0否")
	private int local;			//当前人:1
	@ApiModelProperty(value="备注")
	private String remarks;
	@ApiModelProperty(value="编辑节点")
	private String modify; //编辑节点
	@ApiModelProperty(value="指派明细")
	private List<ResourcesAssignResponse> assignList;
	public ResourcesApplyFlowResponse() {
		super();
	}
	
	public ResourcesApplyFlowResponse(ResourcesApply resourcesApply){
		this.id = resourcesApply.getId();
		this.procInsId = resourcesApply.getProcInsId();
		this.procCode = resourcesApply.getProcCode();
		this.procName = resourcesApply.getProcName();
		this.applyPerCode = resourcesApply.getApplyPerCode();
		this.applyPerName = resourcesApply.getApplyPerName();
		this.officeName = resourcesApply.getOffice().getName();
		if(resourcesApply.getApplyTime() != null){
			this.applyTime = DateUtils.formatDate(resourcesApply.getApplyTime(), DateUtils.YYYY_MM_DD);
		}
		if(resourcesApply.getExpectDate() != null){
			this.expectDate = resourcesApply.getExpectDate().getTime();
		}
		this.amountSum = resourcesApply.getAmountSum();
		this.demandPersonelNum = resourcesApply.getDemandPersonelNum();
		this.timeLong = resourcesApply.getTimeLong();
		this.resourcesType = resourcesApply.getResourcesType();
		this.resourcesTypeValue = DictUtils.getDictLabel(resourcesApply.getResourcesType(), "resources_type", "");
		this.projectId = resourcesApply.getProjectId();
		this.projectName = resourcesApply.getProjectName();
		this.projectPersonel = "";
		this.resourcesStatus = resourcesApply.getResourcesStatus();
		this.resourcesStatusValue =DictUtils.getDictLabel(resourcesApply.getResourcesStatus(), "expense_status", "");
		this.remarks = resourcesApply.getRemarks();
	}
	
	public ResourcesApplyFlowResponse(String id){
		super(id);
	}

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
	
	public String getApplyPerCode() {
		return applyPerCode;
	}

	public void setApplyPerCode(String applyPerCode) {
		this.applyPerCode = applyPerCode;
	}
	
	public String getApplyPerName() {
		return applyPerName;
	}

	public void setApplyPerName(String applyPerName) {
		this.applyPerName = applyPerName;
	}
	
	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
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
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getProjectPersonel() {
		return projectPersonel;
	}

	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}

	public int getIsDeit() {
		return isDeit;
	}

	public void setIsDeit(int isDeit) {
		this.isDeit = isDeit;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public BigDecimal getAmountSum() {
		return amountSum;
	}

	public void setAmountSum(BigDecimal amountSum) {
		this.amountSum = amountSum;
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

	public String getResourcesStatus() {
		return resourcesStatus;
	}

	public void setResourcesStatus(String resourcesStatus) {
		this.resourcesStatus = resourcesStatus;
	}

	public String getResourcesStatusValue() {
		return resourcesStatusValue;
	}

	public void setResourcesStatusValue(String resourcesStatusValue) {
		this.resourcesStatusValue = resourcesStatusValue;
	}

	public String getResourcesTypeValue() {
		return resourcesTypeValue;
	}

	public void setResourcesTypeValue(String resourcesTypeValue) {
		this.resourcesTypeValue = resourcesTypeValue;
	}

	public List<ResourcesAssignResponse> getAssignList() {
		return assignList;
	}

	public void setAssignList(List<ResourcesAssignResponse> assignList) {
		this.assignList = assignList;
	}
	
}