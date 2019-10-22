package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "实施员工状态标记表oa_employee_statusrecord（全字段）")
public class EmployStatusRecordRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id; //主键

    @ApiModelProperty(value = "用户ID")
    private String userId;//用户ID

    @ApiModelProperty(value = "人员状态")
    private String status;//人员状态

    @ApiModelProperty(value = "人员动作")
    private String userAction; //人员动作

    @ApiModelProperty(value = "项目ID")
    private String projectId; //项目ID

    @ApiModelProperty(value = "项目节点ID")
    private String projectNodeId;//项目节点ID

    @ApiModelProperty(value = "基地ID")
    private String baseId;//基地ID

    @ApiModelProperty(value = "创建时间")
    private Date createTime; //创建时间

    @ApiModelProperty(value = "创建人")
    private String createBy; //创建人

    @ApiModelProperty(value = "更新时间")
    private Date updateTime; //更新时间

    @ApiModelProperty(value = "更新人")
    private String updateBy; //更新人

    @ApiModelProperty(value = "删除标记")
    private String delFlag;//删除标记

    @ApiModelProperty(value = "备注")
    private String remarks = "";//备注

    @ApiModelProperty(value = "月报表处理状态 0:未处理，1：已处理")
    private String reportStatus = "";//月报表处理状态 0:未处理，1：已处理

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectNodeId() {
        return projectNodeId;
    }

    public void setProjectNodeId(String projectNodeId) {
        this.projectNodeId = projectNodeId;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

}
