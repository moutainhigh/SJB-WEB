/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.recp;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.sijibao.oa.modules.intfz.request.demand.DemandManagementBudgetRequest;
import com.sijibao.oa.modules.intfz.validator.IntfzValid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接待申请Entity
 * @author xuby
 * @version 2018-04-17
 */
@ApiModel(value="接待申请对象")
public class RecpFlowRequest{
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="主键ID")
	private String id;              //主键ID
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程实例ID")
	private String procInsId;		// 流程实例ID
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="流程编码")
	private String procCode;		// 流程编号
	
	@IntfzValid(min=0, max = 200, nullable=false)
	@ApiModelProperty(value="接待主题")
	private String recpTheme;		// 接待主题
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="项目ID")
	private String projectId;		// 项目编号
	
	@IntfzValid(min=0, max = 32, nullable=false)
	@ApiModelProperty(value="接待人数")
	private Integer recpNum;		// 接待人数
	
	@IntfzValid(min=0, max = 64, nullable=false)
	@ApiModelProperty(value="预计接待时间")
	private long recpTime;		// 预计接待时间：yyyy-MM-dd HH:mm
	
	@IntfzValid(min=0, max = 32, nullable=true)
	@ApiModelProperty(value="预算费用总额")
	private BigDecimal budgetTotal;		// 预算费用总额
	
	@ApiModelProperty(value="预算明细数据")
	private List<DemandManagementBudgetRequest> demandBudgetList;
	
	@IntfzValid(min=0, max = 300, nullable=false)
	@ApiModelProperty(value="陪客人员")
	private String[] employees; //陪客人员

    @IntfzValid(max = 4000)
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
	
	public String getRecpTheme() {
		return recpTheme;
	}

	public void setRecpTheme(String recpTheme) {
		this.recpTheme = recpTheme;
	}
	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public Integer getRecpNum() {
		return recpNum;
	}

	public void setRecpNum(Integer recpNum) {
		this.recpNum = recpNum;
	}
	
	public long getRecpTime() {
		return recpTime;
	}

	public void setRecpTime(long recpTime) {
		this.recpTime = recpTime;
	}
	
	public BigDecimal getBudgetTotal() {
		return budgetTotal;
	}

	public void setBudgetTotal(BigDecimal budgetTotal) {
		this.budgetTotal = budgetTotal;
	}
	
	public List<DemandManagementBudgetRequest> getDemandBudgetList() {
		return demandBudgetList;
	}

	public void setDemandBudgetList(List<DemandManagementBudgetRequest> demandBudgetList) {
		this.demandBudgetList = demandBudgetList;
	}

	public String[] getEmployees() {
		return employees;
	}

	public void setEmployees(String[] employees) {
		this.employees = employees;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "RecpFlowRequest [id=" + id + ", procInsId=" + procInsId + ", procCode=" + procCode + ", recpTheme="
				+ recpTheme + ", projectId=" + projectId + ", recpNum=" + recpNum + ", recpTime=" + recpTime
				+ ", budgetTotal=" + budgetTotal + ", demandBudgetList=" + demandBudgetList + ", employees="
				+ Arrays.toString(employees) + ", remarks=" + remarks + "]";
	}
	
}