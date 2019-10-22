package sijibao.oa.jeesite.modules.intfz.response.contractnew;

import java.io.Serializable;

/**
 * 合同审批详情合同方返回对象
 * @author xby
 * @version 2018-11-12
 */
public class ContractFlowDetailPartyMainResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String key;
	
	private String value;
	
	private String firstMemberName; //甲方名称
	
	private String firstCreditCode;		// 甲方统一社会信用代码
	
	private String firstAddress;		// 甲方住所
	
	private String firstLegalRepresentative;		// 甲方法定代表人
	
	private String firstLinkMethod;		// 甲方联系方式
	
	
	private String secondMemberName; //乙方名称
	
	private String secondCreditCode;		// 乙方统一社会信用代码
	
	private String secondAddress;		// 乙方住所
	
	private String secondLegalRepresentative;		// 乙方法定代表人
	
	private String secondLinkMethod;		// 乙方联系方式
	
	
	private String thirdMemberName; //丙方名称
	
	private String thirdCreditCode;		// 丙方统一社会信用代码
	
	private String thirdAddress;		// 丙方住所
	
	private String thirdLegalRepresentative;		//丙方法定代表人
	
	private String thirdLinkMethod;		// 丙方联系方式
	
	public ContractFlowDetailPartyMainResponse(){}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getFirstLegalRepresentative() {
		return firstLegalRepresentative;
	}

	public void setFirstLegalRepresentative(String firstLegalRepresentative) {
		this.firstLegalRepresentative = firstLegalRepresentative;
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

	public String getSecondLegalRepresentative() {
		return secondLegalRepresentative;
	}

	public void setSecondLegalRepresentative(String secondLegalRepresentative) {
		this.secondLegalRepresentative = secondLegalRepresentative;
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

	public String getThirdLegalRepresentative() {
		return thirdLegalRepresentative;
	}

	public void setThirdLegalRepresentative(String thirdLegalRepresentative) {
		this.thirdLegalRepresentative = thirdLegalRepresentative;
	}

	public String getFirstMemberName() {
		return firstMemberName;
	}

	public void setFirstMemberName(String firstMemberName) {
		this.firstMemberName = firstMemberName;
	}

	public String getSecondMemberName() {
		return secondMemberName;
	}

	public void setSecondMemberName(String secondMemberName) {
		this.secondMemberName = secondMemberName;
	}

	public String getThirdMemberName() {
		return thirdMemberName;
	}

	public void setThirdMemberName(String thirdMemberName) {
		this.thirdMemberName = thirdMemberName;
	}

	public String getFirstLinkMethod() {
		return firstLinkMethod;
	}

	public void setFirstLinkMethod(String firstLinkMethod) {
		this.firstLinkMethod = firstLinkMethod;
	}

	public String getSecondLinkMethod() {
		return secondLinkMethod;
	}

	public void setSecondLinkMethod(String secondLinkMethod) {
		this.secondLinkMethod = secondLinkMethod;
	}

	public String getThirdLinkMethod() {
		return thirdLinkMethod;
	}

	public void setThirdLinkMethod(String thirdLinkMethod) {
		this.thirdLinkMethod = thirdLinkMethod;
	}

	@Override
	public String toString() {
		return "ContractFlowDetailPartyMainResponse [key=" + key + ", value=" + value + ", firstMemberName="
				+ firstMemberName + ", firstCreditCode=" + firstCreditCode + ", firstAddress=" + firstAddress
				+ ", firstLegalRepresentative=" + firstLegalRepresentative + ", firstLinkMethod=" + firstLinkMethod
				+ ", secondMemberName=" + secondMemberName + ", secondCreditCode=" + secondCreditCode
				+ ", secondAddress=" + secondAddress + ", secondLegalRepresentative=" + secondLegalRepresentative
				+ ", secondLinkMethod=" + secondLinkMethod + ", thirdMemberName=" + thirdMemberName
				+ ", thirdCreditCode=" + thirdCreditCode + ", thirdAddress=" + thirdAddress
				+ ", thirdLegalRepresentative=" + thirdLegalRepresentative + ", thirdLinkMethod=" + thirdLinkMethod
				+ "]";
	}

}
