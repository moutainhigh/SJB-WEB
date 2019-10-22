package sijibao.oa.jeesite.modules.intfz.response.usage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class PagedQueryUsageWebResp {

    @ApiModelProperty(value = "功能编码")
    private String functionCode;

    @ApiModelProperty(value = "功能name")
    private String functionName;

    @ApiModelProperty(value = "产品端code")
    private String terminalCode;

    @ApiModelProperty(value = "产品端name")
    private String terminalName;

    @ApiModelProperty(value = "周一使用次数")
    private int mondayNum;

    @ApiModelProperty(value = "周二使用次数")
    private int tuesdayNum;

    @ApiModelProperty(value = "周三使用次数")
    private int wednesdayNum;

    @ApiModelProperty(value = "周四使用次数")
    private int thursdayNum;

    @ApiModelProperty(value = "周五使用次数")
    private int fridayNum;

    @ApiModelProperty(value = "周六使用次数")
    private int saturdayNum;

    @ApiModelProperty(value = "周日使用次数")
    private int sundayNum;

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public int getMondayNum() {
        return mondayNum;
    }

    public void setMondayNum(int mondayNum) {
        this.mondayNum = mondayNum;
    }

    public int getTuesdayNum() {
        return tuesdayNum;
    }

    public void setTuesdayNum(int tuesdayNum) {
        this.tuesdayNum = tuesdayNum;
    }

    public int getWednesdayNum() {
        return wednesdayNum;
    }

    public void setWednesdayNum(int wednesdayNum) {
        this.wednesdayNum = wednesdayNum;
    }

    public int getThursdayNum() {
        return thursdayNum;
    }

    public void setThursdayNum(int thursdayNum) {
        this.thursdayNum = thursdayNum;
    }

    public int getFridayNum() {
        return fridayNum;
    }

    public void setFridayNum(int fridayNum) {
        this.fridayNum = fridayNum;
    }

    public int getSaturdayNum() {
        return saturdayNum;
    }

    public void setSaturdayNum(int saturdayNum) {
        this.saturdayNum = saturdayNum;
    }

    public int getSundayNum() {
        return sundayNum;
    }

    public void setSundayNum(int sundayNum) {
        this.sundayNum = sundayNum;
    }

    @Override
    public String toString() {
        return "PagedQueryUsageWebResp{" +
                "functionCode='" + functionCode + '\'' +
                ", functionName='" + functionName + '\'' +
                ", terminalCode='" + terminalCode + '\'' +
                ", terminalName='" + terminalName + '\'' +
                ", mondayNum=" + mondayNum +
                ", tuesdayNum=" + tuesdayNum +
                ", wednesdayNum=" + wednesdayNum +
                ", thursdayNum=" + thursdayNum +
                ", fridayNum=" + fridayNum +
                ", saturdayNum=" + saturdayNum +
                ", sundayNum=" + sundayNum +
                '}';
    }
}