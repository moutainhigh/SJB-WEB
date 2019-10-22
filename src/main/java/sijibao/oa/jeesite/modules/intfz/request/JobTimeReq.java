/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;

/**
 * 定时任务手动调用接口
 * 2019-02-21 11:27:37
 * wanxb
 */
@ApiModel(value="手动调用接口对象")
public class JobTimeReq {
	private Date yesterday ;
	private Date today ;

	public Date getYesterday() {
		return yesterday;
	}

	public void setYesterday(Date yesterday) {
		this.yesterday = yesterday;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	@Override
	public String toString() {
		return "JobTimeReq{" +
				"yesterday=" + yesterday +
				", today=" + today +
				'}';
	}
}