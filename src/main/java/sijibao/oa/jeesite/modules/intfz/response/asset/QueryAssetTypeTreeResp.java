package sijibao.oa.jeesite.modules.intfz.response.asset;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryAssetTypeTreeResp implements Serializable {

    @ApiModelProperty(value = "类别ID")
    private String id;
    @ApiModelProperty(value = "类别父ID")
    private String pId;
    @ApiModelProperty(value = "类别名称")
    private String name;
    @ApiModelProperty(value = "计量单位")
    private String unit;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
