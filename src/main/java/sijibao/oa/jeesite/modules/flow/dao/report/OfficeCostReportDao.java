/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao.report;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.report.OfficeCostReport;

/**
 * 部门费用报表DAO接口
 * @author xby
 * @version 2018-07-13
 */
@MyBatisDao
public interface OfficeCostReportDao extends CrudDao<OfficeCostReport> {
	
	/**
	 * 查询部门费用信息
	 * @param office
	 * @return
	 */
	List<OfficeCostReport> queryOfficeCostInfo(OfficeCostReport officeCostReport);
	
	/**
	 * 查询部门费用月度汇总金额
	 * @param officeCostReport
	 * @return
	 */
	List<OfficeCostReport> queryOfficeMonthAmounySumCostInfo(OfficeCostReport officeCostReport);
	
}