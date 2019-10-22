package sijibao.oa.jeesite.modules.intfz.request.asset;

import java.util.Arrays;

import io.swagger.annotations.ApiModelProperty;

/**
 * 固定资产退库Entity
 * @author huangkai
 * @version 2019-03-08
 */
public class AssetOutReq {
    @ApiModelProperty(value="退库时间")
    private long outTime;   //退库时间
    @ApiModelProperty(value="放置地ID")
    private String assetPlaceId;  //放置地ID
    @ApiModelProperty(value="备注")
    private String remarks;        //备注
    @ApiModelProperty(value="固定资产编号")
    private String[] assetCode;   //固定资产编号


    @Override
    public String toString() {
        return "AssetOutReq{" +
                "outTime=" + outTime +
                ", assetPlaceId='" + assetPlaceId + '\'' +
                ", remarks='" + remarks + '\'' +
                ", assetCode=" + Arrays.toString(assetCode) +
                '}';
    }


    public long getOutTime() {
        return outTime;
    }

    public void setOutTime(long outTime) {
        this.outTime = outTime;
    }

    public String getAssetPlaceId() {
        return assetPlaceId;
    }

    public void setAssetPlaceId(String assetPlaceId) {
        this.assetPlaceId = assetPlaceId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String[] getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String[] assetCode) {
        this.assetCode = assetCode;
    }
}
