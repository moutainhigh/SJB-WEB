package sijibao.oa.jeesite.modules.intfz.response.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "用户实时信息")
public class UserRealTimeInfoResp {

    @ApiModelProperty(value = "用户id")
    private String id;

    @ApiModelProperty(value = "登录名（唯一账号）")
    private String loginName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "归属部门id")
    private String officeId;

    @ApiModelProperty(value = "归属部门名称")
    private String officeName;
    @ApiModelProperty(value = "归属部门是否可用")
    private String  useable;

    @ApiModelProperty(value = "岗位id")
    private String postId;

    @ApiModelProperty(value = "岗位名称")
    private String postName;
    @ApiModelProperty(value = "版本号")
    private String version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    @Override
    public String toString() {
        return "UserRealTimeInfoResp{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", officeId='" + officeId + '\'' +
                ", officeName='" + officeName + '\'' +
                ", useable='" + useable + '\'' +
                ", postId='" + postId + '\'' +
                ", postName='" + postName + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
