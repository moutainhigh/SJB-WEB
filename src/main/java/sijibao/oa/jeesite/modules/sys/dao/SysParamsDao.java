package sijibao.oa.jeesite.modules.sys.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.SysParams;

/**
 * 系统参数管理DAO接口
 */
@MyBatisDao
public interface SysParamsDao extends CrudDao<SysParams> {
	
	public int deleteAll();
	
}