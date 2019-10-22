package sijibao.oa.jeesite.modules.intfz.request.asset;

import io.swagger.annotations.ApiModelProperty;

/**
 * 固定资产导入导出Entity
 * @author huangkai
 * @version 2019-03-08
 */
public class AssetExportAndImportReq {
    @ApiModelProperty(value="文件路径")
    private String url;  //文件路径

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
