/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.consumables;

import java.math.BigDecimal;

import com.sijibao.base.common.provider.entity.PagerBase;

/**
 * 消耗品出库明细表Entity
 * @author wanxb
 * @version 2019-03-08
 */


public class MainConsumablesPutoutDetailSaveReq extends PagerBase<MainConsumablesPutoutDetailSaveReq>  {
	
	private static final long serialVersionUID = 1L;
	private String consumablesId;		// 物品id
	private String putoutId;		// 出库表id
	private double outCount;		// 出库数量
	private BigDecimal outPrice;		// 单价
	private BigDecimal outTotal;		// 总金额
	private String outPlace;		//放置地
	private String createBy;		// 创建ren
	private String updateBy;		// 更新人Id

	public String getConsumablesId() {
		return consumablesId;
	}

	public void setConsumablesId(String consumablesId) {
		this.consumablesId = consumablesId;
	}

	public String getPutoutId() {
		return putoutId;
	}

	public void setPutoutId(String putoutId) {
		this.putoutId = putoutId;
	}

	public double getOutCount() {
		return outCount;
	}

	public void setOutCount(double outCount) {
		this.outCount = outCount;
	}

	public BigDecimal getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(BigDecimal outPrice) {
		this.outPrice = outPrice;
	}

	public BigDecimal getOutTotal() {
		return outTotal;
	}

	public void setOutTotal(BigDecimal outTotal) {
		this.outTotal = outTotal;
	}

	public String getOutPlace() {
		return outPlace;
	}

	public void setOutPlace(String outPlace) {
		this.outPlace = outPlace;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Override
	public String toString() {
		return "MainConsumablesPutoutDetailSaveReq{" +
				"consumablesId='" + consumablesId + '\'' +
				", putoutId='" + putoutId + '\'' +
				", outCount=" + outCount +
				", outPrice=" + outPrice +
				", outTotal=" + outTotal +
				", outPlace='" + outPlace + '\'' +
				", createBy='" + createBy + '\'' +
				", updateBy='" + updateBy + '\'' +
				'}';
	}
}