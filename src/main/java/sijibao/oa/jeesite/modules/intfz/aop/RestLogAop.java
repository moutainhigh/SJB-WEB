package sijibao.oa.jeesite.modules.intfz.aop;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.message.api.IntfzMessageService;
import com.sijibao.oa.message.api.request.DingTalkMessageRequest;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.sys.utils.DictUtils;

import io.swagger.annotations.ApiOperation;

/**
 * @description: rest对外接口的日志监控
 * @author: xgx
 * @create: 2019-07-25 16:24
 **/
@Component
@Aspect
public class RestLogAop {
    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IntfzMessageService intfzMessageService;

    @Pointcut("execution(com.sijibao.oa.modules.intfz.bean.BaseResp *(..))")
    public void restAspect(){}

    @Pointcut("@annotation(org.apache.shiro.authz.annotation.RequiresPermissions)")
    public void adminAspect(){}

    //web和app端接口拦截
    @Around("restAspect()")
    public Object handlerRestMethod(ProceedingJoinPoint pjp) {
        //开始时间
        long startTime = System.currentTimeMillis();

        Object rtValue = null;
        String classType="";
        String url="";
        String interfaceName="";
        try{
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            // 请求URL
            url=request.getRequestURL().toString();
            classType = pjp.getTarget().getClass().getName();
            Object[] args = pjp.getArgs();
            MethodSignature signature = (MethodSignature)pjp.getSignature();
            Method method = signature.getMethod();
            interfaceName=Optional.ofNullable(method.getAnnotation(ApiOperation.class)).map(ApiOperation::value).orElse("API接口");
            startTime = System.currentTimeMillis();
            log.info("【"+interfaceName+"】开始计时:{}，请求地址：{}，请求方法：{}，请求类方法参数:{}",new SimpleDateFormat("hh:mm:ss.SSS")
                    .format(startTime),url,request.getMethod(),Arrays.toString(pjp.getArgs()));
            rtValue = pjp.proceed(args);
        }catch (Throwable t){
            log.error("【"+interfaceName+"】接口访问异常",t);
            rtValue=handlerException(pjp,t);
            DingTalkMessageRequest request=new DingTalkMessageRequest();
            request.setProjectName("【管理助手OA入口】");
            request.setClassName(classType);
            request.setExceptionNo(url);
            request.setErrorMsg("异常信息："+t);
            intfzMessageService.sendDingTalk(request);
        }finally {
            //开始时间
            long endTime = System.currentTimeMillis();
            long differTime=endTime-startTime;
            log.info("【"+interfaceName+"】计时结束：{}，请求耗时：{}ms",new SimpleDateFormat("hh:mm:ss.SSS").format(endTime),differTime);
            String timeout=DictUtils.getDictValue("接口访问超时","web_time_out","5000");
            if(differTime>Long.valueOf(timeout)){
                DingTalkMessageRequest request=new DingTalkMessageRequest();
                request.setProjectName("【管理助手OA入口】");
                request.setClassName(classType);
                request.setExceptionNo(url);
                request.setErrorMsg("接口访问耗时："+differTime+" ms");
                intfzMessageService.sendDingTalk(request);
            }
        }
        return rtValue;
    }


    private BaseResp<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        BaseResp<String> result=new BaseResp<String>();

        // 已知异常
        if(e.toString().contains("ServiceException")){
            result.setStatus(IntfzRs.ERROR.getCode());
            result.setMessage(e.getMessage());
            result.setData(null);
        }else {
            result.setStatus(IntfzRs.ERROR.getCode());
            result.setMessage("系统异常，请联系管理员！！！");
            result.setData(null);
        }
        return result;
    }


    //后台管理拦截
    @Around("adminAspect()")
    public Object handlerAdminMethod(ProceedingJoinPoint pjp) {
        //开始时间
        long startTime = System.currentTimeMillis();
        Object rtValue = null;
        String url="";
        String[] rolePrivileges=null;
        try{
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            // 请求URL
            url=request.getRequestURL().toString();
            Object[] args = pjp.getArgs();
            MethodSignature signature = (MethodSignature)pjp.getSignature();
            Method method = signature.getMethod();
            rolePrivileges=Optional.ofNullable(method.getAnnotation(RequiresPermissions.class)).map(RequiresPermissions::value).orElse(new String[]{"默认角色"});
            startTime = System.currentTimeMillis();
            log.info("【后台管理】开始计时:{}，请求地址:{}，用户ip地址:{}，角色权限:{}，方法名称:{}，方法参数:{}",new SimpleDateFormat("hh:mm:ss.SSS")
                    .format(startTime),url,WebUtils.getIpAddr(request),rolePrivileges,method.getName(),Arrays.toString(pjp.getArgs()));
            rtValue = pjp.proceed(args);
        }catch (Throwable t){
            log.error("【后台管理】访问异常",t);
        }finally {
            //结束时间
            long endTime = System.currentTimeMillis();
            long differTime=endTime-startTime;
            log.info("【后台管理】计时结束：{}，请求耗时：{}ms",new SimpleDateFormat("hh:mm:ss.SSS").format(endTime),differTime);

        }
        return rtValue;
    }

}
