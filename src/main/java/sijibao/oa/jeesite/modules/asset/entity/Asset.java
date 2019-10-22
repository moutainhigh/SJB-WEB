package sijibao.oa.jeesite.modules.asset.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Asset {
    private String id;

    private String code;

    private String name;

    private String assetTypeId;

    private String company;

    private String assetPlaceId;

    private Date entryTime;

    private String brand;

    private String specificationType;

    private String serialNo;

    private String assetSupplierId;

    private String source;

    private BigDecimal money;

    private Date buyTime;

    private Date dueTime;

    private Date guaranteeBeginTime;


    private Date guaranteeEndTime;

    private String status;

    private String usingOffice;

    private String usingPerson;

    private String usingWorkplace;


    private String remarks;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    private String delFlag;

    private Date doneDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAssetTypeId() {
        return assetTypeId;
    }

    public void setAssetTypeId(String assetTypeId) {
        this.assetTypeId = assetTypeId == null ? null : assetTypeId.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getAssetPlaceId() {
        return assetPlaceId;
    }

    public void setAssetPlaceId(String assetPlaceId) {
        this.assetPlaceId = assetPlaceId == null ? null : assetPlaceId.trim();
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getSpecificationType() {
        return specificationType;
    }

    public void setSpecificationType(String specificationType) {
        this.specificationType = specificationType == null ? null : specificationType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUsingOffice() {
        return usingOffice;
    }

    public void setUsingOffice(String usingOffice) {
        this.usingOffice = usingOffice == null ? null : usingOffice.trim();
    }

    public String getUsingPerson() {
        return usingPerson;
    }

    public void setUsingPerson(String usingPerson) {
        this.usingPerson = usingPerson == null ? null : usingPerson.trim();
    }

    public String getUsingWorkplace() {
        return usingWorkplace;
    }

    public void setUsingWorkplace(String usingWorkplace) {
        this.usingWorkplace = usingWorkplace == null ? null : usingWorkplace.trim();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getAssetSupplierId() {
        return assetSupplierId;
    }

    public void setAssetSupplierId(String assetSupplierId) {
        this.assetSupplierId = assetSupplierId == null ? null : assetSupplierId.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }


    public Date getGuaranteeBeginTime() {
        return guaranteeBeginTime;
    }

    public void setGuaranteeBeginTime(Date guaranteeBeginTime) {
        this.guaranteeBeginTime = guaranteeBeginTime;
    }

    public Date getGuaranteeEndTime() {
        return guaranteeEndTime;
    }

    public void setGuaranteeEndTime(Date guaranteeEndTime) {
        this.guaranteeEndTime = guaranteeEndTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
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
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }
}