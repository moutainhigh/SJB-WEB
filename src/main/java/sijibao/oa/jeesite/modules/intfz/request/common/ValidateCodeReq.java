package sijibao.oa.jeesite.modules.intfz.request.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取验证码请求对象
 * @author Petter
 */
@ApiModel
public class ValidateCodeReq {

	@ApiModelProperty(value = "手机号", required = true)
	private String userName;//用户手机号
	@ApiModelProperty(value = "验证码")
	private String validateCode;//验证码

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	@Override
	public String toString() {
		return "ValidateCodeReq [userName=" + userName + ", validateCode=" + validateCode + "]";
	}
	
}
