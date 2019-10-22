package sijibao.oa.jeesite.modules.intfz.response.common;

import com.sijibao.oa.modules.sys.entity.User;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
public class UserExtend extends User {

    @ApiModelProperty(value = "类型，1机构，2人员")
    private String type;

    @ApiModelProperty(value = "是否可选，0不可选，1可选")
    private String status;

    @ApiModelProperty(value = "是否展示，0不展示，1展示")
    private String show;

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

    @Override
    public String toString() {
        return "UserExtend{" +
                "type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", show='" + show + '\'' +
                '}';
    }
}
