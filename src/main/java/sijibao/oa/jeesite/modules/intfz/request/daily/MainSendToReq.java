/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.daily;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModel;

/**
 * 日报-发送给上级Entity
 * @author wanxb
 * @version 2018-12-12
 */
@ApiModel(value="日报-发送给上级Entity")
public class MainSendToReq extends PagerBase<MainSendToReq> {
	
	private static final long serialVersionUID = 1L;
	private String dailyId;		// 日志id
	private String sendToId;		// 发送给id
	private String sendToName;		// 发送给name
	private String readStatus;		// 阅读状态：0已读，1未读
	public String getDailyId() {
		return dailyId;
	}
	public void setDailyId(String dailyId) {
		this.dailyId = dailyId;
	}
	public String getSendToId() {
		return sendToId;
	}
	public void setSendToId(String sendToId) {
		this.sendToId = sendToId;
	}
	public String getSendToName() {
		return sendToName;
	}
	public void setSendToName(String sendToName) {
		this.sendToName = sendToName;
	}
	public String getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	@Override
	public String toString() {
		return "MainSendToReq [dailyId=" + dailyId + ", sendToId=" + sendToId + ", sendToName=" + sendToName
				+ ", readStatus=" + readStatus + "]";
	}
	
}