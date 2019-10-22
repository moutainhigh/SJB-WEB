/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.ProjectMaintenance;

/**
 * 项目维护管理DAO接口
 * @author wanxb
 * @version 2018-06-29
 */
@MyBatisDao
public interface ProjectMaintenanceDao extends CrudDao<ProjectMaintenance> {
	
}