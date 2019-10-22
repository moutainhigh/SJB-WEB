/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.persistence.DataEntity;
import com.sijibao.oa.common.supcan.annotation.treelist.cols.SupCol;
import com.sijibao.oa.common.utils.excel.annotation.ExcelField;

/**
 * 用户Entity
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserExport extends DataEntity<UserExport> {

	private static final long serialVersionUID = 1L;
	private Office company;	// 归属公司
	private Office office;	// 归属部门
	private String postIds;//岗位id
	private String loginName;// 登录名
	private String password;// 密码
	private String no;		// 工号
	private String dingId; // 钉钉ID
	private String wechatId; //微信openID
	private String name;	// 姓名
	private String email;	// 邮箱
	private String phone;	// 电话
	private String mobile;	// 手机
	private String userType;// 用户类型
	private String loginIp;	// 最后登录IP
	private Date loginDate;	// 最后登录日期
	private String loginFlag;	// 是否允许登录
	private String photo;	// 头像
	private String unameInitials; //用户名称首字母
	
	private String oldLoginName;// 原登录名
	private String newPassword;	// 新密码
	
	private String oldLoginIp;	// 上次登录IP
	private Date oldLoginDate;	// 上次登录日期
	
	private Role role;	// 根据角色查询用户条件
	private String rank;//职级
	
	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表
	private List<PostInfo> postInfoList = Lists.newArrayList(); //拥有岗位列表
	
	private String postName;//岗位名称
	private String userStatus;//用户状态
	private String userStatusName;//用户状态name
	private String roleName;
	private List<String> handover;//离职交接人
	private String isFinish;//个人申请单据是否都完结:0是,1否
	
	public UserExport() {
		super();
		this.loginFlag = Global.YES;
	}
	
	public UserExport(String id){
		super(id);
	}

	public UserExport(String id, String loginName){
		super(id);
		this.loginName = loginName;
	}

	public UserExport(String id, String loginName, String dingId, String wechatId,String phone){
		super(id);
		this.loginName = loginName;
		this.dingId = dingId;
		this.wechatId = wechatId;
		this.phone = phone;
	}

	public UserExport(Role role){
		super();
		this.role = role;
	}
	
	public String getPhoto() {
		return photo;
	}
	@ExcelField(title="工号", align=2, sort=100)
	public String getNo() {
		return no;
	}
	@JsonIgnore
	@NotNull(message="归属公司不能为空")
	@ExcelField(title="归属公司", align=2, sort=200)
	public Office getCompany() {
		return company;
	}
	@JsonIgnore
	@NotNull(message="归属部门不能为空")
	@ExcelField(title="归属部门", align=2, sort=300)
	public Office getOffice() {
		return office;
	}
	@Length(min=1, max=100, message="姓名长度必须介于 1 和 100 之间")
	@ExcelField(title="姓名", align=2, sort=400)
	public String getName() {
		return name;
	}
	@Length(min=0, max=200, message="手机长度必须介于 1 和 200 之间")
	@ExcelField(title="手机号", align=2, sort=500)
	public String getMobile() {
		return mobile;
	}
	@Length(min=1, max=100, message="登录名长度必须介于 1 和 100 之间")
	@ExcelField(title="唯一账号", align=2, sort=550)
	public String getLoginName() {
		return loginName;
	}
	@Email(message="邮箱格式不正确")
	@Length(min=0, max=200, message="邮箱长度必须介于 1 和 200 之间")
	@ExcelField(title="邮箱", align=1, sort=600)
	public String getEmail() {
		return email;
	}
	@ExcelField(title="岗位名称", align=1, sort=700)
	public String getPostName() {
		return postName;
	}
	@ExcelField(title="角色名称", align=1, sort=800)
	public String getRoleName() {
		return roleName;
	}
	public String getUserStatus() {
		return userStatus;
	}
	@ExcelField(title="状态", align=1, sort=850)
	public String getUserStatusName() {
		return userStatusName;
	}
	@ExcelField(title="备注", align=1, sort=900)
	public String getRemarks() {
		return remarks;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	@SupCol(isUnique="true", isHide="true")
//	@ExcelField(title="ID", type=1, align=2, sort=1)
	public String getId() {
		return id;
	}

	public void setCompany(Office company) {
		this.company = company;
	}
	
	
	public void setOffice(Office office) {
		this.office = office;
	}

	public String getPostIds() {
		return postIds;
	}

	public void setPostIds(String postIds) {
		this.postIds = postIds;
	}

	

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@JsonIgnore
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setName(String name) {
		this.name = name;
	}

	

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=200, message="电话长度必须介于 1 和 200 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	
	@Length(min=0, max=100, message="用户类型长度必须介于 1 和 100 之间")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

//	@ExcelField(title="创建时间", type=0, align=1, sort=90)
	public Date getCreateDate() {
		return createDate;
	}

//	@ExcelField(title="最后登录IP", type=1, align=1, sort=100)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@ExcelField(title="最后登录日期", type=1, align=1, sort=110)
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldLoginIp() {
		if (oldLoginIp == null){
			return loginIp;
		}
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOldLoginDate() {
		if (oldLoginDate == null){
			return loginDate;
		}
		return oldLoginDate;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JsonIgnore
//	@ExcelField(title="拥有角色", align=1, sort=800, fieldType=RoleListType.class)
	public List<Role> getRoleList() {
		return roleList;
	}
	
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	
	public List<PostInfo> getPostInfoList() {
		return postInfoList;
	}

	public void setPostInfoList(List<PostInfo> postInfoList) {
		this.postInfoList = postInfoList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}
	@JsonIgnore
	public List<String> getPostIdList() {
		List<String> postIdList = Lists.newArrayList();
		for (PostInfo post : postInfoList) {
			postIdList.add(post.getId());
		}
		return postIdList;
	}

	public void setPostIdList(List<String> postIdList) {
		postInfoList = Lists.newArrayList();
		for (String postId : postIdList) {
			PostInfo post = new PostInfo();
			post.setId(postId);
			postInfoList.add(post);
		}
	}
	@JsonIgnore
	public List<String> getPostNameList() {
		List<String> postNameList = Lists.newArrayList();
		for (PostInfo postInfo : postInfoList) {
			postNameList.add(postInfo.getId());
		}
		return postNameList;
	}

	public void setPostNameList(List<String> postNameList) {
		postInfoList = Lists.newArrayList();
		for (String postId : postNameList) {
			PostInfo postInfo = new PostInfo();
			postInfo.setId(postId);
			postInfoList.add(postInfo);
		}
	}
	
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	
	
	public boolean isAdmin(){
		return isAdmin(this.id);
	}
	
	public static boolean isAdmin(String id){
		return id != null && "1".equals(id);
	}

	public String getUnameInitials() {
		return unameInitials;
	}

	public void setUnameInitials(String unameInitials) {
		this.unameInitials = unameInitials;
	}
	@ExcelField(title="职级", align=1, sort=700)
	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	

	public void setPostName(String postName) {
		this.postName = postName;
	}

	

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	



	public List<String> getHandover() {
		return handover;
	}

	public void setHandover(List<String> handover) {
		this.handover = handover;
	}

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}

	

	public void setUserStatusName(String userStatusName) {
		this.userStatusName = userStatusName;
	}

	@Override
	public String toString() {
		return "UserExport [company=" + company + ", office=" + office + ", postIds=" + postIds + ", loginName="
				+ loginName + ", password=" + password + ", no=" + no + ", dingId=" + dingId + ", wechatId=" + wechatId
				+ ", name=" + name + ", email=" + email + ", phone=" + phone + ", mobile=" + mobile + ", userType="
				+ userType + ", loginIp=" + loginIp + ", loginDate=" + loginDate + ", loginFlag=" + loginFlag
				+ ", photo=" + photo + ", unameInitials=" + unameInitials + ", oldLoginName=" + oldLoginName
				+ ", newPassword=" + newPassword + ", oldLoginIp=" + oldLoginIp + ", oldLoginDate=" + oldLoginDate
				+ ", role=" + role + ", rank=" + rank + ", roleList=" + roleList + ", postInfoList=" + postInfoList
				+ ", postName=" + postName + ", userStatus=" + userStatus + ", userStatusName=" + userStatusName
				+ ", roleName=" + roleName + ", handover=" + handover + ", isFinish=" + isFinish + "]";
	}



}