/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.security;


import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import javax.naming.AuthenticationNotSupportedException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.ldap.UnsupportedAuthenticationMechanismException;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.servlet.ValidateCodeServlet;
import com.sijibao.oa.common.utils.Encodes;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.common.web.Servlets;
import com.sijibao.oa.modules.sys.entity.Menu;
import com.sijibao.oa.modules.sys.entity.Role;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.SystemService;
import com.sijibao.oa.modules.sys.utils.LogUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.modules.sys.web.LoginController;

/**
 * 系统安全认证实现类
 * @author ThinkGem
 * @version 2014-7-5
 */
@Service
//@DependsOn({"userDao","roleDao","menuDao"})
public class LdapAuthorizingRealm extends JndiLdapRealm {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private SystemService systemService;

	private String rootDN;

	public String getRootDN() {
		return rootDN;
	}

	public void setRootDN(String rootDN) {
		this.rootDN = rootDN;
	}

	public LdapAuthorizingRealm() {
		this.setCachingEnabled(false);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		long startTime = System.currentTimeMillis();
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		// 校验登录验证码
		if (LoginController.isValidateCodeLogin(token.getUsername(), false, false)) {
			Session session = UserUtils.getSession();
			String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
				throw new AuthenticationException("msg:验证码错误, 请重试.");
			}
		}

		AuthenticationInfo info;
		try {
			info = queryForAuthenticationInfo(token, getContextFactory());
			// getAuthorizationInfo(token.getUsername());
		} catch (AuthenticationNotSupportedException e) {
			String msg = "msg:Unsupported configured authentication mechanism";
			throw new UnsupportedAuthenticationMechanismException(msg, e);
		} catch (javax.naming.AuthenticationException e) {
			String msg = "msg:LDAP authentication failed.";
			throw new AuthenticationException(msg, e);
		} catch (NamingException e) {
			String msg = "msg:LDAP naming error while attempting to authenticate user.";
			throw new AuthenticationException(msg, e);
		} catch (UnknownAccountException e) {
			String msg = "msg:账号不存在!";
			throw new UnknownAccountException(msg, e);
		} catch (IncorrectCredentialsException e) {
			String msg = "msg:密码错误";
			throw new IncorrectCredentialsException(msg, e);
		}
		long endTime = System.currentTimeMillis();
		long differTime=endTime-startTime;
		logger.info("【web端登入】doGetAuthenticationInfo{}，总共耗时{}ms",new SimpleDateFormat("hh:mm:ss.SSS")
				.format(endTime),differTime);
		return info;

	}


	/**
	 * 连接LDAP查询用户信息是否存在
	 * <p>
	 * 1. 从页面得到登录名和密码。注意这里的登录名和密码一开始并没有被用到。 2.
	 * 先匿名绑定到LDAP服务器，如果LDAP服务器没有启用匿名绑定，一般会提供一个默认的用户，用这个用户进行绑定即可。 3.
	 * 之前输入的登录名在这里就有用了，当上一步绑定成功以后，需要执行一个搜索，而filter就是用登录名来构造，形如： "CN=*（xn607659）"
	 * 。 搜索执行完毕后，需要对结果进行判断，如果只返回一个entry，这个就是包含了该用户信息的entry，可以得到该 entry的DN，后面使用。
	 * 如果返回不止一个或者没有返回，说明用户名输入有误，应该退出验证并返回错误信息。 4.
	 * 如果能进行到这一步，说明用相应的用户，而上一步执行时得到了用户信息所在的entry的DN，
	 * 这里就需要用这个DN和第一步中得到的password重新绑定LDAP服务器。 5.
	 * 执行完上一步，验证的主要过程就结束了，如果能成功绑定，那么就说明验证成功，如果不行，则应该返回密码错误的信息。 这5大步就是基于LDAP的一个
	 * “两次绑定” 验证方法
	 *
	 * @param authcToken
	 * @param ldapContextFactory
	 * @return
	 * @throws NamingException
	 */
	@Override
	protected AuthenticationInfo queryForAuthenticationInfo(AuthenticationToken authcToken,
															LdapContextFactory ldapContextFactory) throws NamingException {
		long startTime = System.currentTimeMillis();
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		Object principal = token.getPrincipal();// 输入的用户名
		Object credentials = token.getCredentials();// 输入的密码
		String userName = principal.toString();
		String password = new String((char[]) credentials);

		LdapContext systemCtx = null;
		LdapContext ctx = null;
		try {
			// 使用系统配置的用户连接LDAP
			systemCtx = ldapContextFactory.getSystemLdapContext();
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);// 搜索范围是包括子树
			NamingEnumeration<SearchResult> results = systemCtx.search(rootDN, "cn=" + principal, constraints);
			if (results != null && !results.hasMore()) {
//				throw new UnknownAccountException();
				throw new AuthenticationException("msg:账号或密码错误！");
			} else {
				String mail = null;
				while (results.hasMore()) {
					SearchResult si = (SearchResult) results.next();
					principal = si.getName() + "," + rootDN;
					mail = si.getAttributes().get("mail").get(0).toString();
					logger.debug(si.getAttributes().get("mail").toString());
				}
				logger.info("DN=[" + principal + "]");
				try {
					// 根据查询到的用户与输入的密码连接LDAP，用户密码正确才能连接
					ctx = ldapContextFactory.getLdapContext(principal, credentials);
					dealUser(userName, password);
				} catch (NamingException e) {
					throw new AuthenticationException("msg:账号或密码错误！");
				}
				// 校验用户名密码
				if (StringUtils.isNotBlank(mail)) {
					// 通过用户名匹配登录
					/*
					 * String name = userName.replace(".", ""); User user =
					 * getSystemService().getUserByLoginName(name);
					 */
					// 通过邮箱匹配来登录
					User user = getSystemService().getUserByMail(mail);
					Gson gson = new Gson();
					System.out.println("mobile=======" + mail);

					if (user != null) {
						if (Global.NO.equals(user.getLoginFlag())) {
							throw new AuthenticationException("msg:该已帐号禁止登录.");
						}
						byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
						return new SimpleAuthenticationInfo(new SystemAuthorizingRealm.Principal(user, token.isMobileLogin()),
								user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
					} else {
						// throw new AuthenticationException("msg:"+"系统错误");
						throw new AuthenticationException("msg:" + mail + "未找到账号");
					}

				} else {
					throw new AuthenticationException("msg:" + "ldap未配置用户的邮箱");
				}
			}
		} finally {
			// 关闭连接
			LdapUtils.closeContext(systemCtx);
			LdapUtils.closeContext(ctx);
			long endTime = System.currentTimeMillis();
			long differTime=endTime-startTime;
			logger.info("【web端登入】queryForAuthenticationInfo{}，总共耗时{}ms",new SimpleDateFormat("hh:mm:ss.SSS")
					.format(endTime),differTime);
		}
	}


	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		long startTime = System.currentTimeMillis();
		SystemAuthorizingRealm.Principal principal = (SystemAuthorizingRealm.Principal) getAvailablePrincipal(principals);
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
					throw new AuthenticationException("msg:账号或密码错误！");
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
			long endTime = System.currentTimeMillis();
			long differTime=endTime-startTime;
			logger.info("【web端登入】doGetAuthorizationInfo{}，总共耗时{}ms",new SimpleDateFormat("hh:mm:ss.SSS")
					.format(endTime),differTime);
			return info;
		} else {
			return null;
		}
	}

	/**
	 * 将LDAP查询到的用户保存到sys_user表
	 *
	 * @param userName
	 */
	private void dealUser(String userName, String password) {
		if (StringUtils.isEmpty(userName)) {
			return;
		}
		// TO DO...
	}

	/**
	 * 获取授权信息
	 *
	 * @param principals
	 * @return
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

		return info;
	}

	/**
	 * 获取系统业务对象
	 */
	public SystemService getSystemService() {
		if (systemService == null) {
			systemService = SpringContextHolder.getBean(SystemService.class);
		}
		return systemService;
	}

}
