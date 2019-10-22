package sijibao.oa.jeesite.modules.intfz.response.asset;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryAssetPlaceResp implements Serializable {

    @ApiModelProperty(value = "放置地ID")
    private String id;
    @ApiModelProperty(value = "放置地名称")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
