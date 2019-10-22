package sijibao.oa.jeesite.modules.intfz.response.usage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class PagedQueryUsageDetailWebResp {

    @ApiModelProperty(value = "功能")
    private String functionName;

    @ApiModelProperty(value = "产品端")
    private String terminalName;

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "使用次数")
    private int num;

    @ApiModelProperty(value = "使用人")
    private String userName;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "PagedQueryUsageDetailWebResp{" +
                "functionName='" + functionName + '\'' +
                ", terminalName='" + terminalName + '\'' +
                ", date='" + date + '\'' +
                ", num=" + num +
                ", userName=" + userName +
                '}';
    }
}