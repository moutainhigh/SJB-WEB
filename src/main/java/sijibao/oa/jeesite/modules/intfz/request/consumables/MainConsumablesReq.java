/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.consumables;

import java.util.Arrays;

import com.sijibao.base.common.provider.entity.PagerBase;

/**
 * 消耗品与放置地关联表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesReq extends PagerBase<MainConsumablesReq> {
	
	private String goodInfo;		// 物品编号/物品名称/备注
	private String[] goodType;		// 物品类别
	private String placeId;			//放置地
	private String withOutZero;		//0为除开库存为0的搜索

	private long inStartTime;   			//	开始入库时间
	private long inEndTime;					//	结束入库时间


	public String getGoodInfo() {
		return goodInfo;
	}

	public void setGoodInfo(String goodInfo) {
		this.goodInfo = goodInfo;
	}

	public String[] getGoodType() {
		return goodType;
	}

	public void setGoodType(String[] goodType) {
		this.goodType = goodType;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getWithOutZero() {
		return withOutZero;
	}

	public void setWithOutZero(String withOutZero) {
		this.withOutZero = withOutZero;
	}

	public long getInStartTime() {
		return inStartTime;
	}

	public void setInStartTime(long inStartTime) {
		this.inStartTime = inStartTime;
	}

	public long getInEndTime() {
		return inEndTime;
	}

	public void setInEndTime(long inEndTime) {
		this.inEndTime = inEndTime;
	}

	@Override
	public String toString() {
		return "MainConsumablesReq{" +
				"goodInfo='" + goodInfo + '\'' +
				", goodType=" + Arrays.toString(goodType) +
				", placeId='" + placeId + '\'' +
				", withOutZero='" + withOutZero + '\'' +
				", inStartTime=" + inStartTime +
				", inEndTime=" + inEndTime +
				'}';
	}
}