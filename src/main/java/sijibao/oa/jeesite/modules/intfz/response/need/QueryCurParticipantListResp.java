package sijibao.oa.jeesite.modules.intfz.response.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryCurParticipantListResp {

    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "姓名")
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
