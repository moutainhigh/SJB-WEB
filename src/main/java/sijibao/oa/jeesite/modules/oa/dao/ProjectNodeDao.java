/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.ProjectNode;

/**
 * 项目节点DAO接口
 * @author wanxb
 * @version 2018-09-12
 */
@MyBatisDao
public interface ProjectNodeDao extends CrudDao<ProjectNode> {
	/**
	 * 删除节点信息
	 * @param id
	 */
	void deleteProjectNodeByProjectId(@Param("id") String id);
	/**
	 * 查询项目的节点信息
	 * @param projectId
	 * @return
	 */
	List<ProjectNode> findProjectNode(@Param("projectId") String projectId);
	
	
}