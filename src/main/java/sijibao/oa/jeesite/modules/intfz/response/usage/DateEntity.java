package sijibao.oa.jeesite.modules.intfz.response.usage;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class DateEntity implements Serializable {

    @ApiModelProperty(value = "周一日期字符串")
    private String mondayStr;
    @ApiModelProperty(value = "周二日期字符串")
    private String tuesdayStr;
    @ApiModelProperty(value = "周三日期字符串")
    private String wednesdayStr;
    @ApiModelProperty(value = "周四日期字符串")
    private String thursdayStr;
    @ApiModelProperty(value = "周五日期字符串")
    private String fridayStr;
    @ApiModelProperty(value = "周六日期字符串")
    private String saturdayStr;
    @ApiModelProperty(value = "周日日期字符串")
    private String sundayStr;

    public String getMondayStr() {
        return mondayStr;
    }

    public void setMondayStr(String mondayStr) {
        this.mondayStr = mondayStr;
    }

    public String getTuesdayStr() {
        return tuesdayStr;
    }

    public void setTuesdayStr(String tuesdayStr) {
        this.tuesdayStr = tuesdayStr;
    }

    public String getWednesdayStr() {
        return wednesdayStr;
    }

    public void setWednesdayStr(String wednesdayStr) {
        this.wednesdayStr = wednesdayStr;
    }

    public String getThursdayStr() {
        return thursdayStr;
    }

    public void setThursdayStr(String thursdayStr) {
        this.thursdayStr = thursdayStr;
    }

    public String getFridayStr() {
        return fridayStr;
    }

    public void setFridayStr(String fridayStr) {
        this.fridayStr = fridayStr;
    }

    public String getSaturdayStr() {
        return saturdayStr;
    }

    public void setSaturdayStr(String saturdayStr) {
        this.saturdayStr = saturdayStr;
    }

    public String getSundayStr() {
        return sundayStr;
    }

    public void setSundayStr(String sundayStr) {
        this.sundayStr = sundayStr;
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "mondayStr='" + mondayStr + '\'' +
                ", tuesdayStr='" + tuesdayStr + '\'' +
                ", wednesdayStr='" + wednesdayStr + '\'' +
                ", thursdayStr='" + thursdayStr + '\'' +
                ", fridayStr='" + fridayStr + '\'' +
                ", saturdayStr='" + saturdayStr + '\'' +
                ", sundayStr='" + sundayStr + '\'' +
                '}';
    }
}
