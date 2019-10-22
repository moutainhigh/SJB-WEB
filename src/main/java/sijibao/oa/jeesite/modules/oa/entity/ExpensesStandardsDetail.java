package sijibao.oa.jeesite.modules.oa.entity;

import java.math.BigDecimal;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 费用控制标准明细表Entity
 * @author xuby
 * @version 2018-03-20
 */
public class ExpensesStandardsDetail extends DataEntity<ExpensesStandardsDetail> {
	private static final long serialVersionUID = 1L;
	private String standsMainId; //控制主表ID
	private String standsType; //控制类别
	private BigDecimal standsAmount; //控制金额
	private String amountUnit; //金额单位
	private String postCode; //岗位编码
	private String cityType; //城市类别
	private String transMode; //交通方式
	
	private String[] standsTypes; // 控制类别列表
	private String[] cityTypes; //城市类别列表
	private String[] transModes; //交通方式列表
	private String[] postCodes; //岗位列表
	public ExpensesStandardsDetail(){
		super();
	}
	
	public ExpensesStandardsDetail(String id){
		super(id);
	}

	public String getStandsMainId() {
		return standsMainId;
	}

	public void setStandsMainId(String standsMainId) {
		this.standsMainId = standsMainId;
	}

	public String getStandsType() {
		return standsType;
	}

	public void setStandsType(String standsType) {
		this.standsType = standsType;
	}

	public BigDecimal getStandsAmount() {
		return standsAmount;
	}

	public void setStandsAmount(BigDecimal standsAmount) {
		this.standsAmount = standsAmount;
	}

	public String getAmountUnit() {
		return amountUnit;
	}

	public void setAmountUnit(String amountUnit) {
		this.amountUnit = amountUnit;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCityType() {
		return cityType;
	}

	public void setCityType(String cityType) {
		this.cityType = cityType;
	}

	public String getTransMode() {
		return transMode;
	}

	public void setTransMode(String transMode) {
		this.transMode = transMode;
	}

	public String[] getStandsTypes() {
		return standsTypes;
	}

	public void setStandsTypes(String[] standsTypes) {
		this.standsTypes = standsTypes;
	}

	public String[] getCityTypes() {
		return cityTypes;
	}

	public void setCityTypes(String[] cityTypes) {
		this.cityTypes = cityTypes;
	}

	public String[] getTransModes() {
		return transModes;
	}

	public void setTransModes(String[] transModes) {
		this.transModes = transModes;
	}

	public String[] getPostCodes() {
		return postCodes;
	}

	public void setPostCodes(String[] postCodes) {
		this.postCodes = postCodes;
	}
}
