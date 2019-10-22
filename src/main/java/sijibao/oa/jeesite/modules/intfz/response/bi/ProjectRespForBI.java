package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "项目信息表oa_project_info（全字段）")
public class ProjectRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id; //主键

    @ApiModelProperty(value = "项目编号")
    private String projectCode; //项目编号

    @ApiModelProperty(value = "项目名称")
    private String projectName; //项目名称

    @ApiModelProperty(value = "项目类型0公司项目1市场项目")
    private String projectType; //项目类型0公司项目1市场项目

    @ApiModelProperty(value = "项目归属部门ID")
    private String officeId; //项目归属部门ID

    @ApiModelProperty(value = "项目归属部门名称")
    private String officeName; //项目归属部门名称

    @ApiModelProperty(value = "客户ID")
    private String custId; //客户ID

    @ApiModelProperty(value = "客户名称")
    private String custName; //客户名称

    @ApiModelProperty(value = "区域ID")
    private String areaId; //区域ID

    @ApiModelProperty(value = "区域名称")
    private String areaName; //区域名称

    @ApiModelProperty(value = "项目状态")
    private String projectState; //项目状态

    @ApiModelProperty(value = "上线日期")
    private Date onLineDate; //上线日期，项目状态为上线时必填，格式yyyy-MM-dd

    @ApiModelProperty(value = "项目负责人")
    private String projectLeader; //项目负责人

    @ApiModelProperty(value = "项目负责人ID")
    private String projectLeaderId; //项目负责人ID

    @ApiModelProperty(value = "市场负责人")
    private String marketLeader; //市场负责人

    @ApiModelProperty(value = "市场负责人ID")
    private String marketLeaderId; //市场负责人ID

    @ApiModelProperty(value = "实施负责人")
    private String impleLeader; //实施负责人

    @ApiModelProperty(value = "实施负责人ID")
    private String impleLeaderId; //实施负责人ID

    @ApiModelProperty(value = "创建时间")
    private Date createTime; //创建时间

    @ApiModelProperty(value = "创建人")
    private String createBy; //创建人

    @ApiModelProperty(value = "更新时间")
    private Date updateTime; //更新时间

    @ApiModelProperty(value = "更新人")
    private String updateBy; //更新人

    @ApiModelProperty(value = "删除标记")
    private String delFlag; //删除标记

    @ApiModelProperty(value = "备注")
    private String remarks; //备注

    @ApiModelProperty(value = "项目名称首字母")
    private String pnameInitials; //项目名称首字母

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public Date getOnLineDate() {
        return onLineDate;
    }

    public void setOnLineDate(Date onLineDate) {
        this.onLineDate = onLineDate;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getProjectLeaderId() {
        return projectLeaderId;
    }

    public void setProjectLeaderId(String projectLeaderId) {
        this.projectLeaderId = projectLeaderId;
    }

    public String getMarketLeader() {
        return marketLeader;
    }

    public void setMarketLeader(String marketLeader) {
        this.marketLeader = marketLeader;
    }

    public String getMarketLeaderId() {
        return marketLeaderId;
    }

    public void setMarketLeaderId(String marketLeaderId) {
        this.marketLeaderId = marketLeaderId;
    }

    public String getImpleLeader() {
        return impleLeader;
    }

    public void setImpleLeader(String impleLeader) {
        this.impleLeader = impleLeader;
    }

    public String getImpleLeaderId() {
        return impleLeaderId;
    }

    public void setImpleLeaderId(String impleLeaderId) {
        this.impleLeaderId = impleLeaderId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getPnameInitials() {
        return pnameInitials;
    }

    public void setPnameInitials(String pnameInitials) {
        this.pnameInitials = pnameInitials;
    }

}
