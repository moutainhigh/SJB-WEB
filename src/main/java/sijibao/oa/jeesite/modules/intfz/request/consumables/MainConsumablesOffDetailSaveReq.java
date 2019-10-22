/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.consumables;

import java.math.BigDecimal;

import com.sijibao.base.common.provider.entity.PagerBase;

/**
 * 消耗品核销库存明细表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesOffDetailSaveReq extends PagerBase<MainConsumablesOffDetailSaveReq> {
	
	private static final long serialVersionUID = 1L;
	private String consumablesId;		// 物品id
	private String offId;		// 核销库表id
	private String offPlace;		// 放置地id
	private double offCount;		// 核销数量
	private BigDecimal offPrice;		// 单价
	private BigDecimal offTotal;		// 总金额

	public String getConsumablesId() {
		return consumablesId;
	}

	public void setConsumablesId(String consumablesId) {
		this.consumablesId = consumablesId;
	}

	public String getOffId() {
		return offId;
	}

	public void setOffId(String offId) {
		this.offId = offId;
	}

	public String getOffPlace() {
		return offPlace;
	}

	public void setOffPlace(String offPlace) {
		this.offPlace = offPlace;
	}

	public double getOffCount() {
		return offCount;
	}

	public void setOffCount(double offCount) {
		this.offCount = offCount;
	}

	public BigDecimal getOffPrice() {
		return offPrice;
	}

	public void setOffPrice(BigDecimal offPrice) {
		this.offPrice = offPrice;
	}

	public BigDecimal getOffTotal() {
		return offTotal;
	}

	public void setOffTotal(BigDecimal offTotal) {
		this.offTotal = offTotal;
	}

	@Override
	public String toString() {
		return "MainConsumablesOffDetailSaveReq{" +
				"consumablesId='" + consumablesId + '\'' +
				", offId='" + offId + '\'' +
				", offPlace='" + offPlace + '\'' +
				", offCount=" + offCount +
				", offPrice=" + offPrice +
				", offTotal=" + offTotal +
				'}';
	}
}