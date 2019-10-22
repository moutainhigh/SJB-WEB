package sijibao.oa.jeesite.modules.intfz.request.usage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class ExportUsageWeekOrDayReq {

    @ApiModelProperty(value = "导出内容，1周报表，2具体报表", required = true)
    private String exportContent;
    @ApiModelProperty(value = "日期")
    private long date;
    @ApiModelProperty(value = "功能名称")
    private String functionName;

    public String getExportContent() {
        return exportContent;
    }

    public void setExportContent(String exportContent) {
        this.exportContent = exportContent;
    }

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

    @Override
    public String toString() {
        return "ExportUsageWeekOrDayReq{" +
                "exportContent='" + exportContent + '\'' +
                ", date=" + date +
                ", functionName='" + functionName + '\'' +
                '}';
    }
}
