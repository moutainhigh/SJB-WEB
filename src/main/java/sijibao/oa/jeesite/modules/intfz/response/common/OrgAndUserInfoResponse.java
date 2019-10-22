package sijibao.oa.jeesite.modules.intfz.response.common;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

/**
 * 机构用户返回结果对象
 *
 * @author xuby
 */
public class OrgAndUserInfoResponse {

    @ApiModelProperty(value = "主键ID")
    private String id; //主键ID

    @ApiModelProperty(value = "父级ID")
    private String pId; //父级ID

    @ApiModelProperty(value = "名称")
    private String name; //名称

    @ApiModelProperty(value = "类型，1机构，2人员")
    private String type; //类型

    @ApiModelProperty(value = "是否可选，1可选，0不可选")
    private String status; //状态

    @ApiModelProperty(value = "是否展示，1展示，0不展示")
    private String show; //是否展示

    //不参与序列化，即使List为null依然返回空数组 {"userInfo":[]} 注意，这个地方与web端不同！
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private List<UserExtend> userInfo; //机构下属用户

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public List<UserExtend> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserExtend> userInfo) {
        this.userInfo = userInfo;
    }

    public static final String TYPE_ORG = "1";
    public static final String TYPE_USER = "2";

    public static final String STATUS_SELECTABLE = "1";
    public static final String STATUS_NOT_SELECTABLE = "0";

    public static final String SHOW_YES = "1";//展示
    public static final String SHOW_NO = "0";//不展示

    @Override
    public String toString() {
        return "OrgAndUserInfoResponse{" +
                "id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", show='" + show + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
