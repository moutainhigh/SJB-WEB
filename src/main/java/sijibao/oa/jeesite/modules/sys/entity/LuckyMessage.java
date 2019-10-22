package sijibao.oa.jeesite.modules.sys.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.sijibao.oa.common.persistence.DataEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * 年会消息Entity
 * @author xby
 * @version 2019-01-14
 */
public class LuckyMessage extends DataEntity<LuckyMessage> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="消息内容")
	private String messageText;		// 消息内容
	@ApiModelProperty(value="发送时间")
	private long dateTimes;		// 发送时间
	@ApiModelProperty(value="openID")
	private String openId;		// 用户openid
	
	
	private Date dateTime;
	public LuckyMessage() {
		super();
	}

	public LuckyMessage(String id){
		super(id);
	}

	@Length(min=0, max=1000, message="消息内容长度必须介于 0 和 1000 之间")
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

	@Length(min=1, max=64, message="用户openid长度必须介于 1 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
}