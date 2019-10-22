/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.travel;

import java.math.BigDecimal;
import java.util.List;

import com.sijibao.oa.modules.intfz.request.demand.DemandManagementBudgetRequest;
import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 出差申请Entity
 * @author xuby
 * @version 2018-05-23
 */
@ApiModel(value="出差申请对象")
public class TravelFlowRequest{
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="主键ID")
	private String id;              //主键ID
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程编码")
	private String procCode;		// 流程编号
	
	@IntfzValid(min=0, max = 100, nullable=true)
	@ApiModelProperty(value="关联主题")
	private String relationTheme;		//关联主题
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="项目编号")
	private String projectId;		// 项目编号
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="项目负责人")
	private String projectPersonel;  //项目负责人

	@ApiModelProperty(value="报销分类")
	private List<String> travelExpenseTypeList;// 出差报销分类，根据类型travel_expense_type搜索字典配置

	@ApiModelProperty(value="随行人员")
	private List<String> entourageList;// 随行人员ID

	@IntfzValid(min=0, max = 64, nullable=true)
	@ApiModelProperty(value="预算费用总额")
	private BigDecimal budgetTotal;		// 预算费用总额
	
	@ApiModelProperty(value="预算明细数据")
	private List<DemandManagementBudgetRequest> demandBudgetList;
	@ApiModelProperty(value="备注")
	protected String remarks;	// 备注
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}

	public void setBudgetTotal(BigDecimal budgetTotal) {
		this.budgetTotal = budgetTotal;
	}

	public List<String> getTravelExpenseTypeList() {
		return travelExpenseTypeList;
	}

	public void setTravelExpenseTypeList(List<String> travelExpenseTypeList) {
		this.travelExpenseTypeList = travelExpenseTypeList;
	}

	public List<String> getEntourageList() {
		return entourageList;
	}

	public void setEntourageList(List<String> entourageList) {
		this.entourageList = entourageList;
	}

	public List<DemandManagementBudgetRequest> getDemandBudgetList() {
		return demandBudgetList;
	}

	public void setDemandBudgetList(List<DemandManagementBudgetRequest> demandBudgetList) {
		this.demandBudgetList = demandBudgetList;
	}

	public String getProjectPersonel() {
		return projectPersonel;
	}

	public void setProjectPersonel(String projectPersonel) {
		this.projectPersonel = projectPersonel;
	}

	public String getRelationTheme() {
		return relationTheme;
	}

	public void setRelationTheme(String relationTheme) {
		this.relationTheme = relationTheme;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "TravelFlowRequest [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode
				+ ", relationTheme=" + relationTheme + ", projectId=" + projectId + ", projectPersonel="
				+ projectPersonel + ", budgetTotal=" + budgetTotal + ", demandBudgetList=" + demandBudgetList
				+ ", remarks=" + remarks + "]";
	}
	
}