package sijibao.oa.jeesite.modules.sys.security;

import java.text.SimpleDateFormat;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.utils.JedisUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * web权限验证
 * @author Petter
 */
@Service
public class WebAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	private Logger log = LoggerFactory.getLogger(WebAuthenticationFilter.class);
	private static LdapContextFactory ldapContextFactory ;

	@Value("${ldap.base}")
	private String rootDN;

	public String getRootDN() {
		return rootDN;
	}

	public void setRootDN(String rootDN) {
		this.rootDN = rootDN;
	}

	public LdapContextFactory getLdapContextFactory() {
		return ldapContextFactory;
	}

	public void setLdapContextFactory(LdapContextFactory ldapContextFactory) {
		this.ldapContextFactory = ldapContextFactory;
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		long startTime = System.currentTimeMillis();
		log.info("【web端登入】createToken开始时间{}",new SimpleDateFormat("hh:mm:ss.SSS")
				.format(startTime));
		String requestStr = WebUtils.readReqStr((HttpServletRequest)request);
		JSONObject json = JSONObject.parseObject(requestStr);
		String username = json.getString(getUsernameParam());// 输入的用户名
		String password = json.getString(getPasswordParam());// 输入的密码


		// 使用系统配置的用户连接LDAP
//		LdapContext systemCtx = null;
//		LdapContext ctx = null;
//		try {
//			systemCtx =  ldapContextFactory.getSystemLdapContext();
//			SearchControls constraints = new SearchControls();
//			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);// 搜索范围是包括子树
//			NamingEnumeration<SearchResult> results = systemCtx.search(rootDN, "cn=" + username, constraints);
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}finally {
//			// 关闭连接
//			LdapUtils.closeContext(systemCtx);
//			LdapUtils.closeContext(ctx);
//		}


		log.debug("username:"+username+";password:"+password);
		if (StringUtils.isBlank(username)){
			username = "";
		}
		if (StringUtils.isBlank(password)){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, null, false, null, true);
		long endTime = System.currentTimeMillis();
		long differTime=endTime-startTime;
		log.info("【web端登入】createToken结束时间{}，总共耗时{}ms",new SimpleDateFormat("hh:mm:ss.SSS")
				.format(endTime),differTime);
		return usernamePasswordToken;
	}
	
	@Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginSubmission(request, response)) {
            return executeLogin(request, response);
        } else {
            return true;
        }
    }

    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest) && WebUtils.toHttp(request).getMethod().equalsIgnoreCase(POST_METHOD);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("【web端登入】onLoginSuccess开始时间{}",new SimpleDateFormat("hh:mm:ss.SSS")
				.format(startTime));
    	Object obj = subject.getPrincipal();
    	Principal p = null;
    	if(obj instanceof Principal){
    		p = (Principal) obj;
    	}

        //手工处理禁止登录情形
        BaseResp<String> failResp;
        UsernamePasswordToken tok = (UsernamePasswordToken)token;
        User u = UserUtils.getByLoginName(tok.getUsername());
        if(u != null && Global.NO.equals(u.getLoginFlag())){
            failResp = new BaseResp<>(IntfzRs.LOGIN_FORBID, "您已被禁止登录，请联系人事开通登录权限", "");
            WebUtils.writer(WebUtils.toHttp(response), failResp);
            return false;
        }

    	BaseResp<Object> br = new BaseResp<Object>(IntfzRs.SUCCESS, null, p);
        log.info("sessionid:::: {}", br.toString());
        JedisUtils.setObject(JedisUtils.KEY_PREFIX+"_"+p.getSessionid(), p, JedisUtils.TIME_OUT);
        WebUtils.writer(WebUtils.toHttp(response), br);
		long endTime = System.currentTimeMillis();
		long differTime=endTime-startTime;
		log.info("【web端登入】onLoginSuccess结束时间{}，总共耗时{}ms",new SimpleDateFormat("hh:mm:ss.SSS")
				.format(endTime),differTime);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
    	WebUtils.initPre(WebUtils.toHttp(request), WebUtils.toHttp(response));
    	BaseResp<String> br;
    	String className = e.getClass().getName();

        //手工处理禁止登录情形
        UsernamePasswordToken tok = (UsernamePasswordToken)token;
        User u = UserUtils.getByLoginName(tok.getUsername());
        if(u != null && Global.NO.equals(u.getLoginFlag())){
                br = new BaseResp<>(IntfzRs.LOGIN_FORBID, "您已被禁止登录，请联系人事开通登录权限", "");
                request.setAttribute(getFailureKeyAttribute(), className);
                WebUtils.writer(WebUtils.toHttp(response), br);
                return false;
        }

		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)
		        || AuthenticationException.class.getName().equals(className)){
			br = new BaseResp<String>(IntfzRs.ERROR, "用户或密码错误, 请重试.", "");
		}else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
			br = new BaseResp<String>(IntfzRs.ERROR, StringUtils.replace(e.getMessage(), "msg:", ""), "");
		}else{
			br = new BaseResp<String>(IntfzRs.ERROR, "系统出现点问题，请稍后再试！", "");
			e.printStackTrace(); // 输出到控制台
		}
        request.setAttribute(getFailureKeyAttribute(), className);
        WebUtils.writer(WebUtils.toHttp(response), br);
        return false;
    }

}
