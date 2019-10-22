package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户资料管理请求对象
 * @author wanxb
 *
 */
@ApiModel(value="客户资料管理请求对象")
public class CustInfoHandleReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="客户名称")
	private String custName;//客户名称
	@ApiModelProperty(value="冲突name")
	private String conflictName;//冲突name
	@ApiModelProperty(value="市场负责人")
	private String marketLeaderId;//市场负责人
	@ApiModelProperty(value="归属行业")
	private String custTrades;//归属行业
	@ApiModelProperty(value="开始 搜索时间")
	private long beginTime = 0l;		// 开始 搜索时间
	@ApiModelProperty(value="结束 搜索时间")
	private long endTime = 0l;		// 结束 搜索时间
	@ApiModelProperty(value="我的ABC客户")
	private String isMyCustInfo;//我的ABC客户
	public String getConflictName() {
		return conflictName;
	}

	public void setConflictName(String conflictName) {
		this.conflictName = conflictName;
	}


	public String getCustTrades() {
		return custTrades;
	}

	public void setCustTrades(String custTrades) {
		this.custTrades = custTrades;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getIsMyCustInfo() {
		return isMyCustInfo;
	}

	public void setIsMyCustInfo(String isMyCustInfo) {
		this.isMyCustInfo = isMyCustInfo;
	}

	@Override
	public String toString() {
		return "CustInfoHandleReq [custName=" + custName + ", conflictName=" + conflictName + ", marketLeaderId="
				+ marketLeaderId + ", custTrades=" + custTrades + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ "]";
	}


}
