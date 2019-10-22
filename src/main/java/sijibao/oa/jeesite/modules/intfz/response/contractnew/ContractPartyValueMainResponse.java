package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同方字段值返回对象
 * @author xby
 * @version 2018-10-24
 */
@ApiModel(value="合同方字段值返回对象")
public class ContractPartyValueMainResponse implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="甲方名称key")
	private String firstMemberName_key; //甲方名称key
	
	@ApiModelProperty(value="甲方名称value")
	private String firstMemberName_value; //甲方名称value
	
	@ApiModelProperty(value="信用代码/身份证号")
	private String firstCreditCode; //信用代码/身份证号
	
	@ApiModelProperty(value="住所")
	private String firstAddress; //住所
	
	@ApiModelProperty(value="联系方式")
	private String firstLinkMethod; //联系方式
	
	@ApiModelProperty(value="法定代表人")
	private String firstLegalRepresentative; //法定代表人
	
	
	
	@ApiModelProperty(value="乙方名称key")
	private String secondMemberName_key; //甲方名称key
	
	@ApiModelProperty(value="乙方名称value")
	private String secondMemberName_value; //甲方名称value
	
	@ApiModelProperty(value="信用代码/身份证号")
	private String secondCreditCode; //信用代码/身份证号
	
	@ApiModelProperty(value="住所")
	private String secondAddress; //住所
	
	@ApiModelProperty(value="联系方式")
	private String secondLinkMethod; //联系方式
	
	@ApiModelProperty(value="法定代表人")
	private String secondLegalRepresentative; //法定代表人
	
	
	
	@ApiModelProperty(value="丙方名称key")
	private String thirdMemberName_key; //甲方名称key
	
	@ApiModelProperty(value="丙方名称value")
	private String thirdMemberName_value; //甲方名称value
	
	@ApiModelProperty(value="信用代码/身份证号")
	private String thirdCreditCode; //信用代码/身份证号
	
	@ApiModelProperty(value="住所")
	private String thirdAddress; //住所
	
	@ApiModelProperty(value="联系方式")
	private String thirdLinkMethod; //联系方式
	
	@ApiModelProperty(value="法定代表人")
	private String thirdLegalRepresentative; //法定代表人

	public String getFirstMemberName_key() {
		return firstMemberName_key;
	}

	public void setFirstMemberName_key(String firstMemberName_key) {
		this.firstMemberName_key = firstMemberName_key;
	}

	public String getFirstMemberName_value() {
		return firstMemberName_value;
	}

	public void setFirstMemberName_value(String firstMemberName_value) {
		this.firstMemberName_value = firstMemberName_value;
	}

	public String getFirstCreditCode() {
		return firstCreditCode;
	}

	public void setFirstCreditCode(String firstCreditCode) {
		this.firstCreditCode = firstCreditCode;
	}

	public String getFirstAddress() {
		return firstAddress;
	}

	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}

	public String getFirstLinkMethod() {
		return firstLinkMethod;
	}

	public void setFirstLinkMethod(String firstLinkMethod) {
		this.firstLinkMethod = firstLinkMethod;
	}

	public String getFirstLegalRepresentative() {
		return firstLegalRepresentative;
	}

	public void setFirstLegalRepresentative(String firstLegalRepresentative) {
		this.firstLegalRepresentative = firstLegalRepresentative;
	}

	public String getSecondMemberName_key() {
		return secondMemberName_key;
	}

	public void setSecondMemberName_key(String secondMemberName_key) {
		this.secondMemberName_key = secondMemberName_key;
	}

	public String getSecondMemberName_value() {
		return secondMemberName_value;
	}

	public void setSecondMemberName_value(String secondMemberName_value) {
		this.secondMemberName_value = secondMemberName_value;
	}

	public String getSecondCreditCode() {
		return secondCreditCode;
	}

	public void setSecondCreditCode(String secondCreditCode) {
		this.secondCreditCode = secondCreditCode;
	}

	public String getSecondAddress() {
		return secondAddress;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}

	public String getSecondLinkMethod() {
		return secondLinkMethod;
	}

	public void setSecondLinkMethod(String secondLinkMethod) {
		this.secondLinkMethod = secondLinkMethod;
	}

	public String getSecondLegalRepresentative() {
		return secondLegalRepresentative;
	}

	public void setSecondLegalRepresentative(String secondLegalRepresentative) {
		this.secondLegalRepresentative = secondLegalRepresentative;
	}

	public String getThirdMemberName_key() {
		return thirdMemberName_key;
	}

	public void setThirdMemberName_key(String thirdMemberName_key) {
		this.thirdMemberName_key = thirdMemberName_key;
	}

	public String getThirdMemberName_value() {
		return thirdMemberName_value;
	}

	public void setThirdMemberName_value(String thirdMemberName_value) {
		this.thirdMemberName_value = thirdMemberName_value;
	}

	public String getThirdCreditCode() {
		return thirdCreditCode;
	}

	public void setThirdCreditCode(String thirdCreditCode) {
		this.thirdCreditCode = thirdCreditCode;
	}

	public String getThirdAddress() {
		return thirdAddress;
	}

	public void setThirdAddress(String thirdAddress) {
		this.thirdAddress = thirdAddress;
	}

	public String getThirdLinkMethod() {
		return thirdLinkMethod;
	}

	public void setThirdLinkMethod(String thirdLinkMethod) {
		this.thirdLinkMethod = thirdLinkMethod;
	}

	public String getThirdLegalRepresentative() {
		return thirdLegalRepresentative;
	}

	public void setThirdLegalRepresentative(String thirdLegalRepresentative) {
		this.thirdLegalRepresentative = thirdLegalRepresentative;
	}

	@Override
	public String toString() {
		return "ContractPartyValueMainResponse [firstMemberName_key=" + firstMemberName_key + ", firstMemberName_value="
				+ firstMemberName_value + ", firstCreditCode=" + firstCreditCode + ", firstAddress=" + firstAddress
				+ ", firstLinkMethod=" + firstLinkMethod + ", firstLegalRepresentative=" + firstLegalRepresentative
				+ ", secondMemberName_key=" + secondMemberName_key + ", secondMemberName_value="
				+ secondMemberName_value + ", secondCreditCode=" + secondCreditCode + ", secondAddress=" + secondAddress
				+ ", secondLinkMethod=" + secondLinkMethod + ", secondLegalRepresentative=" + secondLegalRepresentative
				+ ", thirdMemberName_key=" + thirdMemberName_key + ", thirdMemberName_value=" + thirdMemberName_value
				+ ", thirdCreditCode=" + thirdCreditCode + ", thirdAddress=" + thirdAddress + ", thirdLinkMethod="
				+ thirdLinkMethod + ", thirdLegalRepresentative=" + thirdLegalRepresentative + "]";
	}
	
}
