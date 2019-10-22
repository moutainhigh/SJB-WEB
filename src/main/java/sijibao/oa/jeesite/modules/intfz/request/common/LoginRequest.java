package sijibao.oa.jeesite.modules.intfz.request.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录请求对象 
 * @author huangkai
 */
@ApiModel
public class LoginRequest {

//	@ApiModelProperty(value="手机号")
//	private String loginName;//手机号
	@ApiModelProperty(value="app版本号")
	private String appVersion; //app版本号
	@ApiModelProperty(value="机型")
	private String phoneType; //机型
	@ApiModelProperty(value="设备号")
	private String deviceCode; //设备号


//	public String getLoginName() {
//		return loginName;
//	}
//	public void setLoginName(String loginName) {
//		this.loginName = loginName;
//	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	@Override
	public String toString() {
		return "LoginRequest{" +
//				"loginName='" + loginName + '\'' +
				"appVersion='" + appVersion + '\'' +
				", phoneType='" + phoneType + '\'' +
				", deviceCode='" + deviceCode + '\'' +
				'}';
	}
}
