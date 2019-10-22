package sijibao.oa.jeesite.modules.rest.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.modules.rest.dao.SqlExecDao;

/**
 * @description: sql脚本执行
 * @author: xgx
 * @create: 2019-10-12 15:39
 **/
@Service("sqlExecServiceImpl")
@Transactional
public class SqlExecServiceImpl {
    @Autowired
    private SqlExecDao sqlExecDao;

    public List<LinkedHashMap<String,Object>> select(String sqlStr){
        return sqlExecDao.getPublicItems(sqlStr);
    }

    public int update(String sqlStr){
        return sqlExecDao.updatePublicDate(sqlStr);
    }

    public int ddl(String sqlStr){
        return sqlExecDao.ddlPublicDate(sqlStr);
    }
}
