/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 客户维护Entity
 * @author wanxb
 * @version 2018-05-31
 */
public class CustMaintenance extends DataEntity<CustMaintenance> {
	
	private static final long serialVersionUID = 1L;
	private String custId;		// 客户id
	private String custName;		// 客户名称
	private Date custMaintenanceDate;		// 维护时间:yyyy-MM-dd HH:mm:ss
	private String custStage;		// 客户类型:一般客户，高意向客户，已签约，已上线，全上线
	private String custMaintenanceContent;		// 维护内容
	
	private String linkmanId;//联系人id
	private String linkmanName;//联系人name
	private String visitType;//拜访类型
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
	public Date getCustMaintenanceDate() {
		return custMaintenanceDate;
	}
	public void setCustMaintenanceDate(Date custMaintenanceDate) {
		this.custMaintenanceDate = custMaintenanceDate;
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
	public String getCustStage() {
		return custStage;
	}
	public void setCustStage(String custStage) {
		this.custStage = custStage;
	}
	@Override
	public String toString() {
		return "CustMaintenance [custId=" + custId + ", custName=" + custName + ", custMaintenanceDate="
				+ custMaintenanceDate + ", custStage=" + custStage + ", custMaintenanceContent="
				+ custMaintenanceContent + ", linkmanId=" + linkmanId + ", linkmanName=" + linkmanName + ", visitType="
				+ visitType + "]";
	}
	
	
}