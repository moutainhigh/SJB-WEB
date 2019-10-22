package sijibao.oa.jeesite.modules.flow.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.report.OfficeCostReportDao;
import com.sijibao.oa.modules.flow.entity.report.OfficeCostReport;
import com.sijibao.oa.modules.sys.dao.OfficeDao;
import com.sijibao.oa.modules.sys.entity.Office;

/**
 * 部门费用报表Service
 * @author xby
 *
 */
@Service
@Transactional(readOnly = true)
public class OfficeCostReportService extends CrudService<OfficeCostReportDao, OfficeCostReport> {
	
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private OfficeCostReportDao officeCostReportDao;
	
	/**
	 * 查询部门信息分页
	 * @param page
	 * @param office
	 * @return
	 */
	public Page<Office> queryOfficeInfo(Page<Office> page,Office office){
		office.setPage(page);
		page.setList(officeDao.queryOfficInfoForGrade(office));
		return page;
	}
	
	public List<Office> queryOfficeInfo(Office office){
		
		return officeDao.queryOfficInfoForGrade(office);
	}
	
	/**
	 * 查询部门费用信息
	 * @param officeCostReport
	 * @return
	 */
	public List<OfficeCostReport> queryOfficeCostInfo(OfficeCostReport officeCostReport){
		return officeCostReportDao.queryOfficeCostInfo(officeCostReport);
	}
	
	/**
	 * 查询部门费用月度汇总金额
	 * @param officeCostReport
	 * @return
	 */
	public List<OfficeCostReport> queryOfficeMonthAmounySumCostInfo(OfficeCostReport officeCostReport){
		return officeCostReportDao.queryOfficeMonthAmounySumCostInfo(officeCostReport);
	}
}
