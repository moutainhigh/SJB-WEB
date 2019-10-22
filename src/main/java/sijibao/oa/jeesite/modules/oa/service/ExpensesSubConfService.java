/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.modules.oa.dao.ExpensesSubConfDao;
import com.sijibao.oa.modules.oa.entity.ExpensesSubConf;
import com.sijibao.oa.modules.sys.utils.SubInfoUtils;

/**
 * 费用科目配置管理Service
 * @author Petter
 * @version 2017-12-24
 */
@Service
@Transactional(readOnly = true)
public class ExpensesSubConfService extends CrudService<ExpensesSubConfDao, ExpensesSubConf> {

	public ExpensesSubConf get(String id) {
		return super.get(id);
	}
	
	public List<ExpensesSubConf> findList(ExpensesSubConf expensesSubConf) {
		return super.findList(expensesSubConf);
	}
	
	public Page<ExpensesSubConf> findPage(Page<ExpensesSubConf> page, ExpensesSubConf expensesSubConf) {
		return super.findPage(page, expensesSubConf);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpensesSubConf expensesSubConf) {
		super.save(expensesSubConf);
		CacheUtils.remove(SubInfoUtils.SUB_INFO_LIST);
		CacheUtils.remove(SubInfoUtils.SUB_CONF_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExpensesSubConf expensesSubConf) {
		super.delete(expensesSubConf);
		CacheUtils.remove(SubInfoUtils.SUB_INFO_LIST);
		CacheUtils.remove(SubInfoUtils.SUB_CONF_LIST);
	}
}