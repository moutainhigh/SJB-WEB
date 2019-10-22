/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.consumables;

import java.math.BigDecimal;
import java.util.List;

import com.sijibao.base.common.provider.entity.PagerBase;

/**
 * 消耗品出库表Entity
 * @author wanxb
 * @version 2019-03-08
 */

public class MainConsumablesPutoutSaveReq  extends PagerBase<MainConsumablesPutoutSaveReq> {
	
	private static final long serialVersionUID = 1L;
	private String outCode;		// 出库编号
	private String officeId;		// 领用部门id
	private String officeName;		// 领用部门name
	private String outUserId;		// 领用人id
	private String outUserName;		// 领用人name
	private long outTime;		// 领用时间
	private BigDecimal putoutTotal;		// 出库总金额
	private String createBy;		// 创建时间
	private String updateBy;		// 更新时间
	private String consumablesId;	//物品id

	private List<MainConsumablesPutoutDetailSaveReq> detail ; //	出库明细

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOutCode() {
		return outCode;
	}

	public void setOutCode(String outCode) {
		this.outCode = outCode;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getOutUserId() {
		return outUserId;
	}

	public void setOutUserId(String outUserId) {
		this.outUserId = outUserId;
	}

	public String getOutUserName() {
		return outUserName;
	}

	public void setOutUserName(String outUserName) {
		this.outUserName = outUserName;
	}

	public long getOutTime() {
		return outTime;
	}

	public void setOutTime(long outTime) {
		this.outTime = outTime;
	}

	public BigDecimal getPutoutTotal() {
		return putoutTotal;
	}

	public void setPutoutTotal(BigDecimal putoutTotal) {
		this.putoutTotal = putoutTotal;
	}

	public String getConsumablesId() {
		return consumablesId;
	}

	public void setConsumablesId(String consumablesId) {
		this.consumablesId = consumablesId;
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

	public List<MainConsumablesPutoutDetailSaveReq> getDetail() {
		return detail;
	}

	public void setDetail(List<MainConsumablesPutoutDetailSaveReq> detail) {
		this.detail = detail;
	}



}