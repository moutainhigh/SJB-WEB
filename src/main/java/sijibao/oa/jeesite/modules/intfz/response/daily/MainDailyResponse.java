/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报列表查询对象
 * @author wanxb
 * @version 2018-12-12
 */
public class MainDailyResponse extends PagerBase<MainDailyResponse> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "关联id")
	private String dailyId;		// 关联id
	@ApiModelProperty(value = " 日志模板：0实施模板，1市场模板")
	private String dailyTemplate;		// 日志模板：0实施模板，1市场模板
	@ApiModelProperty(value = " 日志模板name：0实施模板，1市场模板")
	private String dailyTemplateName;		// 日志模板：0实施模板，1市场模板
	@ApiModelProperty(value = "阅读状态：0已读，1未读")
	private String readStatus;		// 阅读状态：0已读，1未读
	@ApiModelProperty(value = "阅读状态name：0已读，1未读")
	private String readStatusName;		// 阅读状态：0已读，1未读
	@ApiModelProperty(value = "提交人部门id")
	private String dailyOffice;		// 提交人部门id
	@ApiModelProperty(value = "提交人部门name")
	private String dailyOfficeName;		// 提交人部门name
	@ApiModelProperty(value = " 提交人id")
	private String createBy;		// 提交人id
	@ApiModelProperty(value = "提交人name")
	private String createByName;//提交人name
	
	public String getDailyId() {
		return dailyId;
	}
	public void setDailyId(String dailyId) {
		this.dailyId = dailyId;
	}
	public String getDailyTemplate() {
		return dailyTemplate;
	}
	public void setDailyTemplate(String dailyTemplate) {
		this.dailyTemplate = dailyTemplate;
	}
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	public String getDailyOffice() {
		return dailyOffice;
	}
	public void setDailyOffice(String dailyOffice) {
		this.dailyOffice = dailyOffice;
	}
	public String getDailyOfficeName() {
		return dailyOfficeName;
	}
	public void setDailyOfficeName(String dailyOfficeName) {
		this.dailyOfficeName = dailyOfficeName;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateByName() {
		return createByName;
	}
	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	public String getDailyTemplateName() {
		return dailyTemplateName;
	}
	public void setDailyTemplateName(String dailyTemplateName) {
		this.dailyTemplateName = dailyTemplateName;
	}
	public String getReadStatusName() {
		return readStatusName;
	}
	public void setReadStatusName(String readStatusName) {
		this.readStatusName = readStatusName;
	}
	
	@Override
	public String toString() {
		return "MainDailyResponse [dailyId=" + dailyId + ", dailyTemplate=" + dailyTemplate + ", dailyTemplateName="
				+ dailyTemplateName + ", readStatus=" + readStatus + ", readStatusName=" + readStatusName
				+ ", dailyOffice=" + dailyOffice + ", dailyOfficeName=" + dailyOfficeName + ", createBy=" + createBy
				+ ", createByName=" + createByName + "]";
	}
	
	
	
}