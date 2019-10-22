/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 合同信息Entity
 * @author wanxb
 * @version 2018-07-24
 */
public class ContractInfo extends DataEntity<ContractInfo> {
	
	private static final long serialVersionUID = 1L;
	private String custId;		// 客户id
	private String creditCode;		// 统一社会信用代码
	private String legalRepresentative;		// 法定代表人
	private String dispatchProportion;		// 调度费比例
	private String registeredAddress;//注册地址
	private String custTrades;//归属行业
	
	public ContractInfo() {
		super();
	}

	public ContractInfo(String id){
		super(id);
	}

	@Length(min=0, max=32, message="客户id长度必须介于 0 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=64, message="统一社会信用代码长度必须介于 0 和 64 之间")
	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	
	@Length(min=0, max=32, message="法定代表人长度必须介于 0 和 32 之间")
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	
	@Length(min=0, max=10, message="调度费比例长度必须介于 0 和 10 之间")
	public String getDispatchProportion() {
		return dispatchProportion;
	}

	public void setDispatchProportion(String dispatchProportion) {
		this.dispatchProportion = dispatchProportion;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getCustTrades() {
		return custTrades;
	}

	public void setCustTrades(String custTrades) {
		this.custTrades = custTrades;
	}

	@Override
	public String toString() {
		return "ContractInfo{" +
				"custId='" + custId + '\'' +
				", creditCode='" + creditCode + '\'' +
				", legalRepresentative='" + legalRepresentative + '\'' +
				", dispatchProportion='" + dispatchProportion + '\'' +
				", registeredAddress='" + registeredAddress + '\'' +
				", custTrades='" + custTrades + '\'' +
				'}';
	}
}