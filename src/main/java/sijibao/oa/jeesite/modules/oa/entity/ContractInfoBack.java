/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 合同信息（后台添加）Entity
 * @author wanxb
 * @version 2018-10-24
 */
public class ContractInfoBack extends DataEntity<ContractInfoBack> {
	
	private static final long serialVersionUID = 1L;
	private String contractCode;		// 合同编号
	private String companyName;		// 公司名称
	private String abbreviation ;//公司简称
	private String creditCode;		// 统一社会信用代码
	private String address;		// 住址
	private String legalRepresentative;		// 法定代表人
	private String del;
	
	public ContractInfoBack() {
		super();
	}

	public ContractInfoBack(String id){
		super(id);
	}

	@Length(min=1, max=32, message="合同编号长度必须介于 1 和 32 之间")
	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	
	@Length(min=0, max=32, message="公司名称长度必须介于 0 和 32 之间")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Length(min=0, max=64, message="统一社会信用代码长度必须介于 0 和 64 之间")
	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	
	@Length(min=0, max=64, message="住址长度必须介于 0 和 64 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=32, message="法定代表人长度必须介于 0 和 32 之间")
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	@Override
	public String toString() {
		return "ContractInfoBack [contractCode=" + contractCode + ", companyName=" + companyName + ", abbreviation="
				+ abbreviation + ", creditCode=" + creditCode + ", address=" + address + ", legalRepresentative="
				+ legalRepresentative + ", del=" + del + "]";
	}


	
}