/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.ProjectNodeDao;
import com.sijibao.oa.modules.oa.entity.ProjectNode;

/**
 * 项目节点Service
 * @author wanxb
 * @version 2018-09-12
 */
@Service
@Transactional(readOnly = true)
public class ProjectNodeService extends CrudService<ProjectNodeDao, ProjectNode> {
	@Autowired
	private ProjectNodeDao projectNodeDao;
	
	public ProjectNode get(String id) {
		return super.get(id);
	}
	
	public List<ProjectNode> findList(ProjectNode projectNode) {
		return super.findList(projectNode);
	}
	
	public Page<ProjectNode> findPage(Page<ProjectNode> page, ProjectNode projectNode) {
		return super.findPage(page, projectNode);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectNode projectNode) {
		super.save(projectNode);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectNode projectNode) {
		super.delete(projectNode);
	}

	public void deleteProjectNodeByProjectId(String id) {
		projectNodeDao.deleteProjectNodeByProjectId(id);
	}
	
}