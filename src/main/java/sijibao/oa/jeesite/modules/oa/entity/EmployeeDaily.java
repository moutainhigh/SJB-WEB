/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 工作日志Entity
 * @author wanxb
 * @version 2018-05-31
 */
public class EmployeeDaily extends DataEntity<EmployeeDaily> {
	
	private static final long serialVersionUID = 1L;
	private String dailyCode;		// 日志编号
	private Date dailyDate;		// 日志记录时间：yyyy-MM-dd
	private User marketLeader;		// 市场负责人ID
	private Office office;		// 部门
	private String custVisitCount;		// 当日拜访数量
	private String custSignCount;		// 当日签约数量
	private String custIntentionCount;		// 当日意向客户数量
	private String afterVisitCount;		// 明日拜访客户数量
	private String dailyContent;		// 日志内容
	private String afterDailyPlan;		// 明日计划
	
	private Date beginTime;		// 开始 查询时间
	private Date endTime;		// 结束 查询时间
	
	private User user;//当前登入用户
	private String sql;//数据权限
	private String marketLeaderName;//查询用负责人
	

	public String getMarketLeaderName() {
		return marketLeaderName;
	}

	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public EmployeeDaily() {
		super();
	}

	public EmployeeDaily(String id){
		super(id);
	}

	@Length(min=1, max=32, message="日志编号长度必须介于 1 和 32 之间")
	public String getDailyCode() {
		return dailyCode;
	}

	public void setDailyCode(String dailyCode) {
		this.dailyCode = dailyCode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(Date dailyDate) {
		this.dailyDate = dailyDate;
	}
	
	public User getMarketLeader() {
		return marketLeader;
	}

	public void setMarketLeader(User marketLeader) {
		this.marketLeader = marketLeader;
	}

	@NotNull(message="部门id不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=1, max=20, message="当日拜访数量长度必须介于 1 和 20 之间")
	public String getCustVisitCount() {
		return custVisitCount;
	}

	public void setCustVisitCount(String custVisitCount) {
		this.custVisitCount = custVisitCount;
	}
	
	@Length(min=1, max=20, message="当日签约数量长度必须介于 1 和 20 之间")
	public String getCustSignCount() {
		return custSignCount;
	}

	public void setCustSignCount(String custSignCount) {
		this.custSignCount = custSignCount;
	}
	
	@Length(min=1, max=20, message="当日意向客户数量长度必须介于 1 和 20 之间")
	public String getCustIntentionCount() {
		return custIntentionCount;
	}

	public void setCustIntentionCount(String custIntentionCount) {
		this.custIntentionCount = custIntentionCount;
	}
	
	@Length(min=1, max=20, message="明日拜访客户数量长度必须介于 1 和 20 之间")
	public String getAfterVisitCount() {
		return afterVisitCount;
	}

	public void setAfterVisitCount(String afterVisitCount) {
		this.afterVisitCount = afterVisitCount;
	}
	
	@Length(min=1, max=1000, message="日志内容长度必须介于 1 和 1000 之间")
	public String getDailyContent() {
		return dailyContent;
	}

	public void setDailyContent(String dailyContent) {
		this.dailyContent = dailyContent;
	}
	
	@Length(min=1, max=1000, message="明日计划长度必须介于 1 和 1000 之间")
	public String getAfterDailyPlan() {
		return afterDailyPlan;
	}

	public void setAfterDailyPlan(String afterDailyPlan) {
		this.afterDailyPlan = afterDailyPlan;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
}