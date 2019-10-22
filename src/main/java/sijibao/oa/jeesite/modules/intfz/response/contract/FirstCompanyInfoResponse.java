/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.contract;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 甲方公司信息信息返回对象
 * @author wanxb
 * @version 2018-07-26
 */
@ApiModel(value="甲方公司信息返回对象")
public class FirstCompanyInfoResponse{
	@ApiModelProperty(value="甲方名称id")
	private String custId;		// 甲方名称id
	@ApiModelProperty(value="甲方客户名称")
	private String custName;		// 甲方客户名称
	@ApiModelProperty(value="客户注册地址")
	private String custAddress;		// 客户注册地址
	@ApiModelProperty(value="统一社会信用代码")
	private String creditCode;		// 统一社会信用代码
	@ApiModelProperty(value="法定代表人")
	private String legalRepresentative;		// 法定代表人
	@ApiModelProperty(value="调度费比例")
	private String dispatchProportion;		// 调度费比例
	@ApiModelProperty(value="联系人姓名")
	private String linkmanName;		// 联系人姓名
	@ApiModelProperty(value="联系人电话")
	private String linkmanPhone;	// 联系人电话
	@ApiModelProperty(value="联系人邮箱")
	private String linkmanMail;		//联系人邮箱
	@ApiModelProperty(value="联系人职位")
	private String linkmanPost;		// 联系人职位
	
	@ApiModelProperty(value="市场负责人姓名")
	private String marketLeaderName;//市场负责人姓名
	@ApiModelProperty(value="完整：0缺失，1完整")
	private String isOverAll;//完整：0缺失，1完整
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
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
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public String getLinkmanPhone() {
		return linkmanPhone;
	}
	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}
	public String getLinkmanPost() {
		return linkmanPost;
	}
	public void setLinkmanPost(String linkmanPost) {
		this.linkmanPost = linkmanPost;
	}
	
	public String getLinkmanMail() {
		return linkmanMail;
	}
	public void setLinkmanMail(String linkmanMail) {
		this.linkmanMail = linkmanMail;
	}
	
	public String getMarketLeaderName() {
		return marketLeaderName;
	}
	public void setMarketLeaderName(String marketLeaderName) {
		this.marketLeaderName = marketLeaderName;
	}
	public String getIsOverAll() {
		return isOverAll;
	}
	public void setIsOverAll(String isOverAll) {
		this.isOverAll = isOverAll;
	}
	@Override
	public String toString() {
		return "FirstCompanyInfoResponse [custId=" + custId + ", custName=" + custName + ", custAddress=" + custAddress
				+ ", creditCode=" + creditCode + ", legalRepresentative=" + legalRepresentative
				+ ", dispatchProportion=" + dispatchProportion + ", linkmanName=" + linkmanName + ", linkmanPhone="
				+ linkmanPhone + ", linkmanMail=" + linkmanMail + ", linkmanPost=" + linkmanPost + ", marketLeaderName="
				+ marketLeaderName + ", isOverAll=" + isOverAll + "]";
	}
}