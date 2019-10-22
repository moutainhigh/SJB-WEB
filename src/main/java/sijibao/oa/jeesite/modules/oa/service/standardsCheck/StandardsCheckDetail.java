package sijibao.oa.jeesite.modules.oa.service.standardsCheck;

import java.util.List;
import java.util.Map;

import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsDetail;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsMain;

/**
 * 费用明细控制标准校验
 * @author xuby
 *
 */
public interface StandardsCheckDetail {
	/**
	 * 根据不同的控制列表校验是否超标
	 * @param sDetailList 控制标准明细
	 * @param smain 控制标准主表
	 * @param expenseDetail 报销明细数据
	 * @return
	 */
	Map<String,Object> checkStandardsDetail(List<ExpensesStandardsDetail> sDetailList, ExpensesStandardsMain smain, ExpenseDetail expenseDetail);
}
