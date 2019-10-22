package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.messageremind;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 提醒管理详细信息
 * @author: xgx
 * @create: 2019-09-04 15:01
 **/
@ApiModel(value="提醒管理--详细信息对象")
public class MainMessageRemindDetailResp implements Serializable {

    private static final long serialVersionUID = 8296872284159042740L;
    @ApiModelProperty(value="主键id")
    private String id;
    @ApiModelProperty(value="提醒管理分类标识：0-代办提醒，1-审批提醒，2-其他")
    private Integer remindCategory;
    @ApiModelProperty(value="提醒管理分类下提醒类型标识：0-报销，1-接待，2-出差，3-合同，4-协作，5-立项")
    private Integer remindType;
    @ApiModelProperty(value="提醒的对象")
    private String remindObject;
    @ApiModelProperty(value="提醒的方式：0-邮件，1-短信")
    private List<String> remindWays;
    @ApiModelProperty(value="发送频率：0-按天，1-按星期，2-按月，3-一次，4-即使发送")
    private Integer sendFrequency;
    @ApiModelProperty(value="发送频率一次的开始时间")
    private Long startTime;
    @ApiModelProperty(value="是否包括周六周日：0-否，1-是")
    private Integer isWeek;
    @ApiModelProperty(value="发送频率时分")
    private String pollHourMin;
    @ApiModelProperty(value="发送频率按月的每几月")
    private Integer pollMonth;
    @ApiModelProperty(value="发送频率按月的具体日")
    private Integer pollMonthday;
    @ApiModelProperty(value="发送频率按周的每几周")
    private Integer pollWeek;
    @ApiModelProperty(value="发送频率按周的具体周")
    private Integer pollWeekday;
    @ApiModelProperty(value="邮件黑名单")
    private List<String> emailBlack;
    @ApiModelProperty(value="短信黑名单")
    private List<String> smsBlack;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRemindCategory() {
        return remindCategory;
    }

    public void setRemindCategory(Integer remindCategory) {
        this.remindCategory = remindCategory;
    }

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
    }

    public String getRemindObject() {
        return remindObject;
    }

    public void setRemindObject(String remindObject) {
        this.remindObject = remindObject;
    }

    public List<String> getRemindWays() {
        return remindWays;
    }

    public void setRemindWays(List<String> remindWays) {
        this.remindWays = remindWays;
    }

    public Integer getSendFrequency() {
        return sendFrequency;
    }

    public void setSendFrequency(Integer sendFrequency) {
        this.sendFrequency = sendFrequency;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Integer getIsWeek() {
        return isWeek;
    }

    public void setIsWeek(Integer isWeek) {
        this.isWeek = isWeek;
    }

    public String getPollHourMin() {
        return pollHourMin;
    }

    public void setPollHourMin(String pollHourMin) {
        this.pollHourMin = pollHourMin;
    }

    public Integer getPollMonth() {
        return pollMonth;
    }

    public void setPollMonth(Integer pollMonth) {
        this.pollMonth = pollMonth;
    }

    public Integer getPollMonthday() {
        return pollMonthday;
    }

    public void setPollMonthday(Integer pollMonthday) {
        this.pollMonthday = pollMonthday;
    }

    public Integer getPollWeek() {
        return pollWeek;
    }

    public void setPollWeek(Integer pollWeek) {
        this.pollWeek = pollWeek;
    }

    public Integer getPollWeekday() {
        return pollWeekday;
    }

    public void setPollWeekday(Integer pollWeekday) {
        this.pollWeekday = pollWeekday;
    }

    public List<String> getEmailBlack() {
        return emailBlack;
    }

    public void setEmailBlack(List<String> emailBlack) {
        this.emailBlack = emailBlack;
    }

    public List<String> getSmsBlack() {
        return smsBlack;
    }

    public void setSmsBlack(List<String> smsBlack) {
        this.smsBlack = smsBlack;
    }
}
