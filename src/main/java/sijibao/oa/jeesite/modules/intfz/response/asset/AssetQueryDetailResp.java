package sijibao.oa.jeesite.modules.intfz.response.asset;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
public class AssetQueryDetailResp implements Serializable {

    @ApiModelProperty(value = "主键")
    private String id;

    @NotNull
    @ApiModelProperty(value = "编号", required = true)
    private String code;

    @NotNull
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @NotNull
    @ApiModelProperty(value = "类别ID", required = true)
    private List<String> assetTypeIdList;

    @ApiModelProperty(value = "所属公司")
    private String company;

    @NotNull
    @ApiModelProperty(value = "放置地ID", required = true)
    private String assetPlaceId;

    @NotNull
    @ApiModelProperty(value = "入库时间", required = true)
    private long entryTime;

    @ApiModelProperty(value = "品牌")
    private String brand;

    @ApiModelProperty(value = "规格型号")
    private String specificationType;

    @NotNull
    @ApiModelProperty(value = "序列号", required = true)
    private String serialNo;

    @ApiModelProperty(value = "供应商ID")
    private String assetSupplierId;

    @NotNull
    @ApiModelProperty(value = "来源（字典中配置）", required = true)
    private String source;

    @NotNull
    @ApiModelProperty(value = "金额", required = true)
    private BigDecimal money;

    @NotNull
    @ApiModelProperty(value = "购入/租入时间", required = true)
    private long buyTime;

    @NotNull
    @ApiModelProperty(value = "租赁到期时间", required = true)
    private long dueTime;

    @ApiModelProperty(value = "保修起始时间")
    private long guaranteeBeginTime;

    @ApiModelProperty(value = "保修截止时间")
    private long guaranteeEndTime;

    @ApiModelProperty(value = "备注")
    private String remarks;

    public AssetQueryDetailResp() {
    }

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

    public List<String> getAssetTypeIdList() {
        return assetTypeIdList;
    }

    public void setAssetTypeIdList(List<String> assetTypeIdList) {
        this.assetTypeIdList = assetTypeIdList;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAssetPlaceId() {
        return assetPlaceId;
    }

    public void setAssetPlaceId(String assetPlaceId) {
        this.assetPlaceId = assetPlaceId;
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

    public String getAssetSupplierId() {
        return assetSupplierId;
    }

    public void setAssetSupplierId(String assetSupplierId) {
        this.assetSupplierId = assetSupplierId;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
