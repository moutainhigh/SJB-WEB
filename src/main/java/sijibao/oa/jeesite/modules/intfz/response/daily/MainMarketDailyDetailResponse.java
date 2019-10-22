/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报-市场日志Entity
 * @author wanxb
 * @version 2018-12-12
 */
public class MainMarketDailyDetailResponse{

	
	@ApiModelProperty(value = "日志编号")
	private String dailyCode;		// 日志编号
	@ApiModelProperty(value = "当日电话拜访数量")
	private String custPhotoCount;		// 当日电话拜访数量
	@ApiModelProperty(value = "当日上门拜访数量")
	private String custVisitCount;		// 当日上门拜访数量
	@ApiModelProperty(value = "当日签约数量")
	private String custSignCount;		// 当日签约数量
	@ApiModelProperty(value = "明日拜访客户数量")
	private String afterVisitCount;		// 明日拜访客户数量
	@ApiModelProperty(value = " 明日计划")
	private String afterDailyPlan;		// 明日计划
	@ApiModelProperty(value = "当日意向客户数量")
	private String custIntentionCount;		// 当日意向客户数量
	@ApiModelProperty(value = "日志模板：0实施模板，1市场模板")
	private String dailyTemplate;		// 日志模板：0实施模板，1市场模板
	@ApiModelProperty(value = "日志模板name：0实施模板，1市场模板")
	private String dailyTemplateName;		// 日志模板：0实施模板，1市场模板
	@ApiModelProperty(value = "提交时间")
	private long createTime = 0l;		// 提交时间
	@ApiModelProperty(value = "提交人")
	private String createByName;		// 提交人
	@ApiModelProperty(value = "部门名称")
	private String officeName;		// 部门名称
	@ApiModelProperty(value = "备注")
	private String remarks;
	
	@ApiModelProperty(value = "发送给上级")
	private List<MainSendToResponse> SendToList;//发送给上级

	@ApiModelProperty(value = "抄送")
	private List<String> copyToList;


	public String getDailyCode() {
		return dailyCode;
	}
	public void setDailyCode(String dailyCode) {
		this.dailyCode = dailyCode;
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
	public String getDailyTemplate() {
		return dailyTemplate;
	}
	public void setDailyTemplate(String dailyTemplate) {
		this.dailyTemplate = dailyTemplate;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public List<MainSendToResponse> getSendToList() {
		return SendToList;
	}
	public void setSendToList(List<MainSendToResponse> sendToList) {
		SendToList = sendToList;
	}
	public String getCustIntentionCount() {
		return custIntentionCount;
	}
	public void setCustIntentionCount(String custIntentionCount) {
		this.custIntentionCount = custIntentionCount;
	}
	public String getDailyTemplateName() {
		return dailyTemplateName;
	}
	public void setDailyTemplateName(String dailyTemplateName) {
		this.dailyTemplateName = dailyTemplateName;
	}
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
		return "MainMarketDailyDetailResponse{" +
				"dailyCode='" + dailyCode + '\'' +
				", custPhotoCount='" + custPhotoCount + '\'' +
				", custVisitCount='" + custVisitCount + '\'' +
				", custSignCount='" + custSignCount + '\'' +
				", afterVisitCount='" + afterVisitCount + '\'' +
				", afterDailyPlan='" + afterDailyPlan + '\'' +
				", custIntentionCount='" + custIntentionCount + '\'' +
				", dailyTemplate='" + dailyTemplate + '\'' +
				", dailyTemplateName='" + dailyTemplateName + '\'' +
				", createTime=" + createTime +
				", createByName='" + createByName + '\'' +
				", officeName='" + officeName + '\'' +
				", remarks='" + remarks + '\'' +
				", SendToList=" + SendToList +
				", copyToList=" + copyToList +
				'}';
	}
}