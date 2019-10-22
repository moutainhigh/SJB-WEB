/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 甲方公司信息信息
 * @author wanxb
 * @version 2018-07-26
 */
public class FirstCompanyInfo  extends DataEntity<FirstCompanyInfo> {
	
	private static final long serialVersionUID = 1L;
	private String custId;		// 甲方名称id
	private String custName;		// 甲方客户名称
	private String custAddress;		// 客户注册地址
	private String creditCode;		// 统一社会信用代码
	private String legalRepresentative;		// 法定代表人
	private String dispatchProportion;		// 调度费比例
	private String linkmanName;		// 联系人姓名
	private String linkmanPhone;	// 联系人电话
	private String linkmanMail;		//联系人邮箱
	private String linkmanPost;		// 联系人职位
	private String marketLeaderId;	//市场负责人
	private String marketLeaderName;	//市场负责人
	private User user ;//登入人
	
	
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
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMarketLeaderId() {
		return marketLeaderId;
	}
	public void setMarketLeaderId(String marketLeaderId) {
		this.marketLeaderId = marketLeaderId;
	}
	@Override
	public String toString() {
		return "FirstCompanyInfo [custId=" + custId + ", custName=" + custName + ", custAddress=" + custAddress
				+ ", creditCode=" + creditCode + ", legalRepresentative=" + legalRepresentative
				+ ", dispatchProportion=" + dispatchProportion + ", linkmanName=" + linkmanName + ", linkmanPhone="
				+ linkmanPhone + ", linkmanMail=" + linkmanMail + ", linkmanPost=" + linkmanPost + ", marketLeaderId="
				+ marketLeaderId + ", marketLeaderName=" + marketLeaderName + ", user=" + user + "]";
	}
	
}