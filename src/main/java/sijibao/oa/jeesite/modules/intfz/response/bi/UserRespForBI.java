package sijibao.oa.jeesite.modules.intfz.response.bi;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户表sys_user（全字段）")
public class UserRespForBI implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private String id;//编号

    @ApiModelProperty(value = "归属公司id")
    private String companyId;    // 归属公司id

    @ApiModelProperty(value = "归属部门id")
    private String officeId;    // 归属部门id

    @ApiModelProperty(value = "职级id")
    private String rank;//职级id

    @ApiModelProperty(value = "岗位id")
    private String postIds = "";//岗位id

    @ApiModelProperty(value = "登录名")
    private String loginName;// 登录名

    //密码不能给！！

    @ApiModelProperty(value = "工号")
    private String no = ""; // 工号

    @ApiModelProperty(value = "钉钉ID")
    private String dingId = ""; // 钉钉ID

    @ApiModelProperty(value = "微信openID")
    private String wechatId = ""; //微信openID

    @ApiModelProperty(value = "姓名")
    private String name;    // 姓名

    @ApiModelProperty(value = "邮箱")
    private String email = "";    // 邮箱

    @ApiModelProperty(value = "电话")
    private String phone = "";    // 电话

    @ApiModelProperty(value = "手机")
    private String mobile = "";    // 手机

    @ApiModelProperty(value = "用户类型")
    private String userType = "";// 用户类型

    @ApiModelProperty(value = "头像")
    private String photo = "";    // 头像

    @ApiModelProperty(value = "最后登录IP")
    private String loginIp = "";    // 最后登录IP

    @ApiModelProperty(value = "最后登录日期")
    private Date loginDate = new Date(-28800);    // 最后登录日期

    @ApiModelProperty(value = "是否可登录")
    private String loginFlag = "";    //是否可登录

    @ApiModelProperty(value = "创建者")
    private String createBy;    // 创建者

    @ApiModelProperty(value = "创建时间")
    private Date createDate;    // 创建时间

    @ApiModelProperty(value = "更新者")
    private String updateBy;    // 更新者

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;    // 更新时间

    @ApiModelProperty(value = "备注信息")
    private String remarks = "";    // 备注信息

    @ApiModelProperty(value = "用户状态")
    private String userStatus;//用户状态:1在职，2离职未完结，3离职

    @ApiModelProperty(value = "删除标记")
    private String delFlag;//删除标记

    @ApiModelProperty(value = "用户名称首字母")
    private String unameInitials = ""; //用户名称首字母

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getPostIds() {
        return postIds;
    }

    public void setPostIds(String postIds) {
        this.postIds = postIds;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDingId() {
        return dingId;
    }

    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    public String getWechatId() {
        return wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginFlag() {
        return loginFlag;
    }

    public void setLoginFlag(String loginFlag) {
        this.loginFlag = loginFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getUnameInitials() {
        return unameInitials;
    }

    public void setUnameInitials(String unameInitials) {
        this.unameInitials = unameInitials;
    }
}
