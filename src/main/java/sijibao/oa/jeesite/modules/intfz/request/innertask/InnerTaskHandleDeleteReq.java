package sijibao.oa.jeesite.modules.intfz.request.innertask;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class InnerTaskHandleDeleteReq {
    @ApiModelProperty(value = "任务ID", required = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}