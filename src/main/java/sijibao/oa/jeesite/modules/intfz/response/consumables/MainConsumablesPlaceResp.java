/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.consumables;

import java.math.BigDecimal;

/**
 * 消耗品与放置地关联表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesPlaceResp  {
	
	private static final long serialVersionUID = 1L;
	private String consumablesId;		// 消耗品id
	private String placeId;		// 放置地id
	private String placeName;		// 放置地name
	private double placeCount;		// 放置地总数量
	private BigDecimal placeTotal;		//放置地总金额

	public String getConsumablesId() {
		return consumablesId;
	}

	public void setConsumablesId(String consumablesId) {
		this.consumablesId = consumablesId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public double getPlaceCount() {
		return placeCount;
	}

	public void setPlaceCount(double placeCount) {
		this.placeCount = placeCount;
	}

	public BigDecimal getPlaceTotal() {
		return placeTotal;
	}

	public void setPlaceTotal(BigDecimal placeTotal) {
		this.placeTotal = placeTotal;
	}

	@Override
	public String toString() {
		return "MainConsumablesPlaceResp{" +
				"consumablesId='" + consumablesId + '\'' +
				", placeId='" + placeId + '\'' +
				", placeName='" + placeName + '\'' +
				", placeCount=" + placeCount +
				", placeTotal=" + placeTotal +
				'}';
	}
}