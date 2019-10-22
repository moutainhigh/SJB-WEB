package sijibao.oa.jeesite.modules.intfz.request.employeedaily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 工作日志请求对象
 * @author wanxb
 *
 */
@ApiModel(value="工作日志请求对象")
public class EmployeeDailyHandleReq {
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="市场负责人name")
	private String marketLeaderName;		// 市场负责人name
	@ApiModelProperty(value="开始 查询时间")
	private long beginTime = 0l;		// 开始 查询时间
	@ApiModelProperty(value="结束 查询时间")
	private long endTime = 0l;		// 结束 查询时间
	private String marketLeaderId;		// 市场负责人name
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
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	@Override
	public String toString() {
		return "EmployeeDailyHandleReq [pageNo=" + pageNo + ", pageSize=" + pageSize + ", marketLeaderName="
				+ marketLeaderName + ", beginTime=" + beginTime + ", endTime=" + endTime + ", marketLeaderId="
				+ marketLeaderId + "]";
	}
	
}
