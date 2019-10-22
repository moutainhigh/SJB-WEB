/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.ProjectCompanyDao;
import com.sijibao.oa.modules.oa.entity.ProjectCompany;

/**
 * 企业与项目关联关系Service
 * @author wanxb
 * @version 2018-09-29
 */
@Service
@Transactional(readOnly = true)
public class ProjectCompanyService extends CrudService<ProjectCompanyDao, ProjectCompany> {

	public ProjectCompany get(String id) {
		return super.get(id);
	}
	
	public List<ProjectCompany> findList(ProjectCompany projectCompany) {
		return super.findList(projectCompany);
	}
	
	public Page<ProjectCompany> findPage(Page<ProjectCompany> page, ProjectCompany projectCompany) {
		return super.findPage(page, projectCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(ProjectCompany projectCompany) {
		super.save(projectCompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(ProjectCompany projectCompany) {
		super.delete(projectCompany);
	}
	
}