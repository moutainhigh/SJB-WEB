package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户维护管理--保存返回对象
 * @author wanxb
 *
 */
@ApiModel(value="客户维护管理--保存返回对象")
public class CustMaintenanceResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="维护时间:yyyy-MM-dd HH:mm:ss")
	private long custMaintenanceDate = 0l;		// 维护时间:yyyy-MM-dd HH:mm:ss
	@ApiModelProperty(value="维护人")
	private String custMaintenanceMan;		// 维护人
	@ApiModelProperty(value="维护内容")
	private String custMaintenanceContent;		// 维护内容
	@ApiModelProperty(value="联系人id")
	private String linkmanId;//联系人id
	@ApiModelProperty(value="联系人name")
	private String linkmanName;//联系人name
	@ApiModelProperty(value="拜访类型:1上门拜访，2电话拜访")
	private String visitType;//拜访类型
	@ApiModelProperty(value=" 拜访类型name:1上门拜访，2电话拜访")
	private String visitTypeName;		// 拜访类型name
	@ApiModelProperty(value="客户类型:一般客户，高意向客户，已签约，已上线，全上线")
	private String custStage;		// 客户类型:一般客户，高意向客户，已签约，已上线，全上线
	@ApiModelProperty(value="客户类型name:一般客户，高意向客户，已签约，已上线，全上线")
	private String custStageName;		// 客户类型name:一般客户，高意向客户，已签约，已上线，全上线
	@ApiModelProperty(value="问题归类")
	private String issuesClassification;
	@ApiModelProperty(value="维护人部门")
	private String maintainerDeptName;
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
	public String getVisitTypeName() {
		return visitTypeName;
	}
	public void setVisitTypeName(String visitTypeName) {
		this.visitTypeName = visitTypeName;
	}
	public String getCustStageName() {
		return custStageName;
	}
	public void setCustStageName(String custStageName) {
		this.custStageName = custStageName;
	}

	public String getIssuesClassification() {
		return issuesClassification;
	}

	public void setIssuesClassification(String issuesClassification) {
		this.issuesClassification = issuesClassification;
	}

	public String getMaintainerDeptName() {
		return maintainerDeptName;
	}

	public void setMaintainerDeptName(String maintainerDeptName) {
		this.maintainerDeptName = maintainerDeptName;
	}

	public String getCustMaintenanceMan() {
		return custMaintenanceMan;
	}
	public void setCustMaintenanceMan(String custMaintenanceMan) {
		this.custMaintenanceMan = custMaintenanceMan;
	}

	@Override
	public String toString() {
		return "CustMaintenanceResponse{" +
				"custName='" + custName + '\'' +
				", custMaintenanceDate=" + custMaintenanceDate +
				", custMaintenanceMan='" + custMaintenanceMan + '\'' +
				", custMaintenanceContent='" + custMaintenanceContent + '\'' +
				", linkmanId='" + linkmanId + '\'' +
				", linkmanName='" + linkmanName + '\'' +
				", visitType='" + visitType + '\'' +
				", visitTypeName='" + visitTypeName + '\'' +
				", custStage='" + custStage + '\'' +
				", custStageName='" + custStageName + '\'' +
				", issuesClassification='" + issuesClassification + '\'' +
				", maintainerDeptName='" + maintainerDeptName + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}
