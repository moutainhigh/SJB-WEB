package sijibao.oa.jeesite.modules.intfz.web.LuckPersonel;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String openId; 
	private String text; // 消息内容
	private long sendDate; //时间
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getSendDate() {
		return sendDate;
	}
	public void setSendDate(long sendDate) {
		this.sendDate = sendDate;
	}
	@Override
	public String toString() {
		return "Message [openId=" + openId + ", text=" + text + ", sendDate=" + sendDate + "]";
	}
}
