package sijibao.oa.jeesite.modules.intfz.response.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户微信绑定基本信息
 * @author Petter
 */
@SuppressWarnings("serial")
@ApiModel(description="用户绑定基本对象")
public class RedCountResp implements java.io.Serializable{


	@ApiModelProperty(value="版本号用于强制登录", dataType="int")
	private String  redCount;

	public String getRedCount() {
		return redCount;
	}

	public void setRedCount(String redCount) {
		this.redCount = redCount;
	}

	@Override
	public String toString() {
		return "RedCountResp{" +
				"redCount='" + redCount + '\'' +
				'}';
	}
}