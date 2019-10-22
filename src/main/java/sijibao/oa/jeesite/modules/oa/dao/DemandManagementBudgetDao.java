/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;

/**
 * 需求申请预算管理DAO接口
 * @author xuby
 * @version 2018-03-28
 */
@MyBatisDao
public interface DemandManagementBudgetDao extends CrudDao<DemandManagementBudget> {
	void deleteForProcCode(DemandManagementBudget demandManagementBudget);
}