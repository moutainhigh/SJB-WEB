package sijibao.oa.jeesite.modules.intfz.request.lucky;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会消息Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyMessageReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="消息内容")
	private String messageText;		// 消息内容
	@ApiModelProperty(value="发送时间")
	private long dateTimes;		// 发送时间
	@ApiModelProperty(value="openID")
	private String openId;		// 用户openid
	
	public LuckyMessageReq() {
		super();
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public long getDateTimes() {
		return dateTimes;
	}

	public void setDateTimes(long dateTimes) {
		this.dateTimes = dateTimes;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}