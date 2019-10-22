/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.consumables;

import java.math.BigDecimal;
import java.util.List;

import com.sijibao.base.common.provider.entity.PagerBase;

/**
 * 消耗品入库表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesPutinSaveReq extends PagerBase<MainConsumablesPutinSaveReq>  {
	
	private static final long serialVersionUID = 1L;
	private long inTime;		// 入库时间
	private String putinPlace;		// 放置地

	private BigDecimal putinTotal;		// 入库总金额

	private List<MainConsumablesPutinDetailSaveReq> detail; //入库物品详细

	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public BigDecimal getPutinTotal() {
		return putinTotal;
	}

	public void setPutinTotal(BigDecimal putinTotal) {
		this.putinTotal = putinTotal;
	}

	public List<MainConsumablesPutinDetailSaveReq> getDetail() {
		return detail;
	}

	public void setDetail(List<MainConsumablesPutinDetailSaveReq> detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "MainConsumablesPutinSaveReq{" +
				"inTime=" + inTime +
				", putinPlace='" + putinPlace + '\'' +
				", putinTotal='" + putinTotal + '\'' +
				", detail=" + detail +
				'}';
	}
}