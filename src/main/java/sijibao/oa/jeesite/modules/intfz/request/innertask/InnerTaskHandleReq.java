package sijibao.oa.jeesite.modules.intfz.request.innertask;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class InnerTaskHandleReq {
    @ApiModelProperty(value = "页数", required = true)
    private int pageNo;//页数
    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;//每页数量

    @ApiModelProperty(value = "所属模块名称")
    private String moduleName;
    @ApiModelProperty(value = "所属页面")
    private String page;
    @ApiModelProperty(value = "负责人姓名")
    private String principalName;

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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}
