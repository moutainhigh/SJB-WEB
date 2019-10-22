/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.ResourcesApplyDao;
import com.sijibao.oa.modules.flow.entity.ResourcesApply;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 资源申请Service
 * @author xuby
 * @version 2018-05-30
 */
@Service
@Transactional(readOnly = true)
public class ResourcesApplyService extends CrudService<ResourcesApplyDao, ResourcesApply> {
	
	public ResourcesApply get(String id) {
		return super.get(id);
	}
	
	public List<ResourcesApply> findList(ResourcesApply resourcesApply) {
		return super.findList(resourcesApply);
	}
	
	public Page<ResourcesApply> findPage(Page<ResourcesApply> page, ResourcesApply resourcesApply) {
		Page<ResourcesApply> resultPage = super.findPage(page, resourcesApply);
		for(ResourcesApply r:resultPage.getList()){
			r.setResourcesStatusValue(DictUtils.getDictLabel(r.getResourcesStatus(), "expense_status", "")); //设置单据状态
			r.setResourcesTypeValue(DictUtils.getDictLabel(r.getResourcesType(), "resources_type", "")); //资源类型
		}
		return resultPage;
	}
	
	public Page<ResourcesApply> findRelationPage(Page<ResourcesApply> page, ResourcesApply resourcesApply) {
		resourcesApply.setPage(page);
		Page<ResourcesApply> resultPage = page.setList(dao.findRelationList(resourcesApply));
		for(ResourcesApply r:resultPage.getList()){
			r.setResourcesStatusValue(DictUtils.getDictLabel(r.getResourcesStatus(), "expense_status", "")); //设置单据状态
			r.setResourcesTypeValue(DictUtils.getDictLabel(r.getResourcesType(), "resources_type", "")); //资源类型
		}
		return resultPage;
	}
	
	@Transactional(readOnly = false)
	public void save(ResourcesApply resourcesApply) {
		super.save(resourcesApply);
	}
	
//	@Transactional(readOnly = false)
//	public void delete(ResourcesApply resourcesApply) {
//		super.delete(resourcesApply);
//	}
	
	public ResourcesApply getByProcInsId(String procInsId){
		return dao.getByProcInsId(procInsId);
	}
	
	public ResourcesApply getByProcCode(String procCode){
		return dao.getByProcCode(procCode);
	}
	

	
}