package sijibao.oa.jeesite.modules.intfz.response.need;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class QueryHisProgressResp {
    @ApiModelProperty(value = "进度状态")
    private String progressName;
    @ApiModelProperty(value = "结束状态")
    private String isEnd;
    @ApiModelProperty(value = "进度负责人")
    private String principal;
    @ApiModelProperty(value = "状态变更时间")
    private long changeTime;
    @ApiModelProperty(value = "处理说明")
    private String handleExplain;

    //以下为非必须返回值的字段
    @ApiModelProperty(value = "需求紧急度")
    private String urgency;
    @ApiModelProperty(value = "优先级")
    private String priority;
    @ApiModelProperty(value = "产品端")
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

    public String getProgressName() {
        return progressName;
    }

    public void setProgressName(String progressName) {
        this.progressName = progressName;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
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

	public String getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(String isEnd) {
		this.isEnd = isEnd;
	}
    
}
