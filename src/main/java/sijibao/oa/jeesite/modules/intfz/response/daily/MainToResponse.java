/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.intfz.response.daily;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * 日报-发送给上级Entity
 * @author wanxb
 * @version 2018-12-12
 */
public class MainToResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "发送")
	private List<MainSendToResponse> sendTo;
	@ApiModelProperty(value = "抄送")
	private List<MainCopyToResponse> copyTo;

	public List<MainSendToResponse> getSendTo() {
		return sendTo;
	}

	public void setSendTo(List<MainSendToResponse> sendTo) {
		this.sendTo = sendTo;
	}

	public List<MainCopyToResponse> getCopyTo() {
		return copyTo;
	}

	public void setCopyTo(List<MainCopyToResponse> copyTo) {
		this.copyTo = copyTo;
	}

	@Override
	public String toString() {
		return "MainToResponse{" +
				"sendTo=" + sendTo +
				", copyTo=" + copyTo +
				'}';
	}
}