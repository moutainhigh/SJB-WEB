package sijibao.oa.jeesite.modules.intfz.request.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryCurParticipantListReq {

    @ApiModelProperty(value = "协作ID", required = true)
    private String needFlowId;

    public String getNeedFlowId() {
        return needFlowId;
    }

    public void setNeedFlowId(String needFlowId) {
        this.needFlowId = needFlowId;
    }

    @Override
    public String toString() {
        return "QueryCurParticipantListReq{" +
                "needFlowId='" + needFlowId + '\'' +
                '}';
    }
}
