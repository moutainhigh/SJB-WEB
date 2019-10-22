/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.entity;

import com.sijibao.oa.common.persistence.DataEntity;

/**
 * 单表生成Entity
 * 
 * @author zlh
 */
public class CustLinkmanHis extends DataEntity<CustLinkmanHis> {

    public static final String CUST_LINKMAN_HIS_TYPE_INSERT = "1";
    public static final String CUST_LINKMAN_HIS_TYPE_UPDATE = "2";
    public static final String CUST_LINKMAN_HIS_TYPE_DELETE = "3";
    private static final long serialVersionUID = 1L;
    private String linkmanId; // 原来的联系人id
    private String mainCustId; // 主客户id
    private String mainCustName; // 主客户名称
    private String linkmanName; // 联系人姓名
    private String linkmanPhone; // 联系人电话
    private String linkmanPost; // 联系人职位
    private String linkmanMail; // 联系人邮箱
    private String operateOfficeId; // 操作人部门id
    private String operateOfficeName; // 操作人部门名称

    public String getMainCustId() {
        return mainCustId;
    }

    public void setMainCustId(String mainCustId) {
        this.mainCustId = mainCustId;
    }

    public String getMainCustName() {
        return mainCustName;
    }

    public void setMainCustName(String mainCustName) {
        this.mainCustName = mainCustName;
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

    public String getLinkmanMail() {
        return linkmanMail;
    }

    public void setLinkmanMail(String linkmanMail) {
        this.linkmanMail = linkmanMail;
    }

    public String getOperateOfficeId() {
        return operateOfficeId;
    }

    public void setOperateOfficeId(String operateOfficeId) {
        this.operateOfficeId = operateOfficeId;
    }

    public String getOperateOfficeName() {
        return operateOfficeName;
    }

    public void setOperateOfficeName(String operateOfficeName) {
        this.operateOfficeName = operateOfficeName;
    }

    public String getLinkmanId() {
        return linkmanId;
    }

    public void setLinkmanId(String linkmanId) {
        this.linkmanId = linkmanId;
    }

    @Override
    public String toString() {
        return "CustLinkmanHis{" + "linkmanId='" + linkmanId + '\'' + ", mainCustId='" + mainCustId + '\''
                + ", mainCustName='" + mainCustName + '\'' + ", linkmanName='" + linkmanName + '\'' + ", linkmanPhone='"
                + linkmanPhone + '\'' + ", linkmanPost='" + linkmanPost + '\'' + ", linkmanMail='" + linkmanMail + '\''
                + ", operateOfficeId='" + operateOfficeId + '\'' + ", operateOfficeName='" + operateOfficeName + '\''
                + '}';
    }
}