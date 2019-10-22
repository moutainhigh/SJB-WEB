/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.consumables;

import java.math.BigDecimal;
import java.util.Date;

import com.sijibao.base.common.provider.entity.PagerBase;

/**
 * 消耗品入库明细表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesPutinDetailSaveReq extends PagerBase<MainConsumablesPutinDetailSaveReq> {
	
	private static final long serialVersionUID = 1L;
	private String consumablesId; //货物id
	private Date buyTime;		// 购买时间

	private double inCount;		// 入库数量
	private BigDecimal inPrice;		// 单价
	private BigDecimal inTotal;		// 总金额

	@Override
	public String toString() {
		return "MainConsumablesPutinDetailSaveReq{" +
				"consumablesId='" + consumablesId + '\'' +
				", buyTime=" + buyTime +
				", inCount=" + inCount +
				", inPrice=" + inPrice +
				", inTotal=" + inTotal +
				'}';
	}

	public String getConsumablesId() {
		return consumablesId;
	}

	public void setConsumablesId(String consumablesId) {
		this.consumablesId = consumablesId;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public double getInCount() {
		return inCount;
	}

	public void setInCount(double inCount) {
		this.inCount = inCount;
	}

	public BigDecimal getInPrice() {
		return inPrice;
	}

	public void setInPrice(BigDecimal inPrice) {
		this.inPrice = inPrice;
	}

	public BigDecimal getInTotal() {
		return inTotal;
	}

	public void setInTotal(BigDecimal inTotal) {
		this.inTotal = inTotal;
	}
}