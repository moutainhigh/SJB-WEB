package sijibao.oa.jeesite.modules.intfz.request.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryNeedFlowDetailReq {
    @ApiModelProperty(value = "协作ID", required = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QueryNeedFlowDetailReq{" +
                "id='" + id + '\'' +
                '}';
    }
}
