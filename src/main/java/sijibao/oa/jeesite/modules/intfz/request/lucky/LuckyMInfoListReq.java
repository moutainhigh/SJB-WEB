package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会活动Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyMInfoListReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="手机号")
	private String phone;
	@ApiModelProperty(value="姓名")
	private String userName;
	@ApiModelProperty(value="奖项")
	private Integer prize;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getPrize() {
		return prize;
	}
	public void setPrize(Integer prize) {
		this.prize = prize;
	}
	
}