/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.consumables;

import java.math.BigDecimal;
import java.util.List;

/**
 * 消耗品与放置地关联表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesResp {
	
	private static final long serialVersionUID = 1L;
	private String id ;				//	列表id
	private String goodCode;		// 物品编号
	private String goodName;		// 物品名称
	private String goodType;		// 物品类别
	private String goodTypeName;	//类型类型名称
	private String goodUnit;		// 物品单位
	private String goodSpec;		// 物品型号
	private BigDecimal goodTotal;		// 总金额
	private double goodCount;		// 库存总数量
	private BigDecimal goodPrice;		//单价
	private List<MainConsumablesPlaceResp> places ; //放置地
	private String remarks;		//备注

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

	public String getGoodType() {
		return goodType;
	}

	public void setGoodType(String goodType) {
		this.goodType = goodType;
	}

	public String getGoodTypeName() {
		return goodTypeName;
	}

	public void setGoodTypeName(String goodTypeName) {
		this.goodTypeName = goodTypeName;
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

	public BigDecimal getGoodTotal() {
		return goodTotal;
	}

	public void setGoodTotal(BigDecimal goodTotal) {
		this.goodTotal = goodTotal;
	}

	public double getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(double goodCount) {
		this.goodCount = goodCount;
	}

	public BigDecimal getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(BigDecimal goodPrice) {
		this.goodPrice = goodPrice;
	}

	public List<MainConsumablesPlaceResp> getPlaces() {
		return places;
	}

	public void setPlaces(List<MainConsumablesPlaceResp> places) {
		this.places = places;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "MainConsumablesResp{" +
				"id='" + id + '\'' +
				", goodCode='" + goodCode + '\'' +
				", goodName='" + goodName + '\'' +
				", goodType='" + goodType + '\'' +
				", goodTypeName='" + goodTypeName + '\'' +
				", goodUnit='" + goodUnit + '\'' +
				", goodSpec='" + goodSpec + '\'' +
				", goodTotal=" + goodTotal +
				", goodCount=" + goodCount +
				", goodPrice=" + goodPrice +
				", places=" + places +
				", remarks='" + remarks + '\'' +
				'}';
	}
}