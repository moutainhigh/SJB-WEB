package sijibao.oa.jeesite.modules.intfz.request.innertask;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class InnerTaskHandleModifyReq {

    @ApiModelProperty(value = "任务ID",required = true)
    private String id;
    @ApiModelProperty(value = "所属模块ID")
    private String moduleId;
    @ApiModelProperty(value = "所属页面")
    private String page;
    @ApiModelProperty(value = "任务描述")
    private String description;
    @ApiModelProperty(value = "负责人ID")
    private String principalId;
    @ApiModelProperty(value = "优先级，0高，1中，2低")
    private int priority;
    @ApiModelProperty(value = "状态，0未完成，1已完成")
    private int status;
    @ApiModelProperty(value = "计划开始时间")
    private String planBeginTime;
    @ApiModelProperty(value = "计划结束时间")
    private String planEndTime;
    @ApiModelProperty(value = "更新人ID")
    private String updateBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getPlanBeginTime() {
        return planBeginTime;
    }

    public void setPlanBeginTime(String planBeginTime) {
        this.planBeginTime = planBeginTime;
    }

    public String getPlanEndTime() {
        return planEndTime;
    }

    public void setPlanEndTime(String planEndTime) {
        this.planEndTime = planEndTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}