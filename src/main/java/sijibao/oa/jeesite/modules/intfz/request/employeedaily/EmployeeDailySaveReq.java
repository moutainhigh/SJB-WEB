package sijibao.oa.jeesite.modules.intfz.request.employeedaily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工作日志保存对象
 * @author wanxb
 */
@ApiModel(value="工作日志保存对象")
public class EmployeeDailySaveReq {
	
	@ApiModelProperty(value="主键ID")
	private String id; //主键ID
	@ApiModelProperty(value="日志记录时间：yyyy-MM-dd")
	private long dailyDate = 0l;		// 日志记录时间：yyyy-MM-dd
	@ApiModelProperty(value="市场负责人ID")
	private String marketLeaderId;		// 市场负责人ID
	@ApiModelProperty(value="部门id")
	private String officeId;		// 部门id
	@ApiModelProperty(value="备注")
	private String remarks;	// 备注
	@ApiModelProperty(value="当日拜访数量")
	private String custVisitCount;		// 当日拜访数量
	@ApiModelProperty(value="当日意向客户数量")
	private String custIntentionCount;		// 当日意向客户数量
	@ApiModelProperty(value="当日签约数量")
	private String custSignCount;		// 当日签约数量
	@ApiModelProperty(value="明日拜访客户数量")
	private String afterVisitCount;		// 明日拜访客户数量
	@ApiModelProperty(value="日志内容")
	private String dailyContent;		// 日志内容
	@ApiModelProperty(value="明日计划")
	private String afterDailyPlan;		// 明日计划
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getDailyDate() {
		return dailyDate;
	}
	public void setDailyDate(long dailyDate) {
		this.dailyDate = dailyDate;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	public String getOfficeId() {
		return officeId;
	}
	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
	public String getDailyContent() {
		return dailyContent;
	}
	public void setDailyContent(String dailyContent) {
		this.dailyContent = dailyContent;
	}
	public String getAfterDailyPlan() {
		return afterDailyPlan;
	}
	public void setAfterDailyPlan(String afterDailyPlan) {
		this.afterDailyPlan = afterDailyPlan;
	}
	@Override
	public String toString() {
		return "EmployeeDailySaveReq [id=" + id + ", dailyDate=" + dailyDate + ", marketLeaderId=" + marketLeaderId
				+ ", officeId=" + officeId + ", remarks=" + remarks + ", custVisitCount=" + custVisitCount
				+ ", custIntentionCount=" + custIntentionCount + ", custSignCount=" + custSignCount
				+ ", afterVisitCount=" + afterVisitCount + ", dailyContent=" + dailyContent + ", afterDailyPlan="
				+ afterDailyPlan + "]";
	}
	
}
