package sijibao.oa.jeesite.modules.flow.service.report;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.report.ProjectReportDao;
import com.sijibao.oa.modules.flow.entity.report.MonthAmountSumReport;
import com.sijibao.oa.modules.flow.entity.report.ProjectReport;

/**
 * 项目费用报表Service
 * @author wanxb
 */
@Service
@Transactional(readOnly = true)
public class ProjectReportService extends CrudService<ProjectReportDao, ProjectReport> {
		
	/**
	 * 查询项目报表信息
	 * @param page
	 * @param e
	 * @return
	 */
	public Page<ProjectReport> queryProjectReport(Page<ProjectReport> page, ProjectReport e) {
		e.setPage(page);
		page.setList(dao.queryProjectReport(e));
		return page;
	}
	/**
	 * 查询项目报表信息(不带分页)
	 * @param page
	 * @param e
	 * @return
	 */
	public List<ProjectReport> queryProjectReport(ProjectReport e) {
		
		return dao.queryProjectReport(e);

	}
	/**
	 * 项目报表月度汇总
	 * @param e
	 * @return
	 */
	public List<MonthAmountSumReport> queryMonthAmountSum(ProjectReport e) {
		return dao.queryMonthAmountSum(e);
	}
}
