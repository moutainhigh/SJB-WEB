/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 系统版本维护Entity
 * @author wanxb
 * @version 2018-06-06
 */
@ApiModel(value="系统版本维护--列表请求对象")
public class SysVersionHandleReq {
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="版本编号")
	private String versionNo;		// 版本编号
	@ApiModelProperty(value="开始时间")	
	private String start;//开始时间
	@ApiModelProperty(value="结束时间")
	private String end;//结束时间
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
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "SysVersionHandleReq [pageNo=" + pageNo + ", pageSize=" + pageSize + ", versionNo=" + versionNo
				+ ", start=" + start + ", end=" + end + "]";
	}


}