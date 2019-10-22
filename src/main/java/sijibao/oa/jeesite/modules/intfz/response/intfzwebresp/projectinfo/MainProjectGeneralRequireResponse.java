package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 项目管理--明细返回对象
 * @author: xgx
 * @create: 2019-08-20 10:27
 **/
@ApiModel(value="项目管理--一般要求明细")
public class MainProjectGeneralRequireResponse implements Serializable {


    private static final long serialVersionUID = -2623128304726836742L;
    @ApiModelProperty(value="开票方式：1一票，2两票")
    private String invoiceMode;
    @ApiModelProperty(value="开票方式名称")
    private String invoiceModeName;
    @ApiModelProperty(value="项目托管：1是，0否")
    private String projectTrusteeshipt;
    @ApiModelProperty(value="项目托管名称")
    private String projectTrusteeshiptName;
    @ApiModelProperty(value="托管渠道：1华夏，2兴业")
    private String trusteeshiptChannel;
    @ApiModelProperty(value="托管渠道名称")
    private String trusteeshiptChannelName;

    public String getInvoiceMode() {
        return invoiceMode;
    }

    public void setInvoiceMode(String invoiceMode) {
        this.invoiceMode = invoiceMode;
        if("1".equals(this.invoiceMode)){
            this.invoiceModeName="一票";
        }else if("2".equals(this.invoiceMode)){
            this.invoiceModeName="两票";
        }else{
            this.invoiceModeName="";
        }
    }

    public String getProjectTrusteeshipt() {
        return projectTrusteeshipt;
    }

    public void setProjectTrusteeshipt(String projectTrusteeshipt) {
        this.projectTrusteeshipt = projectTrusteeshipt;
        this.projectTrusteeshiptName="1".equals(this.projectTrusteeshipt)?"是":"否";
    }

    public String getTrusteeshiptChannel() {
        return trusteeshiptChannel;
    }

    public void setTrusteeshiptChannel(String trusteeshiptChannel) {
        this.trusteeshiptChannel = trusteeshiptChannel;
        if("1".equals(this.trusteeshiptChannel)){
            this.trusteeshiptChannelName="华夏";
        }else if("2".equals(this.trusteeshiptChannel)){
            this.trusteeshiptChannelName="兴业";
        }else{
            this.trusteeshiptChannelName="";
        }
    }

    public String getInvoiceModeName() {
        return invoiceModeName;
    }

    public void setInvoiceModeName(String invoiceModeName) {
        this.invoiceModeName = invoiceModeName;
    }

    public String getProjectTrusteeshiptName() {
        return projectTrusteeshiptName;
    }

    public void setProjectTrusteeshiptName(String projectTrusteeshiptName) {
        this.projectTrusteeshiptName = projectTrusteeshiptName;
    }

    public String getTrusteeshiptChannelName() {
        return trusteeshiptChannelName;
    }

    public void setTrusteeshiptChannelName(String trusteeshiptChannelName) {
        this.trusteeshiptChannelName = trusteeshiptChannelName;
    }
}
