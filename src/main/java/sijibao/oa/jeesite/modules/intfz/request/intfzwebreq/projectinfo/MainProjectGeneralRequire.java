package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 一般要求
 * @author: xgx
 * @create: 2019-08-20 10:27
 **/
@ApiModel(value="项目管理--一般要求")
public class MainProjectGeneralRequire implements Serializable {

    private static final long serialVersionUID = 6356686787288683956L;
    @ApiModelProperty(value="开票方式：1一票，2两票")
    private Integer invoiceMode;
    @ApiModelProperty(value="项目托管：1是，0否")
    private Integer projectTrusteeshipt;
    @ApiModelProperty(value="托管渠道：1华夏，2兴业")
    private Integer trusteeshiptChannel;

    public Integer getInvoiceMode() {
        return invoiceMode;
    }

    public void setInvoiceMode(Integer invoiceMode) {
        this.invoiceMode = invoiceMode;
    }

    public Integer getProjectTrusteeshipt() {
        return projectTrusteeshipt;
    }

    public void setProjectTrusteeshipt(Integer projectTrusteeshipt) {
        this.projectTrusteeshipt = projectTrusteeshipt;
    }

    public Integer getTrusteeshiptChannel() {
        return trusteeshiptChannel;
    }

    public void setTrusteeshiptChannel(Integer trusteeshiptChannel) {
        this.trusteeshiptChannel = trusteeshiptChannel;
    }
}
