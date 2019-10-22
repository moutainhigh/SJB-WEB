package sijibao.oa.jeesite.modules.intfz.request.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录请求对象 
 * @author Petter
 */
@ApiModel
public class LoginReq {

	@ApiModelProperty(value="手机号", required = true)
	private String loginName;//手机号
	@ApiModelProperty(value="密码", required = true)
	private String passWord; //密码
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@Override
	public String toString() {
		return "LoginReq [loginName=" + loginName + ", passWord=" + passWord + "]";
	}
	
}
