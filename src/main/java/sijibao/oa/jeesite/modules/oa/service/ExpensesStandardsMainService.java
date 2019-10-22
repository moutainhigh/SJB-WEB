/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.ExpensesStandardsMainDao;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsMain;

/**
 * 费用科目控制标准主表Service
 * @author xuby
 * @version 2018-03-20
 */
@Service
@Transactional(readOnly = true)
public class ExpensesStandardsMainService extends CrudService<ExpensesStandardsMainDao, ExpensesStandardsMain> {

	public ExpensesStandardsMain get(String id) {
		return super.get(id);
	}
	
	public List<ExpensesStandardsMain> findList(ExpensesStandardsMain expensesStandsMain) {
		return super.findList(expensesStandsMain);
	}
	
	public Page<ExpensesStandardsMain> findPage(Page<ExpensesStandardsMain> page, ExpensesStandardsMain expensesStandsMain) {
		return super.findPage(page, expensesStandsMain);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpensesStandardsMain expensesStandsMain) {
		super.save(expensesStandsMain);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExpensesStandardsMain expensesStandsMain) {
		super.delete(expensesStandsMain);
	}
	
}