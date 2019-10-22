/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao.report;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.report.MonthAmountSumReport;
import com.sijibao.oa.modules.flow.entity.report.SubjectCostReport;

/**
 * 科目费用报表DAO接口
 * @author xby
 * @version 2018-07-13
 */
@MyBatisDao
public interface SubjectCostReportDao extends CrudDao<SubjectCostReport> {
	
	/**
	 * 查询系统中所有科目信息
	 * @param subjectCostReport
	 * @return
	 */
	List<SubjectCostReport> querySubjectInfo(SubjectCostReport subjectCostReport);
	
	/**
	 * 根据项目ID查询一级科目信息
	 * @param subjectCostReport
	 * @return
	 */
	List<SubjectCostReport> querySubjectInfoForProjectId(SubjectCostReport subjectCostReport);
	
	/**
	 * 查询一级科目的费用金额
	 * @param subjectCostReport
	 * @return
	 */
	List<SubjectCostReport> querySubjectCostReport(SubjectCostReport subjectCostReport);
	
	/**
	 * 查询二级科目的费用金额
	 * @param subjectCostReport
	 * @return
	 */
	List<SubjectCostReport> querySecondSubjectCostReport(SubjectCostReport subjectCostReport);
	
	/**
	 * 查询一级科目报表月度汇总金额
	 * @param subjectCostReport
	 * @return
	 */
	List<MonthAmountSumReport> queryMonthAmountSum(SubjectCostReport subjectCostReport);
	
	/**
	 * 查询二级科目报表月度汇总金额
	 * @param subjectCostReport
	 * @return
	 */
	List<MonthAmountSumReport> querySecondSubjectMonthAmountReport(SubjectCostReport subjectCostReport);
}