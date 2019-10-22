package sijibao.oa.jeesite.modules.flow.service.report;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.report.EmployeeReportDao;
import com.sijibao.oa.modules.flow.entity.report.EmployeeReport;
import com.sijibao.oa.modules.flow.entity.report.MonthAmountSumReport;

/**
 * 员工费用报表Service
 * @author wanxb
 */
@Service
@Transactional(readOnly = true)
public class EmployeeReportService extends CrudService<EmployeeReportDao, EmployeeReport> {
		
	/**
	 * 查询员工报表信息（分页查询）
	 * @param page
	 * @param e
	 * @return
	 */
	public Page<EmployeeReport> queryEmployeeReport(Page<EmployeeReport> page, EmployeeReport e) {
		e.setPage(page);
		page.setList(dao.queryEmployeeReport(e));
		return page;
	}
	/**
	 * 查询员工报表信息
	 * @param page
	 * @param e
	 * @return
	 */
	public List<EmployeeReport> queryEmployeeReport(EmployeeReport e) {
		return dao.queryEmployeeReport(e);
	}
	/**
	 * 查询员工月份汇总
	 * @param em
	 * @return
	 */
	public List<MonthAmountSumReport> queryMonthAmountSum(EmployeeReport em) {
		return dao.queryMonthAmountSum(em);
	}
	

}
