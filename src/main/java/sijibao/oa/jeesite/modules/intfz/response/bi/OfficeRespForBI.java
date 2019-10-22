package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "机构表sys_office（全字段）")
public class OfficeRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id; // 编号

    @ApiModelProperty(value = "父级编号")
    private String parentId; // 父级编号

    @ApiModelProperty(value = "所有父级编号")
    private String parentIds; // 所有父级编号

    @ApiModelProperty(value = "机构名称")
    private String name;    // 机构名称

    @ApiModelProperty(value = "排序")
    private BigDecimal sort;        // 排序

    @ApiModelProperty(value = "归属区域")
    private String areaId;        // 归属区域

    @ApiModelProperty(value = "机构编码")
    private String code;    // 机构编码

    @ApiModelProperty(value = "机构类型")
    private String type;    // 机构类型

    @ApiModelProperty(value = "机构等级")
    private String grade;    // 机构等级

    @ApiModelProperty(value = "联系地址")
    private String address = ""; // 联系地址

    @ApiModelProperty(value = "邮政编码")
    private String zipCode = ""; // 邮政编码

    @ApiModelProperty(value = "负责人")
    private String master = "";    // 负责人

    @ApiModelProperty(value = "电话")
    private String phone = "";    // 电话

    @ApiModelProperty(value = "传真")
    private String fax = "";    // 传真

    @ApiModelProperty(value = "邮箱")
    private String email = "";    // 邮箱

    @ApiModelProperty(value = "是否可用")
    private String useable = "";//是否可用

    @ApiModelProperty(value = "主负责人（单个）")
    private String primaryPerson = "";//主负责人（单个）

    @ApiModelProperty(value = "副负责人（多个，以英文逗号分隔）")
    private String deputyPerson;//副负责人（多个）

    @ApiModelProperty(value = "创建者")
    private String createBy;//创建者

    @ApiModelProperty(value = "创建时间")
    private Date createDate;//创建时间

    @ApiModelProperty(value = "更新者")
    private String updateBy;//更新者

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;//更新时间

    @ApiModelProperty(value = "备注信息")
    private String remarks = "";//备注信息

    @ApiModelProperty(value = "删除标记")
    private String delFlag;//删除标记

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSort() {
        return sort;
    }

    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUseable() {
        return useable;
    }

    public void setUseable(String useable) {
        this.useable = useable;
    }

    public String getPrimaryPerson() {
        return primaryPerson;
    }

    public void setPrimaryPerson(String primaryPerson) {
        this.primaryPerson = primaryPerson;
    }

    public String getDeputyPerson() {
        return deputyPerson;
    }

    public void setDeputyPerson(String deputyPerson) {
        this.deputyPerson = deputyPerson;
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
