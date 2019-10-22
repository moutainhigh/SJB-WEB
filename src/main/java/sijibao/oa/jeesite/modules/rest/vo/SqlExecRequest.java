package sijibao.oa.jeesite.modules.rest.vo;

import lombok.Data;

/**
 * @description: sql执行请求体
 * @author: xgx
 * @create: 2019-10-12 16:14
 **/
@Data
public class SqlExecRequest {
    public String userName;
    public String sqlStr;
}
