/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报维护内容Entity
 * @author wanxb
 * @version 2018-12-12
 */
public class MainDailyCustMaintenanceResponse extends PagerBase<MainDailyCustMaintenanceResponse> {
	
	private static final long serialVersionUID = 1L;
	 @ApiModelProperty(value = "市场日志表id")
	private String marketDailyId;		// 市场日志表id
	 @ApiModelProperty(value = "客户id")
	private String custId;		// 客户id
	 @ApiModelProperty(value = "客户name")
	private String custName;		// 客户name
	 @ApiModelProperty(value = "拜访类型")
	private String visitType;		// 拜访类型
	 @ApiModelProperty(value = "拜访类型name")
	private String visitTypeName;		// 拜访类型
	 @ApiModelProperty(value = "维护时间:yyyy-MM-dd HH:mm:ss")
	private long custMaintenanceDate;		// 维护时间:yyyy-MM-dd HH:mm:ss
	 @ApiModelProperty(value = "维护时间(app特定格式):yyyy-MM-dd HH:mm:ss")
		private String custMaintenanceDateTime;		// 维护时间:yyyy-MM-dd HH:mm:ss
	 @ApiModelProperty(value = "维护内容")
	private String custMaintenanceContent;		// 维护内容
	public String getMarketDailyId() {
		return marketDailyId;
	}
	public void setMarketDailyId(String marketDailyId) {
		this.marketDailyId = marketDailyId;
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
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	public long getCustMaintenanceDate() {
		return custMaintenanceDate;
	}
	public void setCustMaintenanceDate(long custMaintenanceDate) {
		this.custMaintenanceDate = custMaintenanceDate;
	}
	public String getVisitTypeName() {
		return visitTypeName;
	}
	public void setVisitTypeName(String visitTypeName) {
		this.visitTypeName = visitTypeName;
	}
	
	public String getCustMaintenanceContent() {
		return custMaintenanceContent;
	}
	public void setCustMaintenanceContent(String custMaintenanceContent) {
		this.custMaintenanceContent = custMaintenanceContent;
	}

	public String getCustMaintenanceDateTime() {
		return custMaintenanceDateTime;
	}
	public void setCustMaintenanceDateTime(String custMaintenanceDateTime) {
		this.custMaintenanceDateTime = custMaintenanceDateTime;
	}
	@Override
	public String toString() {
		return "MainDailyCustMaintenanceResponse [marketDailyId=" + marketDailyId + ", custId=" + custId + ", custName="
				+ custName + ", visitType=" + visitType + ", visitTypeName=" + visitTypeName + ", custMaintenanceDate="
				+ custMaintenanceDate + ", custMaintenanceDateTime=" + custMaintenanceDateTime
				+ ", custMaintenanceContent=" + custMaintenanceContent + "]";
	}
	
}