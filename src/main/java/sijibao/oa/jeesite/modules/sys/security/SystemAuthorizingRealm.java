/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.servlet.ValidateCodeServlet;
import com.sijibao.oa.common.utils.Encodes;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.web.Servlets;
import com.sijibao.oa.modules.intfz.bean.RoleInfo;
import com.sijibao.oa.modules.sys.entity.*;
import com.sijibao.oa.modules.sys.service.SysVersionService;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.LogUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.modules.sys.web.LoginController;

/**
 * 管理后端系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-5
 */
@Service
//@DependsOn({"userDao","roleDao","menuDao"})
public class SystemAuthorizingRealm extends AuthorizingRealm {
	
	private SystemService systemService;
	
	public SystemAuthorizingRealm() {
		this.setCachingEnabled(false);
	}

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
//		int activeSessionSize = getSystemService().getSessionDao().getActiveSessions(false).size();
//		if (logger.isDebugEnabled()){
//			logger.debug("login submit, active session size: , username: {}", token.getUsername());
//		}
		if(!token.isFront()){
			// 校验登录验证码
			if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)){
				Session session = UserUtils.getSession();
				String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
				if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
					throw new AuthenticationException("msg:验证码错误, 请重试.");
				}
			}
		}

		// 是否手机登录
		boolean wxchat = token.isMobileLogin() && StringUtils.isNotBlank(token.getOpenId());
		// 校验用户名密码
		User user;
		// 微信登录授权
		if(wxchat){
			user = getSystemService().getUserByOpenId(token.getOpenId());
			if (user == null) {
				throw new UnknownAccountException();
			}
			user.setPassword(SystemService.entryptPassword(""));
		} else {// PC登录授权,现阶段使用手机号登录
			user = getSystemService().getUserByMobile(token.getUsername());
//			user.setPassword(SystemService.entryptPassword(token.getPassword()));
		}

		if (user != null) {
			if (Global.NO.equals(user.getLoginFlag()) || "admin".equals(user.getLoginName())){
				throw new AuthenticationException("msg:您已被禁止登陆，请联系人事开通登陆权限");
			}
            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
			long endTime = System.currentTimeMillis();
            return new SimpleAuthenticationInfo(new Principal(user, token.isMobileLogin()),
                    user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
		} else {
			if("admin".equals(token.getUsername())){
				user = getSystemService().getUserByLoginName(token.getUsername());
				byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));
	            return new SimpleAuthenticationInfo(new Principal(user, false),
	                    user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
			}
			return null;
		}
	}
	
	/**
	 * 获取权限授权信息，如果缓存中存在，则直接从缓存中获取，否则就重新获取， 登录成功后调用
	 */
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
            return null;
        }
		
        AuthorizationInfo info = null;

        info = (AuthorizationInfo)UserUtils.getFromSession(UserUtils.AUTH_INFO);

        if (info == null) {
            info = doGetAuthorizationInfo(principals);
            if (info != null) {
            	UserUtils.putIntoSession(UserUtils.AUTH_INFO, info);
            }
        }
		long endTime = System.currentTimeMillis();
        return info;
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if (!Global.TRUE.equals(Global.getConfig("user.multiAccountLogin"))){
			Collection<Session> sessions = getSystemService().getSessionDao().getActiveSessions(true, principal, UserUtils.getSession());
			if (sessions.size() > 0){
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtils.getSubject().isAuthenticated()){
					for (Session session : sessions){
						getSystemService().getSessionDao().delete(session);
						session = null;
					}
				}
				// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。
				else{
					UserUtils.getSubject().logout();
					throw new AuthenticationException("msg:账号已在其它地方登录，请重新登录。");
				}
			}
		}
		User user = getSystemService().getUserByLoginName(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<Menu> list = UserUtils.getMenuList();
			for (Menu menu : list){
				if (StringUtils.isNotBlank(menu.getPermission())){
					// 添加基于Permission的权限信息
					for (String permission : StringUtils.split(menu.getPermission(),",")){
						info.addStringPermission(permission);
					}
				}
			}
			// 添加用户权限
			info.addStringPermission("user");
			// 添加用户角色信息
			for (Role role : user.getRoleList()){
				info.addRole(role.getEnname());
			}
			// 更新登录IP和时间
			getSystemService().updateUserLoginInfo(user);
			// 记录登录日志
			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(SystemService.HASH_ALGORITHM);
		matcher.setHashIterations(SystemService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null){
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String id; // 编号
		private String loginName; // 登录名
		private String name; // 姓名
		private String mobile;//手机号
		private boolean mobileLogin; // 是否手机登录
		private String officeId; //部门ID
		private String officeName; //部门名称
		private String postName; //所属岗位名称
		private String postId;  //岗位ID
		private List<RoleInfo> roleList;
		private String version = "0.0.0";  //版本号
		
//		private Map<String, Object> cacheMap;

		public Principal(User user, boolean mobileLogin) {
			this.id = user.getId();
			this.loginName = user.getLoginName();
			this.name = user.getName();
			this.mobile = user.getMobile();
			if(user.getOffice() != null){
				this.officeId = user.getOffice().getId();
				this.officeName = user.getOffice().getName();
			}
			this.postId = user.getPostIds();
			PostInfo postInfo = SpringContextHolder.getBean(SystemService.class).getPostInfo(user.getPostIds());
			this.postName = postInfo != null? postInfo.getPostName():"";
			this.mobileLogin = mobileLogin;
			this.roleList = Lists.newArrayList();
			for(Role role : user.getRoleList()){
				RoleInfo roleInfo = new RoleInfo();
				roleInfo.setName(role.getName());
				roleList.add(roleInfo);
			}
			SysVersion sysVersion = new SysVersion();
			List<SysVersion> sysVersionList = SpringContextHolder.getBean(SysVersionService.class).findList(sysVersion);
			if(sysVersionList != null && sysVersionList.size() > 0){
				this.version = sysVersionList.get(0).getVersionNo();
			}
		}

		public String getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public String getName() {
			return name;
		}

		public String getMobile() {
			return mobile;
		}

		public boolean isMobileLogin() {
			return mobileLogin;
		}
		
		public List<RoleInfo> getRoleList(){
			return roleList;
		}
		public String getOfficeId(){
			return officeId;
		}
		
		public String getOfficeName(){
			return officeName;
		}
		
		public String getPostName(){
			return postName;
		}
		
		public String getPostId(){
			return postId;
		}
		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		/**
		 * 获取SESSIONID
		 */
		public String getSessionid() {
			try{
				String sessionid = (String) UserUtils.getSession().getId();
				System.err.println("mobile:" + mobile + ";sessionid:" + sessionid);
				return sessionid;
			}catch (Exception e) {
				return "";
			}
		}
		
		@Override
		public String toString() {
			return id;
		}

	}
}
