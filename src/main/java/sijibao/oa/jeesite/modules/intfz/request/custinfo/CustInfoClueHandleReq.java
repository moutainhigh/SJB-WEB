package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户线索信息请求对象
 * @author wanxb
 *
 */
@ApiModel(value="客户线索信息请求对象")
public class CustInfoClueHandleReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="页数")
	private int pageNo;//页数
	@ApiModelProperty(value="每页数量")
	private int pageSize;//每页数量
	@ApiModelProperty(value="客户名称")
	private String custName;		// 客户名称
	@ApiModelProperty(value="市场负责人name")
	private String marketLeaderName;		// 市场负责人id
	@ApiModelProperty(value="开始搜索时间（更新时间）")
	private long clueTimeStart = 0l;//开始搜索时间
	@ApiModelProperty(value="结束搜索时间（更新时间）")
	private long clueTimeEnd = 0l;//结束搜索时间
	private String marketLeaderId;		// 市场负责人id

	
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
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
	public long getClueTimeStart() {
		return clueTimeStart;
	}
	public void setClueTimeStart(long clueTimeStart) {
		this.clueTimeStart = clueTimeStart;
	}
	public long getClueTimeEnd() {
		return clueTimeEnd;
	}
	public void setClueTimeEnd(long clueTimeEnd) {
		this.clueTimeEnd = clueTimeEnd;

	}
	@Override
	public String toString() {
		return "CustInfoClueHandleReq [pageNo=" + pageNo + ", pageSize=" + pageSize + ", custName=" + custName
				+ ", marketLeaderName=" + marketLeaderName + ", clueTimeStart=" + clueTimeStart + ", clueTimeEnd="
				+ clueTimeEnd + ", marketLeaderId=" + marketLeaderId + "]";
	}
	
}
