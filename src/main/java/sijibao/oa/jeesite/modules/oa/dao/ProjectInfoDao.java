/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;
import com.sijibao.oa.modules.oa.entity.ProjectMaintenance;

/**
 * 项目信息DAO接口
 * @author Petter
 * @version 2018-04-17
 */
@MyBatisDao
public interface ProjectInfoDao extends CrudDao<ProjectInfo> {

	void close(@Param(value = "id") String id);

	void updateProjectState(ProjectMaintenance p);

	
}