package sijibao.oa.jeesite.modules.intfz.request.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户联系人对象
 * 
 * @author wanxb
 */
@ApiModel(value = "客户联系人对象")
public class CustLinkmanReq implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "联系人Id")
    private String id; // 联系人Id
    @ApiModelProperty(value = "联系人姓名")
    private String linkmanName; // 联系人姓名
    @ApiModelProperty(value = "联系人电话")
    private String linkmanPhone; // 联系人电话
    @ApiModelProperty(value = "联系人邮箱")
    private String linkmanMail; // 联系人邮箱
    @ApiModelProperty(value = "联系人职位")
    private String linkmanPost; // 联系人职位
    @ApiModelProperty(value = "合同联系人：0否，1是")
    private String isContractLinkman;// 合同联系人：0否，1是
    @ApiModelProperty(value = "备注")
    private String remarks; // 备注

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

    public String getIsContractLinkman() {
        return isContractLinkman;
    }

    public void setIsContractLinkman(String isContractLinkman) {
        this.isContractLinkman = isContractLinkman;
    }

    public String getLinkmanMail() {
        return linkmanMail;
    }

    public void setLinkmanMail(String linkmanMail) {
        this.linkmanMail = linkmanMail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustLinkmanReq{" + "id='" + id + '\'' + ", linkmanName='" + linkmanName + '\'' + ", linkmanPhone='"
                + linkmanPhone + '\'' + ", linkmanMail='" + linkmanMail + '\'' + ", linkmanPost='" + linkmanPost + '\''
                + ", isContractLinkman='" + isContractLinkman + '\'' + ", remarks='" + remarks + '\'' + '}';
    }
}
