/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.oa.dao.ExpensesStandardsDetailDao;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsDetail;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsMain;
import com.sijibao.oa.modules.oa.service.standardsCheck.StandardsPostCheck;

/**
 * 费用科目控制标准明细表Service
 * @author xuby
 * @version 2018-03-20
 */
@Service
@Transactional(readOnly = true)
public class ExpensesStandardsDetailService extends CrudService<ExpensesStandardsDetailDao, ExpensesStandardsDetail> {
	
	@Autowired
	private StandardsPostCheck standardsPostCheck;
	
	public ExpensesStandardsDetail get(String id) {
		return super.get(id);
	}
	
	public List<ExpensesStandardsDetail> findList(ExpensesStandardsDetail expensesStandsDetail) {
		return super.findList(expensesStandsDetail);
	}
	
	public Page<ExpensesStandardsDetail> findPage(Page<ExpensesStandardsDetail> page, ExpensesStandardsDetail expensesStandsDetail) {
		return super.findPage(page, expensesStandsDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpensesStandardsDetail expensesStandsDetail) {
		super.save(expensesStandsDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExpensesStandardsDetail expensesStandsDetail) {
		super.delete(expensesStandsDetail);
	}
	
	/**
	 * 根据不同的控制列表校验是否超标
	 * @param sDetailList 控制标准明细
	 * @param smain 控制标准主表
	 * @param expenseDetail 报销明细数据
	 * @return
	 */
	public Map<String,Object> checkStandardsDetail(List<ExpensesStandardsDetail> sDetailList,ExpensesStandardsMain smain,ExpenseDetail expenseDetail){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//校验控制明细标准是否超标
		resultMap = standardsPostCheck.checkStandardsDetail(sDetailList, smain, expenseDetail);
		
		return resultMap;
	}
	
}