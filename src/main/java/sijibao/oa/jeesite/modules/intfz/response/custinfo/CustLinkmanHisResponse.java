package sijibao.oa.jeesite.modules.intfz.response.custinfo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户联系人历史变更对象
 * 
 * @author wanxb
 */
@ApiModel(value = "客户联系人历史变更对象")
public class CustLinkmanHisResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    private String id; // id
    @ApiModelProperty(value = "联系人id")
    private String linkmanId; // 联系人id
    @ApiModelProperty(value = "主客户id")
    private String mainCustId; // 主客户id
    @ApiModelProperty(value = "主客户名称")
    private String mainCustName; // 主客户名称
    @ApiModelProperty(value = "主客户编号")
    private String mainCustCode; // 主客户编号
    @ApiModelProperty(value = "联系人姓名")
    private String linkmanName; // 联系人姓名
    @ApiModelProperty(value = "联系人电话")
    private String linkmanPhone; // 联系人电话
    @ApiModelProperty(value = "联系人职位")
    private String linkmanPost; // 联系人职位
    @ApiModelProperty(value = "操作类型：1，新增，2，修改，3，删除")
    private String operateType; // 操作类型：1，新增，2，修改，3，删除
    @ApiModelProperty(value = "联系人邮箱")
    private String linkmanMail; // 联系人邮箱
    @ApiModelProperty(value = "操作人部门id")
    private String operateOfficeId; // 操作人部门id
    @ApiModelProperty(value = "操作人部门名称")
    private String operateOfficeName; // 操作人部门名称
    @ApiModelProperty(value = "操作人")
    private String operateUserName; // 操作人
    @ApiModelProperty(value = "备注")
    private String remarks; // 备注
    @ApiModelProperty(value = "创建人")
    private String createBy;// 创建人
    @ApiModelProperty(value = "创建时间")
    private long createTime = 0l;// 创建时间
    @ApiModelProperty(value = "原联系人信息")
    private String preLinkmanRemark; // 原联系人信息
    @ApiModelProperty(value = "现联系人信息")
    private String nowLinkmanRemark; // 现联系人信息

    @ApiModelProperty(value = "原联系人姓名")
    private String preLinkmanName; // 原联系人姓名
    @ApiModelProperty(value = "原联系人电话")
    private String preLinkmanPhone; // 原联系人电话
    @ApiModelProperty(value = "原联系人职位")
    private String preLinkmanPost; // 原联系人职位
    @ApiModelProperty(value = "原联系人邮箱")
    private String preLinkmanMail; // 原联系人邮箱

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkmanId() {
        return linkmanId;
    }

    public void setLinkmanId(String linkmanId) {
        this.linkmanId = linkmanId;
    }

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

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
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

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPreLinkmanRemark() {
        return preLinkmanRemark;
    }

    public void setPreLinkmanRemark(String preLinkmanRemark) {
        this.preLinkmanRemark = preLinkmanRemark;
    }

    public String getNowLinkmanRemark() {
        return nowLinkmanRemark;
    }

    public void setNowLinkmanRemark(String nowLinkmanRemark) {
        this.nowLinkmanRemark = nowLinkmanRemark;
    }

    public String getPreLinkmanName() {
        return preLinkmanName;
    }

    public void setPreLinkmanName(String preLinkmanName) {
        this.preLinkmanName = preLinkmanName;
    }

    public String getPreLinkmanPhone() {
        return preLinkmanPhone;
    }

    public void setPreLinkmanPhone(String preLinkmanPhone) {
        this.preLinkmanPhone = preLinkmanPhone;
    }

    public String getPreLinkmanPost() {
        return preLinkmanPost;
    }

    public void setPreLinkmanPost(String preLinkmanPost) {
        this.preLinkmanPost = preLinkmanPost;
    }

    public String getPreLinkmanMail() {
        return preLinkmanMail;
    }

    public void setPreLinkmanMail(String preLinkmanMail) {
        this.preLinkmanMail = preLinkmanMail;
    }

    public String getMainCustCode() {
        return mainCustCode;
    }

    public void setMainCustCode(String mainCustCode) {
        this.mainCustCode = mainCustCode;
    }

    @Override
    public String toString() {
        return "CustLinkmanHisResponse{" + "id='" + id + '\'' + ", linkmanId='" + linkmanId + '\'' + ", mainCustId='"
                + mainCustId + '\'' + ", mainCustName='" + mainCustName + '\'' + ", mainCustCode='" + mainCustCode
                + '\'' + ", linkmanName='" + linkmanName + '\'' + ", linkmanPhone='" + linkmanPhone + '\''
                + ", linkmanPost='" + linkmanPost + '\'' + ", operateType='" + operateType + '\'' + ", linkmanMail='"
                + linkmanMail + '\'' + ", operateOfficeId='" + operateOfficeId + '\'' + ", operateOfficeName='"
                + operateOfficeName + '\'' + ", operateUserName='" + operateUserName + '\'' + ", remarks='" + remarks
                + '\'' + ", createBy='" + createBy + '\'' + ", createTime=" + createTime + ", preLinkmanRemark='"
                + preLinkmanRemark + '\'' + ", nowLinkmanRemark='" + nowLinkmanRemark + '\'' + ", preLinkmanName='"
                + preLinkmanName + '\'' + ", preLinkmanPhone='" + preLinkmanPhone + '\'' + ", preLinkmanPost='"
                + preLinkmanPost + '\'' + ", preLinkmanMail='" + preLinkmanMail + '\'' + '}';
    }
}
