package sijibao.oa.jeesite.modules.intfz.request.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class SetNextPrincipalReq {

    @ApiModelProperty(value = "协作ID", required = true)
    private String needFlowId;
    @ApiModelProperty(value = "用户ID", required = true)
    private String userId;

    public String getNeedFlowId() {
        return needFlowId;
    }

    public void setNeedFlowId(String needFlowId) {
        this.needFlowId = needFlowId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SetNextPrincipalReq{" +
                "needFlowId='" + needFlowId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
