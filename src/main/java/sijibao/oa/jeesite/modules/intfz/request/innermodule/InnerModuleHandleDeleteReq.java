package sijibao.oa.jeesite.modules.intfz.request.innermodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class InnerModuleHandleDeleteReq {

    @ApiModelProperty(value = "模块ID", required = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}