/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.request.message;

import com.sijibao.base.common.provider.entity.PagerBase;

import io.swagger.annotations.ApiModelProperty;

/**
 * 消息
 * @author wanxb
 * @version 2019-04-25
 */
public class MainMessageHandleReq extends PagerBase<MainMessageHandleReq> {

	@ApiModelProperty(value=" 0已读，1未读")
	private String readStatus;		// 0已读，1未读

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	@Override
	public String toString() {
		return "MainMessageHandleReq{" +
				"readStatus='" + readStatus + '\'' +
				'}';
	}
}