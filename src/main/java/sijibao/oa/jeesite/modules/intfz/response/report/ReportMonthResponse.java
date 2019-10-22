package sijibao.oa.jeesite.modules.intfz.response.report;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 报表月份数据返回对象
 * @author xby
 *
 */
@ApiModel(value="报表月份返回对象")
public class ReportMonthResponse {
	@ApiModelProperty(value="1月份")
	private BigDecimal m1 = BigDecimal.ZERO;
	@ApiModelProperty(value="2月份")
	private BigDecimal m2 = BigDecimal.ZERO;
	@ApiModelProperty(value="3月份")
	private BigDecimal m3 = BigDecimal.ZERO;
	@ApiModelProperty(value="4月份")
	private BigDecimal m4 = BigDecimal.ZERO;
	@ApiModelProperty(value="5月份")
	private BigDecimal m5 = BigDecimal.ZERO;
	@ApiModelProperty(value="6月份")
	private BigDecimal m6 = BigDecimal.ZERO;
	@ApiModelProperty(value="7月份")
	private BigDecimal m7 = BigDecimal.ZERO;
	@ApiModelProperty(value="8月份")
	private BigDecimal m8 = BigDecimal.ZERO;
	@ApiModelProperty(value="9月份")
	private BigDecimal m9 = BigDecimal.ZERO;
	@ApiModelProperty(value="10月份")
	private BigDecimal m10 = BigDecimal.ZERO;
	@ApiModelProperty(value="11月份")
	private BigDecimal m11 = BigDecimal.ZERO;
	@ApiModelProperty(value="12月份")
	private BigDecimal m12 = BigDecimal.ZERO;
	@ApiModelProperty(value="行汇总")
	private BigDecimal rowTotal = BigDecimal.ZERO; 
	@ApiModelProperty(value="是否有明细0：没有，1：有")
	private String isDetail;
	
	public BigDecimal getM1() {
		return m1;
	}
	public void setM1(BigDecimal m1) {
		this.m1 = m1;
	}
	public BigDecimal getM2() {
		return m2;
	}
	public void setM2(BigDecimal m2) {
		this.m2 = m2;
	}
	public BigDecimal getM3() {
		return m3;
	}
	public void setM3(BigDecimal m3) {
		this.m3 = m3;
	}
	public BigDecimal getM4() {
		return m4;
	}
	public void setM4(BigDecimal m4) {
		this.m4 = m4;
	}
	public BigDecimal getM5() {
		return m5;
	}
	public void setM5(BigDecimal m5) {
		this.m5 = m5;
	}
	public BigDecimal getM6() {
		return m6;
	}
	public void setM6(BigDecimal m6) {
		this.m6 = m6;
	}
	public BigDecimal getM7() {
		return m7;
	}
	public void setM7(BigDecimal m7) {
		this.m7 = m7;
	}
	public BigDecimal getM8() {
		return m8;
	}
	public void setM8(BigDecimal m8) {
		this.m8 = m8;
	}
	public BigDecimal getM9() {
		return m9;
	}
	public void setM9(BigDecimal m9) {
		this.m9 = m9;
	}
	public BigDecimal getM10() {
		return m10;
	}
	public void setM10(BigDecimal m10) {
		this.m10 = m10;
	}
	public BigDecimal getM11() {
		return m11;
	}
	public void setM11(BigDecimal m11) {
		this.m11 = m11;
	}
	public BigDecimal getM12() {
		return m12;
	}
	public void setM12(BigDecimal m12) {
		this.m12 = m12;
	}
	public BigDecimal getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(BigDecimal rowTotal) {
		this.rowTotal = rowTotal;
	}
	public String getIsDetail() {
		return isDetail;
	}
	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}
	@Override
	public String toString() {
		return "ReportMonthResponse [m1=" + m1 + ", m2=" + m2 + ", m3=" + m3 + ", m4=" + m4 + ", m5=" + m5 + ", m6="
				+ m6 + ", m7=" + m7 + ", m8=" + m8 + ", m9=" + m9 + ", m10=" + m10 + ", m11=" + m11 + ", m12=" + m12
				+ ", rowTotal=" + rowTotal + ", isDetail=" + isDetail + "]";
	}
	
}
