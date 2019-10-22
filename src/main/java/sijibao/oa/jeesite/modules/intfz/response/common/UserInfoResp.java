package sijibao.oa.jeesite.modules.intfz.response.common;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.sijibao.oa.modules.intfz.bean.UserInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户微信绑定基本信息
 * @author Petter
 */
@SuppressWarnings("serial")
@ApiModel(description="用户绑定基本对象")
public class UserInfoResp implements java.io.Serializable{

	@ApiModelProperty(value="是否绑定:true已绑,false未绑", required = true)
	private Boolean isBind;
	@ApiModelProperty(value="用户基本信息", dataType="UserInfo")
	private UserInfo userInfo;

	@ApiModelProperty(value="菜单权限")
	private List<String> permissionList = new ArrayList<String>();
	@ApiModelProperty(value="我的申请菜单权限")
	private List<String> listSend = new ArrayList<String>();
	@ApiModelProperty(value="待我审批菜单权限")
	private List<String> listRecived = new ArrayList<String>();
	@ApiModelProperty(value="小工具菜单权限")
	private List<String> listTools = new ArrayList<String>();

	@ApiModelProperty(value="版本号用于强制登录", dataType="int")
	private int appCode;


	public UserInfoResp(){}
	public UserInfoResp(Boolean isBind, UserInfo userInfo){
		this.isBind = isBind;
		this.userInfo = userInfo;
	}
	public Boolean getIsBind() {
		return isBind;
	}
	public void setIsBind(Boolean isBind) {
		this.isBind = isBind;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	public List<String> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<String> permissionList) {
		this.permissionList = permissionList;
	}
	public List<String> getListSend() {
		return listSend;
	}
	public void setListSend(List<String> listSend) {
		this.listSend = listSend;
	}
	public List<String> getListRecived() {
		return listRecived;
	}
	public void setListRecived(List<String> listRecived) {
		this.listRecived = listRecived;
	}


	public Boolean getBind() {
		return isBind;
	}

	public void setBind(Boolean bind) {
		isBind = bind;
	}

	public List<String> getListTools() {
		return listTools;
	}

	public void setListTools(List<String> listTools) {
		this.listTools = listTools;
	}

	public int getAppCode() {
		return appCode;
	}

	public void setAppCode(int appCode) {
		this.appCode = appCode;
	}

}