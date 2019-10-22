/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

import java.util.List;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报-市场日志Entity
 * @author wanxb
 * @version 2018-12-12
 */

public class MainMarketDailyBIResp extends PagerBase<MainDailyResponse> {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "手机号")
	private String mobile;//手机号
	@ApiModelProperty(value = "负责人姓名")
	private String name;//负责人姓名
	@ApiModelProperty(value = "日报时间")
	private long dailyTime;//日报时间
	@ApiModelProperty(value = "客户维护信息")
	private List<MainMarketCustBIResp> market;//客户维护信息

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDailyTime() {
		return dailyTime;
	}

	public void setDailyTime(long dailyTime) {
		this.dailyTime = dailyTime;
	}

	public List<MainMarketCustBIResp> getMarket() {
		return market;
	}

	public void setMarket(List<MainMarketCustBIResp> market) {
		this.market = market;
	}

	@Override
	public String toString() {
		return "MainMarketDailyBIResp{" +
				"mobile='" + mobile + '\'' +
				", name='" + name + '\'' +
				", dailyTime=" + dailyTime +
				", market=" + market +
				'}';
	}
}