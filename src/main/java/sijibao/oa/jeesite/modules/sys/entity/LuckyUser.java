package sijibao.oa.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会用户Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyUser extends DataEntity<LuckyUser> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="openId")
	private String openId;		// openId
	@ApiModelProperty(value="用户名")
	private String userName;		// 用户名
	@ApiModelProperty(value="手机号")
	private String phone;		// 手机号
	@ApiModelProperty(value="头像url")
	private String imgUrl;		// 图像url
	
	public LuckyUser() {
		super();
	}

	public LuckyUser(String id){
		super(id);
	}

	@Length(min=1, max=64, message="openId长度必须介于 1 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=1, max=64, message="用户名长度必须介于 1 和 64 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=1, max=32, message="手机号长度必须介于 1 和 32 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=128, message="图像url长度必须介于 0 和 128 之间")
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
}