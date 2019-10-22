package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户维护管理--列表返回对象
 * @author wanxb
 *
 */
@ApiModel(value="客户维护管理--列表返回对象")
public class CustMaintenanceDetailResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="维护时间:yyyy-MM-dd HH:mm:ss")
	private long custMaintenanceDate = 0l;		// 维护时间:yyyy-MM-dd HH:mm:ss
	@ApiModelProperty(value="维护类型:1.一般客户，2高意向客户，3已签约，4已上线，5全上线")
	private String custMaintenanceType;		// 维护类型:一般客户，高意向客户，已签约，已上线，全上线cust_maintenance_type
	@ApiModelProperty(value="维护内容")
	private String custMaintenanceContent;		// 维护内容
	@ApiModelProperty(value="主要问题")
	private String custMaintenanceProblem;		// 主要问题
	@ApiModelProperty(value="问题责任人")
	private String custPersonLiable;		// 问题责任人
	@ApiModelProperty(value="备注")
	private String remarks;  //备注
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public long getCustMaintenanceDate() {
		return custMaintenanceDate;
	}
	public void setCustMaintenanceDate(long custMaintenanceDate) {
		this.custMaintenanceDate = custMaintenanceDate;
	}
	public String getCustMaintenanceType() {
		return custMaintenanceType;
	}
	public void setCustMaintenanceType(String custMaintenanceType) {
		this.custMaintenanceType = custMaintenanceType;
	}
	public String getCustMaintenanceContent() {
		return custMaintenanceContent;
	}
	public void setCustMaintenanceContent(String custMaintenanceContent) {
		this.custMaintenanceContent = custMaintenanceContent;
	}
	public String getCustMaintenanceProblem() {
		return custMaintenanceProblem;
	}
	public void setCustMaintenanceProblem(String custMaintenanceProblem) {
		this.custMaintenanceProblem = custMaintenanceProblem;
	}
	public String getCustPersonLiable() {
		return custPersonLiable;
	}
	public void setCustPersonLiable(String custPersonLiable) {
		this.custPersonLiable = custPersonLiable;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Override
	public String toString() {
		return "CustMaintenanceResponse [custName=" + custName + ", custMaintenanceDate=" + custMaintenanceDate
				+ ", custMaintenanceType=" + custMaintenanceType + ", custMaintenanceContent=" + custMaintenanceContent
				+ ", custMaintenanceProblem=" + custMaintenanceProblem + ", custPersonLiable=" + custPersonLiable
				+ ", remarks=" + remarks + "]";
	}
	
	
}
