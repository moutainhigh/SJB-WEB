/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.consumables;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 消耗品与放置地关联表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesSaveReq {
	private String id ;				//物品id
	private String goodCode;		// 物品编号
	private String goodName;		// 物品名称
	private String[] goodType;		// 物品类别
	private String goodUnit;		// 物品单位
	private String goodSpec;		// 物品型号

	private long inTime;		// 入库时间
	private String putinPlace;		// 放置地
	private long buyTime;		// 购买时间
	private double inCount;		// 入库数量
	private BigDecimal inPrice;		// 单价

	private String userId;			//当前登入人id
	private String remarks;
	private String producSide;//产品端

	public String getProducSide() {
		return producSide;
	}

	public void setProducSide(String producSide) {
		this.producSide = producSide;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodCode() {
		return goodCode;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String[] getGoodType() {
		return goodType;
	}

	public void setGoodType(String[] goodType) {
		this.goodType = goodType;
	}

	public String getGoodUnit() {
		return goodUnit;
	}

	public void setGoodUnit(String goodUnit) {
		this.goodUnit = goodUnit;
	}

	public String getGoodSpec() {
		return goodSpec;
	}

	public void setGoodSpec(String goodSpec) {
		this.goodSpec = goodSpec;
	}

	public long getInTime() {
		return inTime;
	}

	public void setInTime(long inTime) {
		this.inTime = inTime;
	}

	public String getPutinPlace() {
		return putinPlace;
	}

	public void setPutinPlace(String putinPlace) {
		this.putinPlace = putinPlace;
	}

	public long getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(long buyTime) {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "MainConsumablesSaveReq{" +
				"id='" + id + '\'' +
				", goodCode='" + goodCode + '\'' +
				", goodName='" + goodName + '\'' +
				", goodType=" + Arrays.toString(goodType) +
				", goodUnit='" + goodUnit + '\'' +
				", goodSpec='" + goodSpec + '\'' +
				", inTime=" + inTime +
				", putinPlace='" + putinPlace + '\'' +
				", buyTime=" + buyTime +
				", inCount=" + inCount +
				", inPrice=" + inPrice +
				", userId='" + userId + '\'' +
				", remarks='" + remarks + '\'' +
				'}';
	}
}