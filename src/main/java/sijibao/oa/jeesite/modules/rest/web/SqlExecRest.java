package sijibao.oa.jeesite.modules.rest.web;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.common.utils.WebUtils;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.rest.service.impl.SqlExecServiceImpl;
import com.sijibao.oa.modules.rest.vo.SqlExecRequest;

/**
 * @description: sql脚本执行
 * @author: xgx
 * @create: 2019-10-12 15:34
 **/
@Controller
@RequestMapping("rest/sql")
public class SqlExecRest {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SqlExecServiceImpl sqlExecService;

    private static final String TOKEN="29e5e694f86e484e8cb6ec40e3924952";

    /**
     * 查询
     * @param sqlExecRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("select")
    public BaseResp<Object> select(@RequestBody SqlExecRequest sqlExecRequest, @RequestHeader("token") String token, HttpServletRequest request){
        if(token==null||!TOKEN.equals(new Md5Hash(token,sqlExecRequest.getUserName()).toString())){
            return new BaseResp<Object>(IntfzRs.ERROR,"认证失败，无法执行",null);
        }
        String ipAddr = WebUtils.getIpAddr(request);
        logger.info("【公共sql执行】执行用户：{}，用户ipAddr：{}，请求参数:{}",sqlExecRequest.getUserName(),ipAddr,sqlExecRequest.getSqlStr());
        if(StringUtils.isBlank(sqlExecRequest.getSqlStr()) || !sqlExecRequest.getSqlStr().contains("limit")){
            return new BaseResp<Object>(IntfzRs.ERROR,"无效的sql语句",null);
        }
        List<LinkedHashMap<String, Object>> select = sqlExecService.select(sqlExecRequest.getSqlStr());
        return new BaseResp<Object>(IntfzRs.SUCCESS,"查询成功",select);
    }


    /**
     * 更新
     * @param sqlExecRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("update")
    public BaseResp<String> update(@RequestBody SqlExecRequest sqlExecRequest, @RequestHeader("token") String token, HttpServletRequest request){
        if(token==null||!TOKEN.equals(new Md5Hash(token,sqlExecRequest.getUserName()).toString())){
            return new BaseResp<String>(IntfzRs.ERROR,"认证失败，无法执行",null);
        }
        String ipAddr = WebUtils.getIpAddr(request);
        logger.info("【公共sql执行】执行用户：{}，用户ipAddr：{}，请求参数:{}",sqlExecRequest.getUserName(),ipAddr,sqlExecRequest.getSqlStr());
        if(StringUtils.isBlank(sqlExecRequest.getSqlStr()) || !sqlExecRequest.getSqlStr().contains("where")){
            return new BaseResp<String>(IntfzRs.ERROR,"无效的sql语句",null);
        }
        int update = sqlExecService.update(sqlExecRequest.getSqlStr());
        return new BaseResp<String>(IntfzRs.SUCCESS,"更新成功，影响"+update+"行",null);
    }

    /**
     * 数据定义语言
     * @param sqlExecRequest
     * @return
     */
    @ResponseBody
    @RequestMapping("ddl")
    public BaseResp<String> ddl(@RequestBody SqlExecRequest sqlExecRequest, @RequestHeader("token") String token, HttpServletRequest request){
        if(token==null||!TOKEN.equals(new Md5Hash(token,sqlExecRequest.getUserName()).toString())){
            return new BaseResp<String>(IntfzRs.ERROR,"认证失败，无法执行",null);
        }
        if(request.getRequestURI().contains("oa.sijibao.com")){
            return new BaseResp<String>(IntfzRs.ERROR,"生产环境，无法执行",null);
        }
        String ipAddr = WebUtils.getIpAddr(request);
        logger.info("【公共sql执行】执行用户：{}，用户ipAddr：{}，请求参数:{}",sqlExecRequest.getUserName(),ipAddr,sqlExecRequest.getSqlStr());
        if(StringUtils.isBlank(sqlExecRequest.getSqlStr())){
            return new BaseResp<String>(IntfzRs.ERROR,"无效的sql语句",null);
        }
        try {
            sqlExecService.ddl(sqlExecRequest.getSqlStr());
        } catch (Exception e) {
            logger.error("【公共sql执行】失败",e);
            return new BaseResp<String>(IntfzRs.ERROR,"更新表结构失败",null);
        }
        return new BaseResp<String>(IntfzRs.SUCCESS,"更新表结构成功",null);
    }

}
