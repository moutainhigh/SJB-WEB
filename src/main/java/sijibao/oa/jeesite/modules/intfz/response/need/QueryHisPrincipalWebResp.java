package sijibao.oa.jeesite.modules.intfz.response.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryHisPrincipalWebResp {
    @ApiModelProperty(value = "开始负责时间")
    private long startTime;
    @ApiModelProperty(value = "人员姓名")
    private String name;
    @ApiModelProperty(value = "登录账号")
    private String loginName;
    @ApiModelProperty(value = "部门")
    private String dept;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
