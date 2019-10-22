package sijibao.oa.jeesite.modules.intfz.request.intfzwebreq.projectinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description: 项目联系人
 * @author: xgx
 * @create: 2019-08-20 14:10
 **/
@ApiModel(value="项目管理--联系人信息")
public class MainProjectContact implements Serializable {

    private static final long serialVersionUID = 2360693403921428619L;
    @ApiModelProperty(value="联系人ID")
    private String linkmanId;
    @ApiModelProperty(value = "项目id")
    private String projectId;
    @ApiModelProperty(value="联系人姓名")
    private String linkmanName;
    @ApiModelProperty(value="联系人电话")
    private String linkmanPhone;
    @ApiModelProperty(value="联系人职位")
    private String linkmanPost;
    @ApiModelProperty(value="备注")
    private String remarks;

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

    public String getLinkmanId() {
        return linkmanId;
    }

    public void setLinkmanId(String linkmanId) {
        this.linkmanId = linkmanId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "MainProjectContact{" +
                "linkmanId='" + linkmanId + '\'' +
                ", projectId='" + projectId + '\'' +
                ", linkmanName='" + linkmanName + '\'' +
                ", linkmanPhone='" + linkmanPhone + '\'' +
                ", linkmanPost='" + linkmanPost + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
