package sijibao.oa.jeesite.modules.sys.security;

import java.net.URLEncoder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.MDC;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 管理后台用户访问过滤器
 */
@Service
public class AdminEndFilter extends AccessControlFilter {
    private static final String LOGIN_URL = Global.getAdminPath() + "/login";
    private static final String LOGOUT_URL = Global.getAdminPath() + "/logout";

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        if (pathsMatch(LOGIN_URL, servletRequest) || pathsMatch(LOGOUT_URL, servletRequest)) {
            return true;
        } else {
            SystemAuthorizingRealm.Principal p = UserUtils.getPrincipal();
            if (p != null) {
                User u = UserUtils.get(p.getId());
                if (u != null) {
                    if(!Global.NO.equals(u.getLoginFlag())){
                        MDC.put("userId", u.getName());
                    }
                    return !Global.NO.equals(u.getLoginFlag());
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        SystemAuthorizingRealm.Principal p = UserUtils.getPrincipal();
        if (p != null) {
            User u = UserUtils.get(p.getId());
            if (u != null) {
                //对被管理员禁止登录但是正处于登录状态的用户，强制登出
                if (Global.NO.equals(u.getLoginFlag())) {
                    UserUtils.getSubject().logout();
                    String msg = URLEncoder.encode("您已被禁止登录，请联系人事开通登录权限", "UTF-8");//防止中文乱码
                    WebUtils.issueRedirect(servletRequest, servletResponse, LOGIN_URL + "?" + FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM + "=" + msg);
                    return false;
                } else {
                    return false;
                }
            } else {
                WebUtils.issueRedirect(servletRequest, servletResponse, LOGIN_URL);
                return false;
            }
        } else {
            WebUtils.issueRedirect(servletRequest, servletResponse, LOGIN_URL);
            return false;
        }
    }

}