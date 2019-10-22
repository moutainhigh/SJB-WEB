package sijibao.oa.jeesite.modules.sys.security;

import java.text.SimpleDateFormat;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sijibao.oa.common.config.Global;
import com.sijibao.oa.common.utils.JedisUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * web端用户访问过滤器
 */
@Service
public class WebUserFilter extends org.apache.shiro.web.filter.authc.UserFilter {
    private Logger log = LoggerFactory.getLogger(WebUserFilter.class);

	private final String WEB_LOGIN_URL = Global.getFrontPath() + "/login";
	private final String WEB_LOGOUT_URL = Global.getFrontPath() + "/logout";

    /**
     * 判断是否已登录，是否已被管理员设置成禁止登录
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
	@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        long startTime = System.currentTimeMillis();
        log.info("【web端登入】isAccessAllowed开始时间{}",new SimpleDateFormat("hh:mm:ss.SSS")
                .format(startTime));
        if (pathsMatch(WEB_LOGIN_URL, request) || pathsMatch(WEB_LOGOUT_URL,request)) {
            return true;
        }else{
        	String oa_token = WebUtils.getHeaderMap((HttpServletRequest)request).get("sessionid");
        	Object obj = JedisUtils.getObject(JedisUtils.KEY_PREFIX + "_" + oa_token);
        	Principal p = null;
        	if(obj instanceof Principal){
        		p = (Principal) obj;
        	}
        	if(p != null){
                User u = UserUtils.get(p.getId());
                long endTime = System.currentTimeMillis();
                long differTime=endTime-startTime;
                log.info("【web端登入】isAccessAllowed结束时间{}，总共耗时{}ms",new SimpleDateFormat("hh:mm:ss.SSS")
                        .format(endTime),differTime);
                if(u != null){
                    if(!pathsMatch(WEB_LOGOUT_URL, request)){
                        return Global.YES.equals(u.getLoginFlag());//账号已被管理员设置成禁止登录，则return false以阻止用户操作
                    }else {
                        return true;
                    }
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }

    }

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        long startTime = System.currentTimeMillis();
        log.info("【web端登入】onAccessDenied开始时间{}",new SimpleDateFormat("hh:mm:ss.SSS")
                .format(startTime));
		Subject subject = getSubject(request, response);
		BaseResp<String> br;

		if(subject.getPrincipal() != null){
		    Principal p = (Principal) subject.getPrincipal();
            User u = UserUtils.get(p.getId());
            if(u != null && Global.NO.equals(u.getLoginFlag())){//处理禁止登录情形
                br = new BaseResp<>(IntfzRs.LOGIN_FORBID, "您已被禁止登录，请联系人事开通登录权限", "");
            }else {
                br = new BaseResp<>(IntfzRs.ERROR, "未登录", "");
            }
        }else{
            br = new BaseResp<>(IntfzRs.LOGIN_FORBID, "未登录", "");
        }
		WebUtils.writer(WebUtils.toHttp(response), br);
        long endTime = System.currentTimeMillis();
        long differTime=endTime-startTime;
        log.info("【web端登入】onAccessDenied结束时间{}，总共耗时{}ms",new SimpleDateFormat("hh:mm:ss.SSS")
                .format(endTime),differTime);
        return false;
	}

	public static boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            System.out.println("当前请求为Ajax请求");
            return Boolean.TRUE;
        }
        System.out.println("当前请求非Ajax请求");
        return Boolean.FALSE;
    }

}
