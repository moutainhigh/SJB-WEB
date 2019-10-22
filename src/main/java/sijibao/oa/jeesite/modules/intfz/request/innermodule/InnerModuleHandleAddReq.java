package sijibao.oa.jeesite.modules.intfz.request.innermodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class InnerModuleHandleAddReq {

    @ApiModelProperty(value = "模块名称", required = true)
    private String name;
    @ApiModelProperty(value = "所属产品ID", required = true)
    private String productId;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "默认负责人ID", required = true)
    private String defaultPrincipalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDefaultPrincipalId() {
        return defaultPrincipalId;
    }

    public void setDefaultPrincipalId(String defaultPrincipalId) {
        this.defaultPrincipalId = defaultPrincipalId;
    }
}