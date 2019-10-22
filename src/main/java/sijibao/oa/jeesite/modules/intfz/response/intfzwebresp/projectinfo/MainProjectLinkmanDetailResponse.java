package sijibao.oa.jeesite.modules.intfz.response.intfzwebresp.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目管理--明细返回对象
 *
 * @author wuys
 */
@ApiModel(value = "项目管理--联系人明细返回对象")
public class MainProjectLinkmanDetailResponse implements Serializable {


    private static final long serialVersionUID = -1239764389977881222L;

    @ApiModelProperty(value = "联系人ID")
    private String linkmanId;//联系人ID
    @ApiModelProperty(value = "联系人姓名")
    private String linkmanName;//联系人姓名
    @ApiModelProperty(value = "联系人电话")
    private String linkmanPhone;//联系人电话
    @ApiModelProperty(value = "联系人职位")
    private String linkmanPost;//联系人职位
    @ApiModelProperty(value = "备注")
    private String remarks;//备注

    public String getLinkmanId() {
        return linkmanId;
    }

    public void setLinkmanId(String linkmanId) {
        this.linkmanId = linkmanId;
    }

    public String getLinkmanName() {
        return linkmanName;
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName;
    }

    public String getLinkmanPhone() {
        return linkmanPhone;
    }

    public void setLinkmanPhone(String linkmanPhone) {
        this.linkmanPhone = linkmanPhone;
    }

    public String getLinkmanPost() {
        return linkmanPost;
    }

    public void setLinkmanPost(String linkmanPost) {
        this.linkmanPost = linkmanPost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "MainProjectLinkmanDetailResponse{" +
                "linkmanId='" + linkmanId + '\'' +
                ", linkmanName='" + linkmanName + '\'' +
                ", linkmanPhone='" + linkmanPhone + '\'' +
                ", linkmanPost='" + linkmanPost + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
