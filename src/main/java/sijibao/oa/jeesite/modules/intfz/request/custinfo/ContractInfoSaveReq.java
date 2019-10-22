package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同信息保存对象
 * @author wanxb
 */
@ApiModel(value="合同信息保存对象")
public class ContractInfoSaveReq implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="统一社会信用代码")
	private String oldCreditCode;		// 统一社会信用代码
	@ApiModelProperty(value="统一社会信用代码")
	private String creditCode;		// 统一社会信用代码
	@ApiModelProperty(value="法定代表人")
	private String legalRepresentative;		// 法定代表人
	@ApiModelProperty(value="调度费比例")
	private String dispatchProportion;		// 调度费比例
	private String registeredAddress;//注册地址
	public String getCreditCode() {
		return creditCode;
	}
	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	public String getLegalRepresentative() {
		return legalRepresentative;
	}
	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
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
	
	public String getOldCreditCode() {
		return oldCreditCode;
	}
	public void setOldCreditCode(String oldCreditCode) {
		this.oldCreditCode = oldCreditCode;
	}
	@Override
	public String toString() {
		return "ContractInfoSaveReq [oldCreditCode=" + oldCreditCode + ", creditCode=" + creditCode
				+ ", legalRepresentative=" + legalRepresentative + ", dispatchProportion=" + dispatchProportion
				+ ", registeredAddress=" + registeredAddress + "]";
	}
	
}
