package sijibao.oa.jeesite.modules.sys.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.sys.dao.UserDao;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 微信登录验证
 * @author Petter
 */
@Service
public class WxAuthenticationFilter extends AuthenticatingFilter {

	private Logger log = LoggerFactory.getLogger(WxAuthenticationFilter.class);
	
	private static final String USERNAME = "";
    private static final char[] PASSWORD = "".toCharArray();
    private static final boolean REMEMBER_ME = true;

    @Autowired
    private UserDao userDao;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
        String openId = getOpenId(request);
       
        if(StringUtils.isBlank(openId)){
        	this.writer(response, new BaseResp<String>(IntfzRs.ERROR, "token认证失败，openId为空", ""));
        }else{
        	// 微信登录授权
        	User user = userDao.getByWechatId(new User(null, null, null, openId,null));
        	if (user == null) {
        		this.writer(response, new BaseResp<String>(IntfzRs.ERROR, "token认证失败", ""));
        	}
        }

        return new UsernamePasswordToken(USERNAME, PASSWORD, REMEMBER_ME, host, null, true, openId, false);
    }

    /**
     * 获取微信OPENID
     */
    @SuppressWarnings("unchecked")
	protected String getOpenId(ServletRequest request) {
    	Enumeration<String> headers = ((HttpServletRequest)request).getHeaderNames();
		while (headers.hasMoreElements()) {
			String headerName = (String) headers.nextElement();
			if(Global.WX_OPEN_ID.equals(headerName)){
				return ((HttpServletRequest)request).getHeader(headerName);
			}
		}
        return "";
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
        BaseResp<Object> br = new BaseResp<Object>(IntfzRs.SUCCESS, null, subject.getPrincipal());
        log.info("sessionid:::: {}", br.toString());
        WebUtils.writer(WebUtils.toHttp(response), br);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
    	BaseResp<String> br;
        if (e instanceof UnknownAccountException) {
            br = new BaseResp<String>(IntfzRs.ERROR, "OPENID不存在", "");
        } else {
        	br = new BaseResp<String>(IntfzRs.ERROR, "服务器未知异常", "");
        }
        this.writer(response, br);
        return false;
    }

    private void writer(ServletResponse response, Object o){
        try {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JSONObject.toJSONString(o));
            response.getWriter().close();
        } catch (IOException e) {

        }
    }

}
