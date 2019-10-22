package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "岗位表数据sys_post（全字段）")
public class PostRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;//主键

    @ApiModelProperty(value = "岗位编号")
    private String postCode;//岗位编号

    @ApiModelProperty(value = "岗位名称")
    private String postName;//岗位名称

    @ApiModelProperty(value = "创建者")
    private String createBy;//创建者

    @ApiModelProperty(value = "创建时间")
    private Date createDate;//创建时间

    @ApiModelProperty(value = "更新者")
    private String updateBy;//更新者

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;//更新时间

    @ApiModelProperty(value = "岗位说明")
    private String remarks = "";//岗位说明

    @ApiModelProperty(value = "删除标记")
    private String delFlag;//删除标记

    @ApiModelProperty(value = "上级岗位id")
    private String parentId = "";//上级岗位id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
