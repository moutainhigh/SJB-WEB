package sijibao.oa.jeesite.modules.intfz.request.asset;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel
public class AssetPickOrTransferReq implements Serializable {

    @ApiModelProperty(value = "资产ID")
    private List<String> assetId;
    @ApiModelProperty(value = "使用部门")
    private String usingOffice;
    @ApiModelProperty(value = "使用人")
    private String usingPerson;
    @ApiModelProperty(value = "工位")
    private String usingWorkplace;
    @ApiModelProperty(value = "放置地")
    private String assetPlaceId;
    @ApiModelProperty(value = "领用/转移日期")
    private long pickOrTransferDate;
    @ApiModelProperty(value = "备注")
    private String remarks;

    public List<String> getAssetId() {
        return assetId;
    }

    public void setAssetId(List<String> assetId) {
        this.assetId = assetId;
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

    public String getAssetPlaceId() {
        return assetPlaceId;
    }

    public void setAssetPlaceId(String assetPlaceId) {
        this.assetPlaceId = assetPlaceId;
    }

    public long getPickOrTransferDate() {
        return pickOrTransferDate;
    }

    public void setPickOrTransferDate(long pickOrTransferDate) {
        this.pickOrTransferDate = pickOrTransferDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
