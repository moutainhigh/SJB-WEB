/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.daily;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModelProperty;


/**
 * 日报列表查询对象
 * @author wanxb
 * @version 2018-12-12
 */
public class MainDailyHandleReq extends PagerBase<MainDailyHandleReq> {
	
	private static final long serialVersionUID = 1L;
	 @ApiModelProperty(value = "查看：1我的日志，2看日志")
	private String queryType;//查看：1我的日志，2看日志
	 @ApiModelProperty(value = " 阅读状态：0已读，1未读")
	private String readStatus;		// 阅读状态：0已读，1未读
	 @ApiModelProperty(value = "提交人或部门模糊查询")
	private String fant;		// 提交人或部门模糊查询
	 @ApiModelProperty(value = "web端人员或部门查询")
	 private String deptOrUserId;//web端查询
	 @ApiModelProperty(value = "搜索开始时间")
	private long startTime;//搜索开始时间
	 @ApiModelProperty(value = "搜索结束时间")
	private long endTime;//搜索结束时间
	 @ApiModelProperty(value = "搜索日报模板")
	private String dailyTemplate ;
	 
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	public String getFant() {
		return fant;
	}
	public void setFant(String fant) {
		this.fant = fant;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getDailyTemplate() {
		return dailyTemplate;
	}
	public void setDailyTemplate(String dailyTemplate) {
		this.dailyTemplate = dailyTemplate;
	}
	public String getDeptOrUserId() {
		return deptOrUserId;
	}
	public void setDeptOrUserId(String deptOrUserId) {
		this.deptOrUserId = deptOrUserId;
	}
	
	@Override
	public String toString() {
		return "MainDailyHandleReq [queryType=" + queryType + ", readStatus=" + readStatus + ", fant=" + fant
				+ ", deptOrUserId=" + deptOrUserId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", dailyTemplate=" + dailyTemplate + "]";
	}
	
	
}