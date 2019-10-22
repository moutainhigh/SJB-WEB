/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.consumables;

import java.util.Arrays;
import java.util.List;

/**
 * 消耗品与放置地关联表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesDetailResp {
	
	private String goodCode;		// 物品编号
	private String goodName;		// 物品名称
	private String[] goodType;		// 物品类别
	private String goodUnit;		// 物品单位
	private String goodSpec;		// 物品型号
	private String goodTotal;		// 总金额
	private String goodCount;		// 库存总数量
	private String remarks;

	private List<MainConsumablesPlaceResp> places ; //放置地



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

	public String getGoodTotal() {
		return goodTotal;
	}

	public void setGoodTotal(String goodTotal) {
		this.goodTotal = goodTotal;
	}

	public String getGoodCount() {
		return goodCount;
	}

	public void setGoodCount(String goodCount) {
		this.goodCount = goodCount;
	}

	public List<MainConsumablesPlaceResp> getPlaces() {
		return places;
	}

	public void setPlaces(List<MainConsumablesPlaceResp> places) {
		this.places = places;
	}

	public String[] getGoodType() {
		return goodType;
	}

	public void setGoodType(String[] goodType) {
		this.goodType = goodType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "MainConsumablesDetailResp{" +
				"goodCode='" + goodCode + '\'' +
				", goodName='" + goodName + '\'' +
				", goodType=" + Arrays.toString(goodType) +
				", goodUnit='" + goodUnit + '\'' +
				", goodSpec='" + goodSpec + '\'' +
				", goodTotal='" + goodTotal + '\'' +
				", goodCount='" + goodCount + '\'' +
				", remarks='" + remarks + '\'' +
				", places=" + places +
				'}';
	}
}