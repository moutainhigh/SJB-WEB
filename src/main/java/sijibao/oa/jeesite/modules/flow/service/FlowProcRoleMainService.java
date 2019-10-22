/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.FlowProcRoleMainDao;
import com.sijibao.oa.modules.flow.entity.FlowProcRoleMain;

/**
 * 流程角色主表Service
 * @author xby
 * @version 2018-06-20
 */
@Service
@Transactional(readOnly = true)
public class FlowProcRoleMainService extends CrudService<FlowProcRoleMainDao, FlowProcRoleMain> {

	public FlowProcRoleMain get(String id) {
		return super.get(id);
	}
	
	public List<FlowProcRoleMain> findList(FlowProcRoleMain flowProcRoleMain) {
		return super.findList(flowProcRoleMain);
	}
	
	public Page<FlowProcRoleMain> findPage(Page<FlowProcRoleMain> page, FlowProcRoleMain flowProcRoleMain) {
		return super.findPage(page, flowProcRoleMain);
	}
	
	@Transactional(readOnly = false)
	public void save(FlowProcRoleMain flowProcRoleMain) {
		super.save(flowProcRoleMain);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlowProcRoleMain flowProcRoleMain) {
		super.delete(flowProcRoleMain);
	}
	
}