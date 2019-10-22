package sijibao.oa.jeesite.modules.intfz.request.asset;

import java.io.Serializable;
import java.util.List;

import com.sijibao.oa.modules.intfz.request.common.PageRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Jianghao Zhang
 */
@ApiModel(value = "查询资产列表请求")
public class AssetQueryPageReq extends PageRequest implements Serializable {

    @ApiModelProperty(value = "关键字，查询资产列表使用")
    private String keyword;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "资产类别")
    private List<String> assetTypeIdList;

    @ApiModelProperty(value = "入库时间（起始）")
    private long entryTimeBegin;

    @ApiModelProperty(value = "入库时间（截止）")
    private long entryTimeEnd;

    @ApiModelProperty(value = "关键字,领用/转移/出库/核销使用")
    private String keywords;

    @ApiModelProperty(value = "使用人名称")
    private String usingPersonName;//使用人名称

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getAssetTypeIdList() {
        return assetTypeIdList;
    }

    public void setAssetTypeIdList(List<String> assetTypeIdList) {
        this.assetTypeIdList = assetTypeIdList;
    }

    public long getEntryTimeBegin() {
        return entryTimeBegin;
    }

    public void setEntryTimeBegin(long entryTimeBegin) {
        this.entryTimeBegin = entryTimeBegin;
    }

    public long getEntryTimeEnd() {
        return entryTimeEnd;
    }

    public void setEntryTimeEnd(long entryTimeEnd) {
        this.entryTimeEnd = entryTimeEnd;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getUsingPersonName() {
        return usingPersonName;
    }

    public void setUsingPersonName(String usingPersonName) {
        this.usingPersonName = usingPersonName;
    }
}
