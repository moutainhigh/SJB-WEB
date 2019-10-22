package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会用户Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyUserReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="openId")
	private String openId;		// openId
	@ApiModelProperty(value="用户名")
	private String userName;		// 用户名
	@ApiModelProperty(value="手机号")
	private String phone;		// 手机号
	@ApiModelProperty(value="头像url")
	private String imgUrl;		// 图像url
	
	public LuckyUserReq() {}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}