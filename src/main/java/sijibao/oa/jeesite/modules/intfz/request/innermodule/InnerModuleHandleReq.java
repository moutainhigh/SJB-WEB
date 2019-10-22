package sijibao.oa.jeesite.modules.intfz.request.innermodule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Petter
 */
@ApiModel
public class InnerModuleHandleReq{

    @ApiModelProperty(value = "页数", required = true)
    private int pageNo;//页数
    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;//每页数量

    @ApiModelProperty(value = "模块名称")
    private String moduleName;//模块名称
    @ApiModelProperty(value = "所属产品名称")
    private String productName;//所属产品名称
    @ApiModelProperty(value = "默认负责人姓名")
    private String defaultPrincipalName;//默认负责人姓名

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDefaultPrincipalName() {
        return defaultPrincipalName;
    }

    public void setDefaultPrincipalName(String defaultPrincipalName) {
        this.defaultPrincipalName = defaultPrincipalName;
    }
}