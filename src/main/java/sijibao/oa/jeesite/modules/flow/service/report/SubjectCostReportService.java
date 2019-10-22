package sijibao.oa.jeesite.modules.flow.service.report;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.report.SubjectCostReportDao;
import com.sijibao.oa.modules.flow.entity.report.MonthAmountSumReport;
import com.sijibao.oa.modules.flow.entity.report.SubjectCostReport;

/**
 * 科目费用报表Service
 * @author xby
 */
@Service
@Transactional(readOnly = true)
public class SubjectCostReportService extends CrudService<SubjectCostReportDao, SubjectCostReport> {
		
	/**
	 * 查询科目信息(分页)
	 * @param page
	 * @param subjectCostReport
	 * @return
	 */
	public Page<SubjectCostReport> querySubjectInfo(Page<SubjectCostReport> page,SubjectCostReport subjectCostReport){
		subjectCostReport.setPage(page);
		page.setList(dao.querySubjectInfo(subjectCostReport));
		return page;
	}
	
	/**
	 * 查询科目信息
	 * @param page
	 * @param subjectCostReport
	 * @return
	 */
	public List<SubjectCostReport> querySubjectInfo(SubjectCostReport subjectCostReport){
	
		return dao.querySubjectInfo(subjectCostReport);
	}
	
	
	/**
	 * 根据项目ID查询科目信息(分页)
	 * @param page
	 * @param subjectCostReport
	 * @return
	 */
	public Page<SubjectCostReport> querySubjectInfoForProjectId(Page<SubjectCostReport> page,SubjectCostReport subjectCostReport){
		subjectCostReport.setPage(page);
		page.setList(dao.querySubjectInfoForProjectId(subjectCostReport));
		return page;
	}
	
	/**
	 * 根据项目ID查询科目信息
	 * @param page
	 * @param subjectCostReport
	 * @return
	 */
	public List<SubjectCostReport> querySubjectInfoForProjectId(SubjectCostReport subjectCostReport){
	
		return dao.querySubjectInfoForProjectId(subjectCostReport);
	}
	
	
	/**
	 * 查询一级科目费用金额
	 * @param subjectCostReport
	 * @return
	 */
	public List<SubjectCostReport> querySubjectCostReport(SubjectCostReport subjectCostReport){
		return dao.querySubjectCostReport(subjectCostReport);
	}
	
	/**
	 * 查询二级科目费用金额
	 * @param subjectCostReport
	 * @return
	 */
	public List<SubjectCostReport> querySecondSubjectCostReport(SubjectCostReport subjectCostReport){
		return dao.querySecondSubjectCostReport(subjectCostReport);
	}
	
	/**
	 * 查询科目报表月度汇总金额
	 * @param subjectCostReport
	 * @return
	 */
	public List<MonthAmountSumReport> queryMonthAmountSum(SubjectCostReport subjectCostReport){
		return dao.queryMonthAmountSum(subjectCostReport);
	}
	
	/**
	 * 查询二级科目报表月度汇总金额
	 * @param subjectCostReport
	 * @return
	 */
	public List<MonthAmountSumReport> querySecondSubjectMonthAmountReport(SubjectCostReport subjectCostReport){
		return dao.querySecondSubjectMonthAmountReport(subjectCostReport);
	}
}
