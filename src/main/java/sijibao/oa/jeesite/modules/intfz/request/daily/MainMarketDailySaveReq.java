/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.daily;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报-市场日志Entity
 * @author wanxb
 * @version 2018-12-12
 */
public class MainMarketDailySaveReq{

    @ApiModelProperty(value = "当日电话拜访数量")
	private String custPhotoCount;		// 当日电话拜访数量
    @ApiModelProperty(value = "当日上门拜访数量")
	private String custVisitCount;		// 当日上门拜访数量
    @ApiModelProperty(value = "当日签约数量")
	private String custSignCount;		// 当日签约数量
    @ApiModelProperty(value = "明日拜访客户数量")
	private String afterVisitCount;		// 明日拜访客户数量
    @ApiModelProperty(value = "明日计划")
	private String afterDailyPlan;		// 明日计划
    @ApiModelProperty(value = "当日意向客户数量")
	private String custIntentionCount;		// 当日意向客户数量
    @ApiModelProperty(value = "日志模板：0实施模板，1市场模板")
	private String dailyTemplate;		// 日志模板：0实施模板，1市场模板
    @ApiModelProperty(value = "备注")
	private String remarks;		// 备注
    @ApiModelProperty(value = "客户维护管理信息")
	private List<MainDailyCustMaintenanceReq> DailyCustMaintenanceList;//客户维护管理信息
    @ApiModelProperty(value = "发送给上级")
	private List<String> SendToList;//发送给上级

	@ApiModelProperty(value = "抄送")
	private List<String> copyToList;

	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public String getCustPhotoCount() {
		return custPhotoCount;
	}

	public void setCustPhotoCount(String custPhotoCount) {
		this.custPhotoCount = custPhotoCount;
	}

	public String getCustVisitCount() {
		return custVisitCount;
	}

	public void setCustVisitCount(String custVisitCount) {
		this.custVisitCount = custVisitCount;
	}

	public String getCustSignCount() {
		return custSignCount;
	}

	public void setCustSignCount(String custSignCount) {
		this.custSignCount = custSignCount;
	}

	public String getAfterVisitCount() {
		return afterVisitCount;
	}

	public void setAfterVisitCount(String afterVisitCount) {
		this.afterVisitCount = afterVisitCount;
	}

	public String getAfterDailyPlan() {
		return afterDailyPlan;
	}

	public void setAfterDailyPlan(String afterDailyPlan) {
		this.afterDailyPlan = afterDailyPlan;
	}


	public String getCustIntentionCount() {
		return custIntentionCount;
	}

	public void setCustIntentionCount(String custIntentionCount) {
		this.custIntentionCount = custIntentionCount;
	}

	public String getDailyTemplate() {
		return dailyTemplate;
	}

	public void setDailyTemplate(String dailyTemplate) {
		this.dailyTemplate = dailyTemplate;
	}

	public List<MainDailyCustMaintenanceReq> getDailyCustMaintenanceList() {
		return DailyCustMaintenanceList;
	}

	public void setDailyCustMaintenanceList(List<MainDailyCustMaintenanceReq> dailyCustMaintenanceList) {
		DailyCustMaintenanceList = dailyCustMaintenanceList;
	}


	public List<String> getSendToList() {
		return SendToList;
	}

	public void setSendToList(List<String> sendToList) {
		SendToList = sendToList;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<String> getCopyToList() {
		return copyToList;
	}

	public void setCopyToList(List<String> copyToList) {
		this.copyToList = copyToList;
	}

	@Override
	public String toString() {
		return "MainMarketDailySaveReq{" +
				"custPhotoCount='" + custPhotoCount + '\'' +
				", custVisitCount='" + custVisitCount + '\'' +
				", custSignCount='" + custSignCount + '\'' +
				", afterVisitCount='" + afterVisitCount + '\'' +
				", afterDailyPlan='" + afterDailyPlan + '\'' +
				", custIntentionCount='" + custIntentionCount + '\'' +
				", dailyTemplate='" + dailyTemplate + '\'' +
				", remarks='" + remarks + '\'' +
				", DailyCustMaintenanceList=" + DailyCustMaintenanceList +
				", SendToList=" + SendToList +
				", copyToList=" + copyToList +
				'}';
	}
}