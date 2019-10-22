/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.ProjectMaintenanceDao;
import com.sijibao.oa.modules.oa.entity.ProjectMaintenance;

/**
 * 项目维护管理Service
 * @author wanxb
 * @version 2018-06-29
 */
@Service
@Transactional(readOnly = true)
public class ProjectMaintenanceService extends CrudService<ProjectMaintenanceDao, ProjectMaintenance> {

	public ProjectMaintenance get(String id) {
		return super.get(id);
	}
	
	public List<ProjectMaintenance> findList(ProjectMaintenance projectMaintenance) {
		return super.findList(projectMaintenance);
	}
	
	public Page<ProjectMaintenance> findPage(Page<ProjectMaintenance> page, ProjectMaintenance projectMaintenance) {
		return super.findPage(page, projectMaintenance);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectMaintenance projectMaintenance) {
		super.save(projectMaintenance);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectMaintenance projectMaintenance) {
		super.delete(projectMaintenance);
	}
	
}