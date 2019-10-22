package sijibao.oa.jeesite.modules.intfz.response.expense;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报销费用明细Entity
 */
@ApiModel(value="报销费用明细信息")
public class AmtDetailResponse {
	@ApiModelProperty(value="一级科目编号")
	private String firstSub;		// 一级科目编号
	@ApiModelProperty(value="一级科目名称")
	private String firstSubName;		// 一级科目名称
	@ApiModelProperty(value="二级科目编号")
	private String secondSub;		// 二级科目编号
	@ApiModelProperty(value="二级科目名称")
	private String secondSubName;		// 二级科目名称
	@ApiModelProperty(value="金额")
	private BigDecimal amt;//金额
	@ApiModelProperty(value = "科目或者金额是否超标，0否，1是")
	private String isExceed;
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
		return "AmtDetailResponse [firstSub=" + firstSub + ", firstSubName=" + firstSubName + ", secondSub=" + secondSub
				+ ", secondSubName=" + secondSubName + ", amt=" + amt + ", isExceed=" + isExceed + "]";
	}

	public String getIsExceed() {
		return isExceed;
	}

	public void setIsExceed(String isExceed) {
		this.isExceed = isExceed;
	}
}