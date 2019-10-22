/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao.report;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.report.EmployeeReport;
import com.sijibao.oa.modules.flow.entity.report.MonthAmountSumReport;

/**
 * 员工报表DAO接口
 * @author wanxb
 * @version 2018-07-05
 */
@MyBatisDao
public interface EmployeeReportDao extends CrudDao<EmployeeReport> {

	List<EmployeeReport> queryEmployeeReport(EmployeeReport e);

	List<MonthAmountSumReport> queryMonthAmountSum(EmployeeReport em);

	
}