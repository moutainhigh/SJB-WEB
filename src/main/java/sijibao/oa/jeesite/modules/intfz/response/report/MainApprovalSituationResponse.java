package sijibao.oa.jeesite.modules.intfz.response.report;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="单据提交情况返回对象")
public class MainApprovalSituationResponse implements Serializable {


    @ApiModelProperty(value="功能编号")
    private double subjectCode;
    @ApiModelProperty(value="序号")
    private int number;
    @ApiModelProperty(value="功能")
    private String function;
    @ApiModelProperty(value="产品端")
    private String producSide;
    @ApiModelProperty(value="上线时间")
    private String onlineTime;
    @ApiModelProperty(value="使用次数（当月新增次数）")
    private String useCount;
    @ApiModelProperty(value="未完结（本月提交，本月未完结）")
    private String unfinishedThis;
    @ApiModelProperty(value="已完成（本月提交，本月完结）")
    private String finishedThis;
    @ApiModelProperty(value="已完成（本月完结）")
    private String finished;
    @ApiModelProperty(value="本月总审批时间（天）")
    private double situationSumDate;
    @ApiModelProperty(value="本月总审批时间（秒）")
    private int situationSumTime;
    @ApiModelProperty(value="上月使用次数（当月新增次数）")
    private String useCountLast;
    @ApiModelProperty(value="上月总审批时间（天）")
    private double situationSumDateLast;
    @ApiModelProperty(value="上月总审批时间（秒）")
    private int situationSumTimeLast;
    @ApiModelProperty(value="年平均使用次数")
    private int yearAverageCount;

    public double getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(double subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getProducSide() {
        return producSide;
    }

    public void setProducSide(String producSide) {
        this.producSide = producSide;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getUseCount() {
        return useCount;
    }

    public void setUseCount(String useCount) {
        this.useCount = useCount;
    }

    public String getUnfinishedThis() {
        return unfinishedThis;
    }

    public void setUnfinishedThis(String unfinishedThis) {
        this.unfinishedThis = unfinishedThis;
    }

    public String getFinishedThis() {
        return finishedThis;
    }

    public void setFinishedThis(String finishedThis) {
        this.finishedThis = finishedThis;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public double getSituationSumDate() {
        return situationSumDate;
    }

    public void setSituationSumDate(double situationSumDate) {
        this.situationSumDate = situationSumDate;
    }

    public int getSituationSumTime() {
        return situationSumTime;
    }

    public void setSituationSumTime(int situationSumTime) {
        this.situationSumTime = situationSumTime;
    }

    public String getUseCountLast() {
        return useCountLast;
    }

    public void setUseCountLast(String useCountLast) {
        this.useCountLast = useCountLast;
    }

    public double getSituationSumDateLast() {
        return situationSumDateLast;
    }

    public void setSituationSumDateLast(double situationSumDateLast) {
        this.situationSumDateLast = situationSumDateLast;
    }

    public int getSituationSumTimeLast() {
        return situationSumTimeLast;
    }

    public void setSituationSumTimeLast(int situationSumTimeLast) {
        this.situationSumTimeLast = situationSumTimeLast;
    }

    public int getYearAverageCount() {
        return yearAverageCount;
    }

    public void setYearAverageCount(int yearAverageCount) {
        this.yearAverageCount = yearAverageCount;
    }

    @Override
    public String toString() {
        return "MainApprovalSituationResponse{" +
                "subjectCode=" + subjectCode +
                ", number=" + number +
                ", function='" + function + '\'' +
                ", producSide='" + producSide + '\'' +
                ", onlineTime='" + onlineTime + '\'' +
                ", useCount='" + useCount + '\'' +
                ", unfinishedThis='" + unfinishedThis + '\'' +
                ", finishedThis='" + finishedThis + '\'' +
                ", finished='" + finished + '\'' +
                ", situationSumDate=" + situationSumDate +
                ", situationSumTime=" + situationSumTime +
                ", useCountLast='" + useCountLast + '\'' +
                ", situationSumDateLast=" + situationSumDateLast +
                ", situationSumTimeLast=" + situationSumTimeLast +
                ", yearAverageCount=" + yearAverageCount +
                '}';
    }
}
