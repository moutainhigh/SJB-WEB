/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报-发送给上级Entity
 * @author wanxb
 * @version 2018-12-12
 */
public class MainCopyToResponse extends PagerBase<MainCopyToResponse> {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "日志id")
	private String dailyId;		// 日志id
	@ApiModelProperty(value = "抄送id")
	private String copyToId;		// 发送给id
	@ApiModelProperty(value = "抄送给name")
	private String copyToName;		// 发送给name
	@ApiModelProperty(value = "阅读状态：0已读，1未读")
	private String readStatus;		// 阅读状态：0已读，1未读


	public String getDailyId() {
		return dailyId;
	}

	public void setDailyId(String dailyId) {
		this.dailyId = dailyId;
	}

	public String getCopyToId() {
		return copyToId;
	}

	public void setCopyToId(String copyToId) {
		this.copyToId = copyToId;
	}

	public String getCopyToName() {
		return copyToName;
	}

	public void setCopyToName(String copyToName) {
		this.copyToName = copyToName;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	@Override
	public String toString() {
		return "MainCopyToResponse{" +
				"dailyId='" + dailyId + '\'' +
				", copyToId='" + copyToId + '\'' +
				", copyToName='" + copyToName + '\'' +
				", readStatus='" + readStatus + '\'' +
				'}';
	}
}