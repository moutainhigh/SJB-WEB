/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 合同归档保存请求对象
 * @author wanxb
 * @version 2018-10-22
 */
public class ContractHis extends DataEntity<ContractHis> {
	
	private static final long serialVersionUID = 1L;
	private String contractName;		// 合同名称
	private String configId;		// 合同模板id
	private String version;		// 模版版本号
	private String associationMainId;		// 主合同id
	private String contractCode;		// 合同编号
	private String firstMemberName;		// 甲方名称
	private String firstCreditCode;		// 甲方统一社会信用代码（个人为身份证）
	private String firstAddress;		// 甲方住所
	private String firstLegalRepresentative;		// 甲方法定代表人（甲方为企业有）
	private String firstLinkMethod;		// 甲方联系方式（甲方为个人有）
	private String secondMemberName;		// 乙方名称
	private String secondCreditCode;		// 乙方统一社会信用代码（个人为身份证）
	private String secondAddress;		// 乙方住所
	private String secondLegalRepresentative;		// 乙方法定代表人（乙方为企业有）
	private String secondLinkMethod;		// 乙方联系方式（乙方为个人有）
	private String thirdMemberName;		// 丙方名称
	private String thirdCreditCode;		// 丙方统一社会信用代码（个人为身份证）
	private String thirdAddress;		// 丙方住所
	private String thirdLegalRepresentative;		// 丙方法定代表人（丙方为企业有）
	private String thirdLinkMethod;		// 丙方联系方式（丙方为个人有）
	private Date contractStartTime;		// 合同开始日期
	private Date contractEndTime;		// 合同开始日期
	private String contractLeaderId;		// 合同负责人id
	private String signLeaderId;		// 签约人id
	private String expressCompany;		// 快递公司
	private String expressBill;		// 快递单号
	private String contractHisStatus;		// 归档合同状态
	private String contractHisMethod;		// 归档合同方式:1手动归档，2经审核归档
	private String contractType;		// 合同类型
	private String contractHisRenewal;//合同是否续签：0否，1是
	
	
	private String faint;//模糊搜索字段
	private Date startTime;		// 合同开始日期
	private Date endTime;		// 合同结束日期
	
	public ContractHis() {
		super();
	}

	public ContractHis(String id){
		super(id);
	}

	@Length(min=1, max=300, message="合同名称长度必须介于 1 和 300 之间")
	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	
	@Length(min=1, max=32, message="合同模板id长度必须介于 1 和 32 之间")
	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}
	
	@Length(min=1, max=32, message="模版版本号长度必须介于 1 和 32 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=1, max=32, message="主合同id长度必须介于 1 和 32 之间")
	public String getAssociationMainId() {
		return associationMainId;
	}

	public void setAssociationMainId(String associationMainId) {
		this.associationMainId = associationMainId;
	}
	
	@Length(min=1, max=32, message="合同编号长度必须介于 1 和 32 之间")
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@Length(min=1, max=300, message="甲方名称长度必须介于 1 和 300 之间")
	public String getFirstMemberName() {
		return firstMemberName;
	}

	public void setFirstMemberName(String firstMemberName) {
		this.firstMemberName = firstMemberName;
	}
	
	@Length(min=1, max=64, message="甲方统一社会信用代码（个人为身份证）长度必须介于 1 和 64 之间")
	public String getFirstCreditCode() {
		return firstCreditCode;
	}

	public void setFirstCreditCode(String firstCreditCode) {
		this.firstCreditCode = firstCreditCode;
	}
	
	@Length(min=1, max=64, message="甲方住所长度必须介于 1 和 64 之间")
	public String getFirstAddress() {
		return firstAddress;
	}

	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}
	
	@Length(min=1, max=32, message="甲方法定代表人（甲方为企业有）长度必须介于 1 和 32 之间")
	public String getFirstLegalRepresentative() {
		return firstLegalRepresentative;
	}

	public void setFirstLegalRepresentative(String firstLegalRepresentative) {
		this.firstLegalRepresentative = firstLegalRepresentative;
	}
	
	@Length(min=1, max=32, message="甲方联系方式（甲方为个人有）长度必须介于 1 和 32 之间")
	public String getFirstLinkMethod() {
		return firstLinkMethod;
	}

	public void setFirstLinkMethod(String firstLinkMethod) {
		this.firstLinkMethod = firstLinkMethod;
	}
	
	@Length(min=1, max=300, message="乙方名称长度必须介于 1 和 300 之间")
	public String getSecondMemberName() {
		return secondMemberName;
	}

	public void setSecondMemberName(String secondMemberName) {
		this.secondMemberName = secondMemberName;
	}
	
	@Length(min=1, max=64, message="乙方统一社会信用代码（个人为身份证）长度必须介于 1 和 64 之间")
	public String getSecondCreditCode() {
		return secondCreditCode;
	}

	public void setSecondCreditCode(String secondCreditCode) {
		this.secondCreditCode = secondCreditCode;
	}
	
	@Length(min=1, max=64, message="乙方住所长度必须介于 1 和 64 之间")
	public String getSecondAddress() {
		return secondAddress;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}
	
	@Length(min=1, max=32, message="乙方法定代表人（乙方为企业有）长度必须介于 1 和 32 之间")
	public String getSecondLegalRepresentative() {
		return secondLegalRepresentative;
	}

	public void setSecondLegalRepresentative(String secondLegalRepresentative) {
		this.secondLegalRepresentative = secondLegalRepresentative;
	}
	
	@Length(min=1, max=32, message="乙方联系方式（乙方为个人有）长度必须介于 1 和 32 之间")
	public String getSecondLinkMethod() {
		return secondLinkMethod;
	}

	public void setSecondLinkMethod(String secondLinkMethod) {
		this.secondLinkMethod = secondLinkMethod;
	}
	
	@Length(min=1, max=300, message="丙方名称长度必须介于 1 和 300 之间")
	public String getThirdMemberName() {
		return thirdMemberName;
	}

	public void setThirdMemberName(String thirdMemberName) {
		this.thirdMemberName = thirdMemberName;
	}
	
	@Length(min=1, max=64, message="丙方统一社会信用代码（个人为身份证）长度必须介于 1 和 64 之间")
	public String getThirdCreditCode() {
		return thirdCreditCode;
	}

	public void setThirdCreditCode(String thirdCreditCode) {
		this.thirdCreditCode = thirdCreditCode;
	}
	
	@Length(min=1, max=64, message="丙方住所长度必须介于 1 和 64 之间")
	public String getThirdAddress() {
		return thirdAddress;
	}

	public void setThirdAddress(String thirdAddress) {
		this.thirdAddress = thirdAddress;
	}
	
	@Length(min=1, max=32, message="丙方法定代表人（丙方为企业有）长度必须介于 1 和 32 之间")
	public String getThirdLegalRepresentative() {
		return thirdLegalRepresentative;
	}

	public void setThirdLegalRepresentative(String thirdLegalRepresentative) {
		this.thirdLegalRepresentative = thirdLegalRepresentative;
	}
	
	@Length(min=1, max=32, message="丙方联系方式（丙方为个人有）长度必须介于 1 和 32 之间")
	public String getThirdLinkMethod() {
		return thirdLinkMethod;
	}

	public void setThirdLinkMethod(String thirdLinkMethod) {
		this.thirdLinkMethod = thirdLinkMethod;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getContractStartTime() {
		return contractStartTime;
	}

	public void setContractStartTime(Date contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getContractEndTime() {
		return contractEndTime;
	}

	public void setContractEndTime(Date contractEndTime) {
		this.contractEndTime = contractEndTime;
	}
	
	@Length(min=1, max=32, message="合同负责人id长度必须介于 1 和 32 之间")
	public String getContractLeaderId() {
		return contractLeaderId;
	}

	public void setContractLeaderId(String contractLeaderId) {
		this.contractLeaderId = contractLeaderId;
	}
	
	@Length(min=1, max=32, message="签约人id长度必须介于 1 和 32 之间")
	public String getSignLeaderId() {
		return signLeaderId;
	}

	public void setSignLeaderId(String signLeaderId) {
		this.signLeaderId = signLeaderId;
	}
	
	@Length(min=0, max=64, message="快递公司长度必须介于 0 和 64 之间")
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	
	@Length(min=0, max=32, message="快递单号长度必须介于 0 和 32 之间")
	public String getExpressBill() {
		return expressBill;
	}

	public void setExpressBill(String expressBill) {
		this.expressBill = expressBill;
	}
	
	@Length(min=1, max=32, message="归档合同状态长度必须介于 1 和 32 之间")
	public String getContractHisStatus() {
		return contractHisStatus;
	}

	public void setContractHisStatus(String contractHisStatus) {
		this.contractHisStatus = contractHisStatus;
	}

	public String getContractHisMethod() {
		return contractHisMethod;
	}

	public void setContractHisMethod(String contractHisMethod) {
		this.contractHisMethod = contractHisMethod;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractHisRenewal() {
		return contractHisRenewal;
	}

	public void setContractHisRenewal(String contractHisRenewal) {
		this.contractHisRenewal = contractHisRenewal;
	}

	public String getFaint() {
		return faint;
	}

	public void setFaint(String faint) {
		this.faint = faint;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
	
}