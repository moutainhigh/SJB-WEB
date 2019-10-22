package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 主客户维护保存对象
 * @author wanxb
 */
@ApiModel(value="主客户维护保存对象")
public class MasterCustMaintainSaveReq implements Serializable{

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="客户id")
	private String custId;		// 客户id
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="维护内容")
	private String custMaintenanceContent;		// 维护内容
	@ApiModelProperty(value="联系人id")
	private String linkmanId;//联系人id
	@ApiModelProperty(value="联系人name")
	private String linkmanName;//联系人name
	@ApiModelProperty(value="拜访类型")
	private String visitType;//拜访类型
	@ApiModelProperty(value="问题归类")
	private List<String> issuesClassification;
	@ApiModelProperty(value="归属客户id")
	private String belongCustId;
	@ApiModelProperty(value="备注")
	private String remarks;  //备注
	private String producSide;//产品端

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

	public String getCustMaintenanceContent() {
		return custMaintenanceContent;
	}

	public void setCustMaintenanceContent(String custMaintenanceContent) {
		this.custMaintenanceContent = custMaintenanceContent;
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

	public List<String> getIssuesClassification() {
		return issuesClassification;
	}

	public void setIssuesClassification(List<String> issuesClassification) {
		this.issuesClassification = issuesClassification;
	}

	public String getBelongCustId() {
		return belongCustId;
	}

	public void setBelongCustId(String belongCustId) {
		this.belongCustId = belongCustId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	@Override
	public String toString() {
		return "MasterCustMaintainSaveReq{" +
				"custId='" + custId + '\'' +
				", custName='" + custName + '\'' +
				", custMaintenanceContent='" + custMaintenanceContent + '\'' +
				", linkmanId='" + linkmanId + '\'' +
				", linkmanName='" + linkmanName + '\'' +
				", visitType='" + visitType + '\'' +
				", issuesClassification=" + issuesClassification +
				", belongCustId='" + belongCustId + '\'' +
				", remarks='" + remarks + '\'' +
				", producSide='" + producSide + '\'' +
				'}';
	}
}
