package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "字典表sys_dict（全字段）")
public class DictRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;           //编号

    @ApiModelProperty(value = "数据值")
    private String value;        //数据值

    @ApiModelProperty(value = "标签名")
    private String label;        //标签名

    @ApiModelProperty(value = "类型")
    private String type;         //类型

    @ApiModelProperty(value = "描述")
    private String description;  //描述

    @ApiModelProperty(value = "排序（升序）")
    private BigDecimal sort;     //排序（升序）

    @ApiModelProperty(value = "父级编号")
    private String parentId = "";    //父级编号

    @ApiModelProperty(value = "创建者")
    private String createBy;    //创建者

    @ApiModelProperty(value = "创建时间")
    private Date createDate;    //创建时间

    @ApiModelProperty(value = "更新者")
    private String updateBy;    //更新者

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;    //更新时间

    @ApiModelProperty(value = "备注信息")
    private String remarks = "";      //备注信息

    @ApiModelProperty(value = "删除标记")
    private String delFlag;     //删除标记

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
}
