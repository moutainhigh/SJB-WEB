/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.daily;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报-发送给上级Entity
 * @author wanxb
 * @version 2018-12-12
 */
public class BIReq {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "搜索开始时间")
	private long startTime = 0;//搜索开始时间
	@ApiModelProperty(value = "搜索结束时间")
	private long endTime = 0;//搜索结束时间
	@ApiModelProperty(value = "页数")
	private int pageNo = 0;
	@ApiModelProperty(value = "每页显示条数")
	private int pageSize = 0;

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

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public String toString() {
		return "BIReq{" +
				"startTime=" + startTime +
				", endTime=" + endTime +
				", pageNo=" + pageNo +
				", pageSize=" + pageSize +
				'}';
	}
}