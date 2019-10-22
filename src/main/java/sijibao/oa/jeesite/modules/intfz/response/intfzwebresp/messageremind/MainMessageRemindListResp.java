package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.messageremind;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 提醒管理相关列表
 * @author: xgx
 * @create: 2019-09-04 15:01
 **/
@ApiModel(value="提醒管理--列表返回对象")
public class MainMessageRemindListResp implements Serializable {

    private static final long serialVersionUID = -2096594594273758055L;

    @ApiModelProperty(value="主键id")
    private String id;
    @ApiModelProperty(value="提醒管理分类下提醒类型标识：0-报销，1-接待，2-出差，3-合同，4-协作，5-立项")
    private Integer remindType;
    @ApiModelProperty(value="提醒的对象")
    private String remindObject;
    @ApiModelProperty(value="提醒的方式：0-邮件，1-短信")
    private List<String> remindWays;
    @ApiModelProperty(value="发送频率：0-按天，1-按星期，2-按月，3-一次，4-即使发送")
    private Integer sendFrequency;
    @ApiModelProperty(value="最新修改时间")
    private Long updateTime;
    @ApiModelProperty(value="最新修改人")
    private String updateBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getRemindType() {
        return remindType;
    }

    public void setRemindType(Integer remindType) {
        this.remindType = remindType;
    }

    public Integer getSendFrequency() {
        return sendFrequency;
    }

    public void setSendFrequency(Integer sendFrequency) {
        this.sendFrequency = sendFrequency;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
