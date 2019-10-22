/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.math.BigDecimal;
import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;

/**
 * 报销费用明细DAO接口
 * @author Petter
 * @version 2017-12-25
 */
@MyBatisDao
public interface ExpenseDetailDao extends CrudDao<ExpenseDetail> {
	
	void deleteByProcCode(String procCode);
	
	/**
	 * 根据流程code 统计金额
	 * @param procCode
	 * @return
	 */
	BigDecimal totalByProcCode(String procCode);
	
	/**
	 * 根据报销编号查询对应的明细分组数据
	 * @param expenseFlow
	 * @return
	 */
    List<ExpenseDetail> queryExpenseDetailGroupForExpenseFlow(String procCode);
}