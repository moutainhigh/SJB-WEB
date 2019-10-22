/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.Cn2SpellUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.oa.dao.ProjectInfoDao;
import com.sijibao.oa.modules.oa.entity.ProjectInfo;

/**
 * 项目信息Service
 * @author Petter
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class ProjectInfoService extends CrudService<ProjectInfoDao, ProjectInfo> {
	
	@Transactional(readOnly = false)
	public void save(ProjectInfo projectInfo){
		if(StringUtils.isNotBlank(projectInfo.getProjectName())){
			//设置项目名称首字母
			projectInfo.setPnameInitials(
					Cn2SpellUtils.converterToFirstSpell(projectInfo.getProjectName().subSequence(0, 1).toString()));
		}
		super.save(projectInfo);
	}

	@Transactional(readOnly = false)
	public void close(ProjectInfo projectInfo) {
		dao.close(projectInfo.getId());
	}
	
}