package sijibao.oa.jeesite.modules.intfz.response.asset;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel(value = "资产列表（分页查询）")
public class AssetQueryPageResp implements Serializable {

    @ApiModelProperty(value = "唯一ID")
    private String id;
    @ApiModelProperty(value = "资产编号")
    private String code;
    @ApiModelProperty(value = "资产名称")
    private String name;
    @ApiModelProperty(value = "资产类别（名称）")
    private String assetTypeName;
    @ApiModelProperty(value = "计量单位")
    private String unit;
    @ApiModelProperty(value = "所属公司")
    private String company;
    @ApiModelProperty(value = "放置地（名称）")
    private String assetPlaceName;
    @ApiModelProperty(value = "入库时间")
    private long entryTime;
    @ApiModelProperty(value = "品牌")
    private String brand;
    @ApiModelProperty(value = "规格型号")
    private String specificationType;
    @ApiModelProperty(value = "序列号")
    private String serialNo;
    @ApiModelProperty(value = "供应商（名称）")
    private String assetSupplierName;
    @ApiModelProperty(value = "供应商联系方式")
    private String supplierContactWay;
    @ApiModelProperty(value = "来源")
    private String source;
    @ApiModelProperty(value = "金额")
    private BigDecimal money;
    @ApiModelProperty(value = "租用/购入时间")
    private long buyTime;
    @ApiModelProperty(value = "租赁到期时间")
    private long dueTime;
    @ApiModelProperty(value = "保修起始时间")
    private long guaranteeBeginTime;
    @ApiModelProperty(value = "过保时间")
    private long guaranteeEndTime;
    @ApiModelProperty(value = "资产状态")
    private String status;
    @ApiModelProperty(value = "使用部门")
    private String usingOffice;
    @ApiModelProperty(value = "使用人")
    private String usingPerson;
    @ApiModelProperty(value = "使用工位")
    private String usingWorkplace;
    @ApiModelProperty(value = "备注")
    private String remarks;
    @ApiModelProperty(value = "领用日期")
    private long pickDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSpecificationType() {
        return specificationType;
    }

    public void setSpecificationType(String specificationType) {
        this.specificationType = specificationType;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getSupplierContactWay() {
        return supplierContactWay;
    }

    public void setSupplierContactWay(String supplierContactWay) {
        this.supplierContactWay = supplierContactWay;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(long buyTime) {
        this.buyTime = buyTime;
    }

    public long getDueTime() {
        return dueTime;
    }

    public void setDueTime(long dueTime) {
        this.dueTime = dueTime;
    }

    public long getGuaranteeBeginTime() {
        return guaranteeBeginTime;
    }

    public void setGuaranteeBeginTime(long guaranteeBeginTime) {
        this.guaranteeBeginTime = guaranteeBeginTime;
    }

    public long getGuaranteeEndTime() {
        return guaranteeEndTime;
    }

    public void setGuaranteeEndTime(long guaranteeEndTime) {
        this.guaranteeEndTime = guaranteeEndTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsingOffice() {
        return usingOffice;
    }

    public void setUsingOffice(String usingOffice) {
        this.usingOffice = usingOffice;
    }

    public String getUsingPerson() {
        return usingPerson;
    }

    public void setUsingPerson(String usingPerson) {
        this.usingPerson = usingPerson;
    }

    public String getUsingWorkplace() {
        return usingWorkplace;
    }

    public void setUsingWorkplace(String usingWorkplace) {
        this.usingWorkplace = usingWorkplace;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getPickDate() {
        return pickDate;
    }

    public void setPickDate(long pickDate) {
        this.pickDate = pickDate;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAssetPlaceName() {
        return assetPlaceName;
    }

    public void setAssetPlaceName(String assetPlaceName) {
        this.assetPlaceName = assetPlaceName;
    }

    public String getAssetSupplierName() {
        return assetSupplierName;
    }

    public void setAssetSupplierName(String assetSupplierName) {
        this.assetSupplierName = assetSupplierName;
    }
}
