package sijibao.oa.jeesite.modules.intfz.request.usage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询功能使用情况统计列表的参数
 *
 * @author Jianghao Zhang
 */
@ApiModel
public class PagedQueryUsageWebReq {

    @ApiModelProperty(value = "日期")
    private long date;
    @ApiModelProperty(value = "功能名称")
    private String functionName;
    @ApiModelProperty(value = "页数", required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

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

    @Override
    public String toString() {
        return "PagedQueryUsageWebReq{" +
                "date=" + date +
                ", functionName=" + functionName +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
