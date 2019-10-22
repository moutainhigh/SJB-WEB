package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会答题结果Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyResultReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="openID")
	private String openId;		// 用户openid
	@ApiModelProperty(value="答对个数")
	private Integer rightNum;		// 答对个数
	@ApiModelProperty(value="答题总个数")
	private Integer allNum;		// 答题总个数
	@ApiModelProperty(value="答题时长")
	private Integer timeLong;		// 答题时长
	
	public LuckyResultReq() {
	}

	@Length(min=1, max=64, message="用户openid长度必须介于 1 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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
	
}