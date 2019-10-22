package sijibao.oa.jeesite.modules.intfz.request.usage;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询功能使用情况统计列表的参数
 *
 * @author Jianghao Zhang
 */
@ApiModel
public class PagedQueryUsageDetailWebReq implements Serializable {

    @ApiModelProperty(value = "功能编码")
    private String functionCode;
    @ApiModelProperty(value = "产品端编码")
    private String terminalCode;
    @ApiModelProperty(value = "日期字符串")
    private String date;
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "页数", required = true)
    private int pageNo;
    @ApiModelProperty(value = "每页数量", required = true)
    private int pageSize;

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "PagedQueryUsageDetailWebReq{" +
                "functionCode='" + functionCode + '\'' +
                ", terminalCode='" + terminalCode + '\'' +
                ", date='" + date + '\'' +
                ", userName='" + userName + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
