/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

/**
 * 日报-市场日志Entity
 * @author wanxb
 * @version 2018-12-12
 */

public class MarketDailyExport {
	
	private String createByName;		// 提交人
	private String officeName;		// 部门名称
	private String dailyTemplateName;		// 日志模板Name：0实施模板，1市场模板
	private String createTime;		// 提交时间
	private String custPhotoCount;		// 当日电话拜访数量
	private String custVisitCount;		// 当日上门拜访数量
	private String custIntentionCount;		// 当日意向客户数量
	private String custSignCount;		// 当日签约数量
	private String afterVisitCount;		// 明日拜访客户数量
	private String afterDailyPlan;		// 明日计划
	private String remarks;//备注
	private String custName;		// 客户name
	private String visitTypeName;		// 拜访类型
	private String custMaintenanceDate;		// 维护时间:yyyy-MM-dd HH:mm:ss
	private String custMaintenanceContent;		// 维护内容
	private String sendTo;//发给谁
	private String copyTo;//抄送谁
	public String getCreateByName() {
		return createByName;
	}
	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getDailyTemplateName() {
		return dailyTemplateName;
	}
	public void setDailyTemplateName(String dailyTemplateName) {
		this.dailyTemplateName = dailyTemplateName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public String getCustIntentionCount() {
		return custIntentionCount;
	}
	public void setCustIntentionCount(String custIntentionCount) {
		this.custIntentionCount = custIntentionCount;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getVisitTypeName() {
		return visitTypeName;
	}
	public void setVisitTypeName(String visitTypeName) {
		this.visitTypeName = visitTypeName;
	}
	public String getCustMaintenanceDate() {
		return custMaintenanceDate;
	}
	public void setCustMaintenanceDate(String custMaintenanceDate) {
		this.custMaintenanceDate = custMaintenanceDate;
	}
	public String getCustMaintenanceContent() {
		return custMaintenanceContent;
	}
	public void setCustMaintenanceContent(String custMaintenanceContent) {
		this.custMaintenanceContent = custMaintenanceContent;
	}
	public String getSendTo() {
		return sendTo;
	}
	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getCopyTo() {
		return copyTo;
	}

	public void setCopyTo(String copyTo) {
		this.copyTo = copyTo;
	}

	@Override
	public String toString() {
		return "MarketDailyExport{" +
				"createByName='" + createByName + '\'' +
				", officeName='" + officeName + '\'' +
				", dailyTemplateName='" + dailyTemplateName + '\'' +
				", createTime='" + createTime + '\'' +
				", custPhotoCount='" + custPhotoCount + '\'' +
				", custVisitCount='" + custVisitCount + '\'' +
				", custIntentionCount='" + custIntentionCount + '\'' +
				", custSignCount='" + custSignCount + '\'' +
				", afterVisitCount='" + afterVisitCount + '\'' +
				", afterDailyPlan='" + afterDailyPlan + '\'' +
				", remarks='" + remarks + '\'' +
				", custName='" + custName + '\'' +
				", visitTypeName='" + visitTypeName + '\'' +
				", custMaintenanceDate='" + custMaintenanceDate + '\'' +
				", custMaintenanceContent='" + custMaintenanceContent + '\'' +
				", sendTo='" + sendTo + '\'' +
				", copyTo='" + copyTo + '\'' +
				'}';
	}
}