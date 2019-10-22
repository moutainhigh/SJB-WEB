/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.modules.oa.dao.ExpensesSubInfoDao;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.sys.utils.SubInfoUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 费用科目信息管理Service
 * @author Petter
 * @version 2017-12-24
 */
@Service
@Transactional(readOnly = true)
public class ExpensesSubInfoService extends CrudService<ExpensesSubInfoDao, ExpensesSubInfo> {

	public ExpensesSubInfo get(String id) {
		return super.get(id);
	}
	
	public List<ExpensesSubInfo> findList(ExpensesSubInfo expensesSubInfo) {
		return super.findList(expensesSubInfo);
	}
	
	public Page<ExpensesSubInfo> findPage(Page<ExpensesSubInfo> page, ExpensesSubInfo expensesSubInfo) {
		return super.findPage(page, expensesSubInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpensesSubInfo expensesSubInfo) {
		super.save(expensesSubInfo);
		CacheUtils.remove(SubInfoUtils.SUB_INFO_LIST);
		CacheUtils.remove(SubInfoUtils.SUB_CONF_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExpensesSubInfo expensesSubInfo) {
		super.delete(expensesSubInfo);
		CacheUtils.remove(SubInfoUtils.SUB_INFO_LIST);
		CacheUtils.remove(SubInfoUtils.SUB_CONF_LIST);
	}
	
	public ExpensesSubInfo getBySubCode(String subCode) {
		return dao.getBySubCode(subCode);
	}

	/**
	 * 停用和启用按钮
	 * @param expensesSubInfo
	 */
	@Transactional(readOnly = false)
	public void enableChange(ExpensesSubInfo expensesSubInfo) {
		List<String> ids = dao.queryIdsById(expensesSubInfo);
		expensesSubInfo.setIds(ids);
		expensesSubInfo.setUpdateTime(new Date());
		expensesSubInfo.setUpdateBy(UserUtils.getUser());
		dao.enableChange(expensesSubInfo);
		CacheUtils.remove(SubInfoUtils.SUB_INFO_LIST);
		CacheUtils.remove(SubInfoUtils.SUB_CONF_LIST);
	}

	public List<ExpensesSubInfo> findListNew(ExpensesSubInfo expensesSubInfo) {
		return dao.findListNew(expensesSubInfo);
	}

    public int querySubByIds(List<String> ids) {
		return dao.querySubByIds(ids);
    }

	public ExpensesSubInfo findBySubCode(String subCode) {
		return dao.findBySubCode(subCode);
	}
}