package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户维护保存对象
 * @author wanxb
 */
@ApiModel(value="客户维护保存对象")
public class CustMaintenanceSaveReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="客户id")
	private String custId;		// 客户id
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="维护时间:yyyy-MM-dd HH:mm:ss")
	private long custMaintenanceDate = 0l;		// 维护时间:yyyy-MM-dd HH:mm:ss
	@ApiModelProperty(value="客户类型:一般客户，高意向客户，已签约，已上线，全上线")
	private String custStage;		// 客户类型:一般客户，高意向客户，已签约，已上线，全上线
	@ApiModelProperty(value="维护内容")
	private String custMaintenanceContent;		// 维护内容
	@ApiModelProperty(value="联系人id")
	private String linkmanId;//联系人id
	@ApiModelProperty(value="联系人name")
	private String linkmanName;//联系人name
	@ApiModelProperty(value="拜访类型")
	private String visitType;//拜访类型
	@ApiModelProperty(value="备注")
	private String remarks;  //备注
	private boolean flag = true;//是否改变位置
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
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
	public String getCustMaintenanceContent() {
		return custMaintenanceContent;
	}
	public void setCustMaintenanceContent(String custMaintenanceContent) {
		this.custMaintenanceContent = custMaintenanceContent;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLinkmanId() {
		return linkmanId;
	}
	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
	}
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	
	public String getCustStage() {
		return custStage;
	}
	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	@Override
	public String toString() {
		return "CustMaintenanceSaveReq [custId=" + custId + ", custName=" + custName + ", custMaintenanceDate="
				+ custMaintenanceDate + ", custStage=" + custStage + ", custMaintenanceContent="
				+ custMaintenanceContent + ", linkmanId=" + linkmanId + ", linkmanName=" + linkmanName + ", visitType="
				+ visitType + ", remarks=" + remarks + ", flag=" + flag + "]";
	}
}
