/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.ExpenseDetailDao;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.oa.service.ExpensesSubInfoService;

/**
 * 报销费用明细Service
 * @author Petter
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class ExpenseDetailService extends CrudService<ExpenseDetailDao, ExpenseDetail> {
	@Autowired
	private ExpensesSubInfoService expensesSubInfoService;
	
	public ExpenseDetail get(String id) {
		return super.get(id);
	}
	
	public List<ExpenseDetail> findList(ExpenseDetail expenseDetail) {
		List<ExpenseDetail> resultList = super.findList(expenseDetail);
		for(ExpenseDetail d:resultList){
			//查询一级科目名称
			ExpensesSubInfo firstSubInfo = expensesSubInfoService.getBySubCode(d.getFirstSub());
			if(firstSubInfo != null && StringUtils.isNotBlank(firstSubInfo.getSubName())){
				d.setFirstSubName(firstSubInfo.getSubName());
			}
			//查询二级科目名称
			ExpensesSubInfo secondSubInfo = expensesSubInfoService.getBySubCode(d.getSecondSub());
			if(secondSubInfo != null && StringUtils.isNotBlank(secondSubInfo.getSubName())){
				d.setSecondSubName(secondSubInfo.getSubName());
			}
		}
		return resultList;
	}
	
	public Page<ExpenseDetail> findPage(Page<ExpenseDetail> page, ExpenseDetail expenseDetail) {
		return super.findPage(page, expenseDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(ExpenseDetail expenseDetail) {
		super.save(expenseDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(ExpenseDetail expenseDetail) {
		super.delete(expenseDetail);
	}
	
	public BigDecimal totalByProcCode(String procCode){
		return dao.totalByProcCode(procCode);
	}
	
}