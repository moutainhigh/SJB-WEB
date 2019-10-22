package sijibao.oa.jeesite.modules.sys.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会用户Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyUserResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="openID")
	private String openId;		// openId
	@ApiModelProperty(value="用户名")
	private String userName;		// 用户名
	@ApiModelProperty(value="手机号")
	private String phone;		// 手机号
	@ApiModelProperty(value="头像url")
	private String imgUrl;		// 图像url
	@ApiModelProperty(value="答对个数")
	private Integer rightNum;		// 答对个数
	@ApiModelProperty(value="答题总个数")
	private Integer allNum;		// 答题总个数
	@ApiModelProperty(value="答题时长")
	private Integer timeLong;		// 答题时长
	@ApiModelProperty(value="名次")
	private Integer rank; //名次
	
	public LuckyUserResult() {
		super();
	}

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

	public Integer getRightNum() {
		return rightNum;
	}

	public void setRightNum(Integer rightNum) {
		this.rightNum = rightNum;
	}

	public Integer getAllNum() {
		return allNum;
	}

	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}

	public Integer getTimeLong() {
		return timeLong;
	}

	public void setTimeLong(Integer timeLong) {
		this.timeLong = timeLong;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	

}