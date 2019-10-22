package sijibao.oa.jeesite.modules.rest.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sijibao.oa.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface SqlExecDao {
    @Select("${sqlStr}")
    List<LinkedHashMap<String,Object>> getPublicItems(@Param(value = "sqlStr") String sqlStr);
    @Update("${sqlStr}")
    int updatePublicDate(@Param(value = "sqlStr") String sqlStr);
    @Update("${sqlStr}")
    int ddlPublicDate(@Param(value = "sqlStr") String sqlStr);
}
