package sijibao.oa.jeesite.modules.intfz.response.custinfo;


import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 合同返回对象信息
 * @author wanxb
 *
 */
@ApiModel(value="客户信息--合同明细返回对象")
public class ContractInfoDetailResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="统一社会信用代码")
	private String creditCode;		// 统一社会信用代码
	@ApiModelProperty(value="法定代表人")
	private String legalRepresentative;		// 法定代表人
	@ApiModelProperty(value="联系人")
	private String linkman;		// 联系人
	@ApiModelProperty(value="联系人电话")
	private String linkmanPhone;		// 联系人电话
	@ApiModelProperty(value="联系人邮箱")
	private String linkmanMail;		// 联系人邮箱
	@ApiModelProperty(value="调度费比例")
	private String dispatchProportion;		// 调度费比例
	@ApiModelProperty(value="注册地址")
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
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getLinkmanPhone() {
		return linkmanPhone;
	}
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}
	public String getLinkmanMail() {
		return linkmanMail;
	}
	public void setLinkmanMail(String linkmanMail) {
		this.linkmanMail = linkmanMail;
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
	@Override
	public String toString() {
		return "ContractInfoDetailResponse [creditCode=" + creditCode + ", legalRepresentative=" + legalRepresentative
				+ ", linkman=" + linkman + ", linkmanPhone=" + linkmanPhone + ", linkmanMail=" + linkmanMail
				+ ", dispatchProportion=" + dispatchProportion + ", registeredAddress=" + registeredAddress + "]";
	}
	
}
