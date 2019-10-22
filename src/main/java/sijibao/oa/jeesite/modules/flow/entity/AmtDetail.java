package sijibao.oa.jeesite.modules.flow.entity;

import java.math.BigDecimal;

import com.sijibao.oa.common.persistence.DataEntity;


/**
 * 报销费用明细Entity
 */
public class AmtDetail  extends DataEntity<AmtDetail> {

	private static final long serialVersionUID = 1L;
	private String firstSub;		// 科目编号
	private String firstSubName;		// 科目名称
	private String secondSub;		// 科目编号
	private String secondSubName;		// 科目名称
	private BigDecimal amt;//金额
	public String getFirstSub() {
		return firstSub;
	}
	public void setFirstSub(String firstSub) {
		this.firstSub = firstSub;
	}
	public String getFirstSubName() {
		return firstSubName;
	}
	public void setFirstSubName(String firstSubName) {
		this.firstSubName = firstSubName;
	}
	public String getSecondSub() {
		return secondSub;
	}
	public void setSecondSub(String secondSub) {
		this.secondSub = secondSub;
	}
	public String getSecondSubName() {
		return secondSubName;
	}
	public void setSecondSubName(String secondSubName) {
		this.secondSubName = secondSubName;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	@Override
	public String toString() {
		return "AmtDetail [firstSub=" + firstSub + ", firstSubName=" + firstSubName + ", secondSub=" + secondSub
				+ ", secondSubName=" + secondSubName + ", amt=" + amt + "]";
	}
	
}