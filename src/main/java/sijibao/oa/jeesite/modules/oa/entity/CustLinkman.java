/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * @author wanxb
 * @version 2018-05-25
 */
public class CustLinkman extends DataEntity<CustLinkman> {
	
	private static final long serialVersionUID = 1L;
	private String custId;		// 客户id
	private String linkmanName;		// 联系人姓名
	private String linkmanPhone;		// 联系人电话
	private String linkmanPost;		// 联系人职位
	private String isContractLinkman;//合同联系人：0否，1是
	private String linkmanMail;		//联系人邮箱
	private int orderNum;//排序用序号
	
	public CustLinkman() {
		super();
	}

	public CustLinkman(String id){
		super(id);
	}

	@Length(min=0, max=32, message="客户id长度必须介于 0 和 32 之间")
	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	@Length(min=0, max=32, message="联系人姓名长度必须介于 0 和 32 之间")
	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	
	@Length(min=0, max=16, message="联系人电话长度必须介于 0 和 16 之间")
	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}
	
	@Length(min=0, max=32, message="联系人职位长度必须介于 0 和 32 之间")
	public String getLinkmanPost() {
		return linkmanPost;
	}

	public void setLinkmanPost(String linkmanPost) {
		this.linkmanPost = linkmanPost;
	}

	public String getIsContractLinkman() {
		return isContractLinkman;
	}

	public void setIsContractLinkman(String isContractLinkman) {
		this.isContractLinkman = isContractLinkman;
	}

	public String getLinkmanMail() {
		return linkmanMail;
	}

	public void setLinkmanMail(String linkmanMail) {
		this.linkmanMail = linkmanMail;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public String toString() {
		return "CustLinkman [custId=" + custId + ", linkmanName=" + linkmanName + ", linkmanPhone=" + linkmanPhone
				+ ", linkmanPost=" + linkmanPost + ", isContractLinkman=" + isContractLinkman + ", linkmanMail="
				+ linkmanMail + ", orderNum=" + orderNum + "]";
	}

	
	
}