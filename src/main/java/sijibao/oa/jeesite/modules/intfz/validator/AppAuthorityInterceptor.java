package sijibao.oa.jeesite.modules.intfz.validator;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * APP端接口权限校验拦截器
 */
public class AppAuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AppAuthority authority = handlerMethod.getMethod().getAnnotation(AppAuthority.class);

            String clientType = httpServletRequest.getHeader("clientType");//客户端类型，因为现在关闭公众微信号了，所以类型只能是手机app
            String sjboacert = httpServletRequest.getHeader("sjboacert");//手机号，对应sys_user表mobile字段

            if (authority == null) {// controller方法未加注解则直接pass
                return true;
            } else if (StringUtils.isNotBlank(clientType) && StringUtils.isNotBlank(sjboacert)) {
                User currentUser = UserUtils.getByPhone(sjboacert);//通过mobile获取用户信息
                if (currentUser == null) {//用户未登录或不存在
                    httpServletResponse.setCharacterEncoding("utf-8");
                    httpServletResponse.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.print("{\"status\":\"" + IntfzRs.AUTH_FAILED.getCode() + "\",\"message\":\"" + IntfzRs.AUTH_FAILED.getMsg() + "\",\"data\":{}}");
                    out.flush();
                    out.close();
                    return false;
                } else if ("0".equals(currentUser.getLoginFlag())) {//用户已被禁止登录
                    httpServletResponse.setCharacterEncoding("utf-8");
                    httpServletResponse.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.print("{\"status\":\"" + IntfzRs.LOGIN_FORBID.getCode() + "\",\"message\":\"" + IntfzRs.LOGIN_FORBID.getMsg() + "\",\"data\":{}}");
                    out.flush();
                    out.close();
                    return false;
                } else {
                    return true;
                }
            } else {//请求头错误

                //如果APP端请求头中没有包含sjboacert，则返回LOGIN_FOBID禁止登录
                if(StringUtils.isBlank(sjboacert)){
                    httpServletResponse.setCharacterEncoding("utf-8");
                    httpServletResponse.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = httpServletResponse.getWriter();
                    out.print("{\"status\":\"" + IntfzRs.LOGIN_FORBID.getCode() + "\",\"message\":\"" + IntfzRs.LOGIN_FORBID.getMsg() + "\",\"data\":{}}");
                    out.flush();
                    out.close();
                    return false;
                }

                httpServletResponse.setCharacterEncoding("utf-8");
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                PrintWriter out = httpServletResponse.getWriter();
                out.print("{\"status\":\"" + IntfzRs.PARAM.getCode() + "\",\"message\":\"" + IntfzRs.PARAM.getMsg() + "\",\"data\":{}}");
                out.flush();
                out.close();
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
