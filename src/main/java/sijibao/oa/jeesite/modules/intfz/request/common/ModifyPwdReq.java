package sijibao.oa.jeesite.modules.intfz.request.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录请求对象 
 * @author Petter
 */
@ApiModel
public class ModifyPwdReq {
	@ApiModelProperty(value="旧密码")
	private String newPassword;//旧密码
	@ApiModelProperty(value="新密码")
	private String oldPassword;//新密码
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	@Override
	public String toString() {
		return "ModifyPwdReq [newPassword=" + newPassword + ", oldPassword=" + oldPassword + "]";
	}
	
}
