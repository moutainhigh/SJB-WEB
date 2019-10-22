/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.ResourcesHandleFlowDao;
import com.sijibao.oa.modules.flow.entity.ResourcesHandleFlow;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 资源申请办理Service
 * @author xuby
 * @version 2018-05-29
 */
@Service
@Transactional(readOnly = true)
public class ResourcesHandleFlowService extends CrudService<ResourcesHandleFlowDao, ResourcesHandleFlow> {

	public ResourcesHandleFlow get(String id) {
		return super.get(id);
	}
	
	public List<ResourcesHandleFlow> findList(ResourcesHandleFlow resourcesHandleFlow) {
		return super.findList(resourcesHandleFlow);
	}
	
	public Page<ResourcesHandleFlow> findPage(Page<ResourcesHandleFlow> page, ResourcesHandleFlow resourcesHandleFlow) {
		Page<ResourcesHandleFlow> resultPage = super.findPage(page, resourcesHandleFlow);
		for(ResourcesHandleFlow r:resultPage.getList()){
			r.setResourcesHandleStatusValue(DictUtils.getDictLabel(r.getResourcesHandleStatus(), "expense_status", "")); //设置单据状态
			r.setResourcesTypeValue(DictUtils.getDictLabel(r.getResourcesType(), "resources_type", "")); //资源类型
			r.setHandleTypeValue(DictUtils.getDictLabel(r.getHandleType(), "handle_type", ""));
		}
		return resultPage;
	}
	
	public Page<ResourcesHandleFlow> findRelationPage(Page<ResourcesHandleFlow> page, ResourcesHandleFlow resourcesHandleFlow) {
		resourcesHandleFlow.setPage(page);
		Page<ResourcesHandleFlow> resultPage = page.setList(dao.findRelationList(resourcesHandleFlow));
		for(ResourcesHandleFlow r:resultPage.getList()){
			r.setResourcesHandleStatusValue(DictUtils.getDictLabel(r.getResourcesHandleStatus(), "expense_status", "")); //设置单据状态
			r.setResourcesTypeValue(DictUtils.getDictLabel(r.getResourcesType(), "resources_type", "")); //资源类型
			r.setHandleTypeValue(DictUtils.getDictLabel(r.getHandleType(), "handle_type", ""));
		}
		return resultPage;
	}
	
	@Transactional(readOnly = false)
	public void save(ResourcesHandleFlow resourcesHandleFlow) {
		super.save(resourcesHandleFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(ResourcesHandleFlow resourcesHandleFlow) {
		super.delete(resourcesHandleFlow);
	}
	
	public ResourcesHandleFlow getByProcInsId(String procInsId){
		return dao.getByProcInsId(procInsId);
	}
	
	public ResourcesHandleFlow getByProcCode(String procCode){
		return dao.getByProcCode(procCode);
	}
}