/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.SysVersion;

/**
 * 系统版本维护DAO接口
 * @author wanxb
 * @version 2018-06-06
 */
@MyBatisDao
public interface SysVersionDao extends CrudDao<SysVersion> {

    String queryNewVersion();
}