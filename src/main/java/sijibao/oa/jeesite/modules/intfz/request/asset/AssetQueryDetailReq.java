package sijibao.oa.jeesite.modules.intfz.request.asset;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class AssetQueryDetailReq implements Serializable {

    @ApiModelProperty(value = "资产ID")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
