package sijibao.oa.jeesite.modules.intfz.response.need;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueryNeedFlowDetailAppResp {

    @ApiModelProperty(value = "协作ID")
    private String id;
    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "类型name")
    private String type;
    @ApiModelProperty(value = "类型ID")
    private String typeId;
    @ApiModelProperty(value = "标签")
    private List<String> labelList;
    @ApiModelProperty(value = "提交人")
    private String initiator;
    @ApiModelProperty(value = "提交时间")
    private long initiateTime;
    @ApiModelProperty(value = "描述")
    private String description;
    @ApiModelProperty(value = "进度状态")
    private String progressStatus;
    @ApiModelProperty(value = "是否是结束进度状态")
    private String isEnd;
    @ApiModelProperty(value = "处理说明")
    private String handleExplain;

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

    @ApiModelProperty(value = "状态变更时间")
    private long changeTime;
    @ApiModelProperty(value = "进度负责人")
    private String progressPrincipal;
    @ApiModelProperty(value = "当前负责人")
    private String curPrincipal;
    @ApiModelProperty(value = "参与人")
    private List<String> participantList;
    @ApiModelProperty(value = "可见的操作按钮，1评论，2下级负责人，3编辑参与人，4增加进度")
    private List<String> operationList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public long getInitiateTime() {
        return initiateTime;
    }

    public void setInitiateTime(long initiateTime) {
        this.initiateTime = initiateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
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

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

    public String getProgressPrincipal() {
        return progressPrincipal;
    }

    public void setProgressPrincipal(String progressPrincipal) {
        this.progressPrincipal = progressPrincipal;
    }

    public String getCurPrincipal() {
        return curPrincipal;
    }

    public void setCurPrincipal(String curPrincipal) {
        this.curPrincipal = curPrincipal;
    }

    public List<String> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<String> participantList) {
        this.participantList = participantList;
    }

    public List<String> getOperationList() {
        return operationList;
    }

    public void setOperationList(List<String> operationList) {
        this.operationList = operationList;
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd;
    }

}
