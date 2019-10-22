/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.consumables;

import java.util.List;

import com.sijibao.base.common.provider.entity.PagerBase;

/**
 * 消耗品核销库存Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesOffSaveReq extends PagerBase<MainConsumablesOffSaveReq> {
	
	private static final long serialVersionUID = 1L;
	private String offCode;		// 清理编号
	private long offTime;		// 清理时间
	private String offWhy;		// 清理理由
	private String offCause;		// 清理原因
	private List<MainConsumablesOffDetailSaveReq> detail ;			//核销明细

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOffCode() {
		return offCode;
	}

	public void setOffCode(String offCode) {
		this.offCode = offCode;
	}

	public long getOffTime() {
		return offTime;
	}

	public void setOffTime(long offTime) {
		this.offTime = offTime;
	}

	public String getOffWhy() {
		return offWhy;
	}

	public void setOffWhy(String offWhy) {
		this.offWhy = offWhy;
	}

	public String getOffCause() {
		return offCause;
	}

	public void setOffCause(String offCause) {
		this.offCause = offCause;
	}

	public List<MainConsumablesOffDetailSaveReq> getDetail() {
		return detail;
	}

	public void setDetail(List<MainConsumablesOffDetailSaveReq> detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "MainConsumablesOffSaveReq{" +
				"offCode='" + offCode + '\'' +
				", offTime=" + offTime +
				", offWhy='" + offWhy + '\'' +
				", offCause='" + offCause + '\'' +
				", detail=" + detail +
				'}';
	}
}