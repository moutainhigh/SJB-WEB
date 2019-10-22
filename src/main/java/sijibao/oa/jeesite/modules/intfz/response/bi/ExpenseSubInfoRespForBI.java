package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "费用科目信息表oa_expenses_sub_info（全字段）")
public class ExpenseSubInfoRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id; //主键

    @ApiModelProperty(value = "科目编号")
    private String subCode;//科目编号

    @ApiModelProperty(value = "父级科目编号")
    private String parSubCode;//父级科目编号

    @ApiModelProperty(value = "科目名称")
    private String subName;//科目名称

    @ApiModelProperty(value = "费用标准")
    private String expenseNormal; //费用标准

    @ApiModelProperty(value = "单位类型")
    private String unitType; //单位类型

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
    private String remarks;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getParSubCode() {
        return parSubCode;
    }

    public void setParSubCode(String parSubCode) {
        this.parSubCode = parSubCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getExpenseNormal() {
        return expenseNormal;
    }

    public void setExpenseNormal(String expenseNormal) {
        this.expenseNormal = expenseNormal;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
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
}
