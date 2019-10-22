package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyActiveQueryReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="openID")
	private String openId;  //openId 
	@ApiModelProperty(value="phone")
	private String phone;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public LuckyActiveQueryReq(){}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}