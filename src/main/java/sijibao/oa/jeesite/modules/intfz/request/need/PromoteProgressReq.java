package sijibao.oa.jeesite.modules.intfz.request.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class PromoteProgressReq {

    @ApiModelProperty(value = "协作ID", required = true)
    private String needFlowId;
    @ApiModelProperty(value = "进度状态名称", required = true)
    private String progressName;
    @ApiModelProperty(value = "进度状态是否是结束状态", required = true)
    private String isEnd;
    @ApiModelProperty(value = "处理说明", required = true)
    private String handleExplain;
    //非必传参数
    @ApiModelProperty(value = "需求紧急度")
    private String urgency;
    @ApiModelProperty(value = "优先级")
    private String priority;
    @ApiModelProperty(value = "产品端，以英文逗号分隔")
    private String productEnd;
    @ApiModelProperty(value = "预计设计时间")
    private long planDesignTime;
    @ApiModelProperty(value = "预计开发时间")
    private long planDevTime;
    @ApiModelProperty(value = "预计提测时间")
    private long planTestTime;
    @ApiModelProperty(value = "预计上线时间")
    private long planReleaseTime;
    @ApiModelProperty(value = "实际上线时间")
    private long actualReleaseTime;
    @ApiModelProperty(value = "培训时间")
    private long trainTime;

    public String getNeedFlowId() {
        return needFlowId;
    }

    public void setNeedFlowId(String needFlowId) {
        this.needFlowId = needFlowId;
    }

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

    public String getHandleExplain() {
        return handleExplain;
    }

    public void setHandleExplain(String handleExplain) {
        this.handleExplain = handleExplain;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProductEnd() {
        return productEnd;
    }

    public void setProductEnd(String productEnd) {
        this.productEnd = productEnd;
    }

    public long getPlanDesignTime() {
        return planDesignTime;
    }

    public void setPlanDesignTime(long planDesignTime) {
        this.planDesignTime = planDesignTime;
    }

    public long getPlanDevTime() {
        return planDevTime;
    }

    public void setPlanDevTime(long planDevTime) {
        this.planDevTime = planDevTime;
    }

    public long getPlanTestTime() {
        return planTestTime;
    }

    public void setPlanTestTime(long planTestTime) {
        this.planTestTime = planTestTime;
    }

    public long getPlanReleaseTime() {
        return planReleaseTime;
    }

    public void setPlanReleaseTime(long planReleaseTime) {
        this.planReleaseTime = planReleaseTime;
    }

    public long getActualReleaseTime() {
        return actualReleaseTime;
    }

    public void setActualReleaseTime(long actualReleaseTime) {
        this.actualReleaseTime = actualReleaseTime;
    }

    public long getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(long trainTime) {
        this.trainTime = trainTime;
    }

    @Override
    public String toString() {
        return "PromoteProgressReq ["
                + "needFlowId=" + needFlowId + ", "
                + "progressName=" + progressName + ", "
                + "handleExplain=" + handleExplain + ", "
                + "urgency=" + urgency + ", "
                + "priority=" + priority + ", "
                + "productEnd=" + productEnd + ", "
                + "planDesignTime=" + planDesignTime + ", "
                + "planDevTime=" + planDevTime + ", "
                + "planTestTime=" + planTestTime + ", "
                + "planReleaseTime=" + planReleaseTime + ", "
                + "actualReleaseTime=" + actualReleaseTime + ", "
                + "trainTime=" + trainTime + "]";
    }
}
