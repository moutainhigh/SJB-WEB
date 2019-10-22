package sijibao.oa.jeesite.modules.intfz.bean;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户基本信息
 * @author Petter
 */
@SuppressWarnings("serial")
@ApiModel
public class UserInfo implements Serializable {

	@ApiModelProperty(value="用户")
	private String userId;
	@ApiModelProperty(value="用户名称")
	private String userName;
    @ApiModelProperty(value="部门ID")
    private String officeId;
    @ApiModelProperty(value="部门名称")
    private String officeName;
	@ApiModelProperty(value = "归属部门是否可用")
	private String  useable;
	@ApiModelProperty(value="手机号")
	private String phone;
	@ApiModelProperty(value="登入唯一账号")
	private String loginName;
	@ApiModelProperty(value="岗位名称")
	private String postName;
	@ApiModelProperty(value="岗位编码")
	private String postCode;
	@ApiModelProperty(value="角色列表")
	private List<RoleInfo> roleList;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
    public String getOfficeId() {
        return officeId;
    }
    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }
    public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<RoleInfo> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<RoleInfo> roleList) {
		this.roleList = roleList;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				"userId='" + userId + '\'' +
				", userName='" + userName + '\'' +
				", officeId='" + officeId + '\'' +
				", officeName='" + officeName + '\'' +
				", useable='" + useable + '\'' +
				", phone='" + phone + '\'' +
				", loginName='" + loginName + '\'' +
				", postName='" + postName + '\'' +
				", postCode='" + postCode + '\'' +
				", roleList=" + roleList +
				'}';
	}
}
