package sijibao.oa.jeesite.modules.intfz.response.innerTask;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel(description = "公司内部任务列表返回对象")
public class InnerTaskHandleResp {

    @ApiModelProperty(value = "任务ID")
    private String id;
    @ApiModelProperty(value = "所属页面")
    private String page;
    @ApiModelProperty(value = "任务描述")
    private String description;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "所属模块名称")
    private String moduleName;
    @ApiModelProperty(value = "负责人姓名")
    private String principalName;
    @ApiModelProperty(value = "优先级")
    private String priority;
    @ApiModelProperty(value = "计划开始时间")
    private String planBeginTime;
    @ApiModelProperty(value = "计划结束时间")
    private String planEndTime;
    @ApiModelProperty(value = "实际开始时间")
    private String actualBeginTime;
    @ApiModelProperty(value = "实际结束时间")
    private String actualEndTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getActualBeginTime() {
        return actualBeginTime;
    }

    public void setActualBeginTime(String actualBeginTime) {
        this.actualBeginTime = actualBeginTime;
    }

    public String getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        this.actualEndTime = actualEndTime;
    }
}
