package sijibao.oa.jeesite.modules.intfz.request.common;

public class MessageRequest {
	/**
	 * 接收应用编码
	 */
	private String toAppCode;
	/**
	 * 接收用户的唯一标识
	 */
	private String toCode;
	/**
	 * 发送应用编码
	 */
	private String fromAppCode;
	/**
	 * 发送用户code
	 */
	private String fromCode;
	/**
	 * 消息类别
	 */
	private int type;
	/**
	 * 消息子类型
	 */
	private int subType;
	/**
	 * 消息内容
	 */
	private String data;
	/**
	 * 消息扩展(附加属性,如tel:1234,href:http://zuv.cc,等)
	 */
	private String extra;
	/**
	 * 消息通知主题栏
	 */
	private String noticeItem;
	/**
	 * 消息通知内容栏（IOS）
	 */
	private String noticeContentIos;
	
	/**
     * 消息通知内容栏（声音）
     */
    private String noticeVoiceContentIos;
	/**
	 * 消息通知内容栏（Android）
	 */
	private String noticeContentAndroid;
	/**
	 * 声音
	 */
	private String sound;
	/**
	 * 有效时间，时间长度，毫秒单位
	 */
	private long holdTime;
	public String getToAppCode() {
		return toAppCode;
	}
	public void setToAppCode(String toAppCode) {
		this.toAppCode = toAppCode;
	}
	public String getToCode() {
		return toCode;
	}
	public void setToCode(String toCode) {
		this.toCode = toCode;
	}
	public String getFromAppCode() {
		return fromAppCode;
	}
	public void setFromAppCode(String fromAppCode) {
		this.fromAppCode = fromAppCode;
	}
	public String getFromCode() {
		return fromCode;
	}
	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}
	public int getSubType() {
		return subType;
	}
	public void setSubType(int subType) {
		this.subType = subType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getNoticeItem() {
		return noticeItem;
	}
	public void setNoticeItem(String noticeItem) {
		this.noticeItem = noticeItem;
	}
	public String getNoticeContentIos() {
		return noticeContentIos;
	}
	public void setNoticeContentIos(String noticeContentIos) {
		this.noticeContentIos = noticeContentIos;
	}
	public String getNoticeVoiceContentIos() {
		return noticeVoiceContentIos;
	}
	public void setNoticeVoiceContentIos(String noticeVoiceContentIos) {
		this.noticeVoiceContentIos = noticeVoiceContentIos;
	}
	public String getNoticeContentAndroid() {
		return noticeContentAndroid;
	}
	public void setNoticeContentAndroid(String noticeContentAndroid) {
		this.noticeContentAndroid = noticeContentAndroid;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public long getHoldTime() {
		return holdTime;
	}
	public void setHoldTime(long holdTime) {
		this.holdTime = holdTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "MessageRequest [toAppCode=" + toAppCode + ", toCode=" + toCode + ", fromAppCode=" + fromAppCode
				+ ", fromCode=" + fromCode + ", type=" + type + ", subType=" + subType + ", data=" + data + ", extra="
				+ extra + ", noticeItem=" + noticeItem + ", noticeContentIos=" + noticeContentIos
				+ ", noticeVoiceContentIos=" + noticeVoiceContentIos + ", noticeContentAndroid=" + noticeContentAndroid
				+ ", sound=" + sound + ", holdTime=" + holdTime + "]";
	}

   
}
