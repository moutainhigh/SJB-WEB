/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.daily;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 日报-发送给上级Entity
 * @author wanxb
 * @version 2018-12-12
 */
@ApiModel(value="日报-发送给上级请求对象")
public class MainDailyReq extends PagerBase<MainDailyReq> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "0市场工作日志，1实施工作日志")
	private String dailyTemplate;		// 日志类型
	public String getDailyTemplate() {
		return dailyTemplate;
	}
	public void setDailyTemplate(String dailyTemplate) {
		this.dailyTemplate = dailyTemplate;
	}
	@Override
	public String toString() {
		return "MainDailyReq [dailyTemplate=" + dailyTemplate + "]";
	}
	
	
}