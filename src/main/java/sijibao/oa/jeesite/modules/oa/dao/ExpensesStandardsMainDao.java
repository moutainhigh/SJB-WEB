/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;


import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsMain;

/**
 * 费用科目控制标准主表DAO接口
 * @author xuby
 * @version 2018-03-20
 */
@MyBatisDao
public interface ExpensesStandardsMainDao extends CrudDao<ExpensesStandardsMain> {
	
	/**
	 * 根据机构ID和一级报销科目编码查询对应的控制标准信息
	 * @param expensesStandardsMain
	 * @return
	 */
	ExpensesStandardsMain queryStandardsMainForOrgIdAndProjectCode(ExpensesStandardsMain expensesStandardsMain);
}