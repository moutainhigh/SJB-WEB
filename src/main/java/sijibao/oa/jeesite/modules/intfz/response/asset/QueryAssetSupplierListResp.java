package sijibao.oa.jeesite.modules.intfz.response.asset;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryAssetSupplierListResp implements Serializable {

    @ApiModelProperty(value = "供应商ID")
    private String id;
    @ApiModelProperty(value = "供应商名称")
    private String name;
    @ApiModelProperty(value = "供应商联系方式")
    private String contactWay;

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

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }
}
