package sijibao.oa.jeesite.modules.intfz.request.asset;

import java.util.Arrays;

import io.swagger.annotations.ApiModelProperty;

/**
 * 固定资产退库Entity
 * @author huangkai
 * @version 2019-03-08
 */
public class AssetWriteOffReq {
    @ApiModelProperty(value="清理时间")
    private long cleanTime;     //清理时间
    @ApiModelProperty(value="清理理由")
    private String cleanType;  //清理理由
    @ApiModelProperty(value="清理原因")
    private String cleanReason;//清理原因
    @ApiModelProperty(value="备注")
    private String remarks;        //备注
    @ApiModelProperty(value="固定资产编号")
    private String[] assetCode;   //固定资产编号


    @Override
    public String toString() {
        return "AssetWriteOffReq{" +
                "cleanTime=" + cleanTime +
                ", cleanType='" + cleanType + '\'' +
                ", cleanReason='" + cleanReason + '\'' +
                ", remarks='" + remarks + '\'' +
                ", assetCode=" + Arrays.toString(assetCode) +
                '}';
    }

    public long getCleanTime() {
        return cleanTime;
    }

    public void setCleanTime(long cleanTime) {
        this.cleanTime = cleanTime;
    }

    public String getCleanType() {
        return cleanType;
    }

    public void setCleanType(String cleanType) {
        this.cleanType = cleanType;
    }

    public String getCleanReason() {
        return cleanReason;
    }

    public void setCleanReason(String cleanReason) {
        this.cleanReason = cleanReason;
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
