package sijibao.oa.jeesite.modules.intfz.response.innermodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel(description = "公司内部产品模块列表返回对象")
public class InnerModuleHandleResp {
    @ApiModelProperty(value = "模块ID")
    private String id;//主键ID
    @ApiModelProperty(value = "模块名称")
    private String name;//模块名称
    @ApiModelProperty(value = "产品名称")
    private String productName;//产品名称
    @ApiModelProperty(value = "备注")
    private String remarks;//备注
    @ApiModelProperty(value = "默认责任人")
    private String defaultPrincipalName;//默认责任人

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remark) {
        this.remarks = remark;
    }

    public String getDefaultPrincipalName() {
        return defaultPrincipalName;
    }

    public void setDefaultPrincipalName(String defaultPrincipalName) {
        this.defaultPrincipalName = defaultPrincipalName;
    }

    @Override
    public String toString() {
        return "MainContractHisResponse [id=" + id + ", name=" + name + ", productName=" + productName + ", remarks=" + remarks + ", defaultPrincipalName" + defaultPrincipalName + "]";
    }
}
