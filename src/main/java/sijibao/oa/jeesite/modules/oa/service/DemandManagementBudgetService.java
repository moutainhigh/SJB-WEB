/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.DemandManagementBudgetDao;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;

/**
 * 需求预算明细管理Service
 * @author xuby
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class DemandManagementBudgetService extends CrudService<DemandManagementBudgetDao, DemandManagementBudget> {

	public DemandManagementBudget get(String id) {
		return super.get(id);
	}
	
	public List<DemandManagementBudget> findList(DemandManagementBudget demandManagementBudget) {
		return super.findList(demandManagementBudget);
	}
	
	public Page<DemandManagementBudget> findPage(Page<DemandManagementBudget> page, DemandManagementBudget demandManagementBudget) {
		return super.findPage(page, demandManagementBudget);
	}
	
	@Transactional(readOnly = false)
	public void save(DemandManagementBudget demandManagementBudget) {
        if(demandManagementBudget.getRemarks() == null){
            demandManagementBudget.setRemarks("");
        }
		super.save(demandManagementBudget);
	}
	
	@Transactional(readOnly = false)
	public void delete(DemandManagementBudget demandManagementBudget) {
		super.delete(demandManagementBudget);
	}
	
	@Transactional(readOnly = false)
	public void deleteForProcCode(DemandManagementBudget demandManagementBudget) {
		dao.deleteForProcCode(demandManagementBudget);
	}
}