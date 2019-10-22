/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.ResourcesAssignDao;
import com.sijibao.oa.modules.flow.entity.ResourcesAssign;

/**
 * 资源申请人员指派Service
 * @author xuby
 * @version 2018-05-29
 */
@Service
@Transactional(readOnly = true)
public class ResourcesAssignService extends CrudService<ResourcesAssignDao, ResourcesAssign> {

	public ResourcesAssign get(String id) {
		return super.get(id);
	}
	
	public List<ResourcesAssign> findList(ResourcesAssign resourcesAssign) {
		return super.findList(resourcesAssign);
	}
	
	public Page<ResourcesAssign> findPage(Page<ResourcesAssign> page, ResourcesAssign resourcesAssign) {
		return super.findPage(page, resourcesAssign);
	}
	
	@Transactional(readOnly = false)
	public void save(ResourcesAssign resourcesAssign) {
		super.save(resourcesAssign);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourcesAssign resourcesAssign) {
		super.delete(resourcesAssign);
	}
	
}