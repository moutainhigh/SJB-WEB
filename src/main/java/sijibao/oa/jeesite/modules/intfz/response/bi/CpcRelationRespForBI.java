package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询客户/项目/企业关联关系，响应数据
 */
@ApiModel
public class CpcRelationRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目ID")
    private String projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "客户ID")
    private String custId;

    @ApiModelProperty(value = "客户名称")
    private String custName;

    @ApiModelProperty(value = "企业名称")
    private String companyName;

    @ApiModelProperty(value = "企业编码")
    private String companyCode;

    @ApiModelProperty(value = "企业持有人编码")
    private String holderCode;

    @ApiModelProperty(value = "市场负责人ID")
    private String marketLeaderId;

    @ApiModelProperty(value = "市场负责人")
    private String marketLeader;

    @ApiModelProperty(value = "更新时间")
    private Date relationChangeTime;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getHolderCode() {
        return holderCode;
    }

    public void setHolderCode(String holderCode) {
        this.holderCode = holderCode;
    }

    public String getMarketLeaderId() {
        return marketLeaderId;
    }

    public void setMarketLeaderId(String marketLeaderId) {
        this.marketLeaderId = marketLeaderId;
    }

    public String getMarketLeader() {
        return marketLeader;
    }

    public void setMarketLeader(String marketLeader) {
        this.marketLeader = marketLeader;
    }

    public Date getRelationChangeTime() {
        return relationChangeTime;
    }

    public void setRelationChangeTime(Date relationChangeTime) {
        this.relationChangeTime = relationChangeTime;
    }
}
