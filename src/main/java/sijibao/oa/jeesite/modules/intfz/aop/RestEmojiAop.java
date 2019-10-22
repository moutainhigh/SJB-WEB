package sijibao.oa.jeesite.modules.intfz.aop;

import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.vdurmont.emoji.EmojiParser;

/**
 * 拦截前端输入的emoji表情
 **/
@Component
@Aspect
public class RestEmojiAop {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(com.sijibao.oa.modules.intfz.bean.BaseResp *(..))")
    public void restAspect() {
    }

    //web和app端接口拦截
    @Around("restAspect()")
    public Object handlerRestMethod(ProceedingJoinPoint pjp) {
        Object rtValue = null;
        String url = "";
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            HttpServletResponse response = requestAttributes.getResponse();
            url = request.getRequestURL().toString();// 请求URL
            String content = Arrays.toString(pjp.getArgs());
            //除“保存市场工作日志”和“保存实施工作日志”这两个接口外，其他所有接口都不允许输入中包含emoji表情
            if (!url.contains("/f/implyDaily/saveImplyDaily")
                    && !url.contains("/f/marketDaily/saveMarketDaily")
                    && !url.contains("/wechat/implyDaily/saveImplyDaily")
                    && !url.contains("/wechat/marketDaily/saveMarketDaily")
                    && EmojiParser.extractEmojis(content).size() > 0) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.print("{\"status\":\"" + IntfzRs.ERROR.getCode() + "\",\"message\":\"输入中包含emoji表情\",\"data\":{}}");
                out.flush();
                out.close();
                return null;
            }
            Object[] args = pjp.getArgs();
            rtValue = pjp.proceed(args);
        } catch (Throwable t) {
            log.error("【" + url + "】接口访问异常", t);
        }
        return rtValue;
    }

}
