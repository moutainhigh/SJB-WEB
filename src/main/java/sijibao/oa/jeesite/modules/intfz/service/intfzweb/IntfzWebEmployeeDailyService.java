package sijibao.oa.jeesite.modules.intfz.service.intfzweb;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.request.employeedaily.EmployeeDailyHandleReq;
import com.sijibao.oa.modules.intfz.request.employeedaily.EmployeeDailySaveReq;
import com.sijibao.oa.modules.intfz.response.employeedaily.EmployeeDailyDetailResponse;
import com.sijibao.oa.modules.intfz.response.employeedaily.EmployeeDailyResponse;
import com.sijibao.oa.modules.oa.dao.EmployeeDailyDao;
import com.sijibao.oa.modules.oa.entity.EmployeeDaily;
import com.sijibao.oa.modules.oa.service.EmployeeDailyService;
import com.sijibao.oa.modules.oa.utils.CodeUtils;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 工作日志service
 * @author wanxb
 *
 */
@Service
@Transactional(readOnly = true)
public class IntfzWebEmployeeDailyService  {
	@Autowired
	private EmployeeDailyService employeeDailyService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private EmployeeDailyDao employeeDailyDao;
	/**
	 * 列表查询，带搜索条件
	 * @param page
	 * @param req
	 * @return
	 */
	public Page<EmployeeDailyResponse> findPage(Page<EmployeeDaily> page, EmployeeDailyHandleReq req, User user) {
		EmployeeDaily e = new EmployeeDaily();
		if(StringUtils.isNotBlank(req.getMarketLeaderName())){
			e.setMarketLeaderName(req.getMarketLeaderName());
		}
		if(req.getBeginTime() != 0l){
			e.setBeginTime(DateUtils.parseDateFormUnix(req.getBeginTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		if(req.getEndTime() != 0l){
			e.setEndTime(DateUtils.parseDateFormUnix(req.getEndTime(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		}
		e.setUser(user);
		if(StringUtils.isNotBlank(req.getMarketLeaderId())){
			e.setMarketLeader(UserUtils.get(req.getMarketLeaderId()));
		}
		Page<EmployeeDaily> findPage = employeeDailyService.findPage(page, e);
		Page<EmployeeDailyResponse> newPage = new Page<EmployeeDailyResponse>();
		newPage.setPageNo(findPage.getPageNo());
		newPage.setPageSize(findPage.getPageSize());
		newPage.setCount(findPage.getCount());
		ArrayList<EmployeeDailyResponse> list = Lists.newArrayList();
		for (EmployeeDaily cu : findPage.getList()) {
			EmployeeDailyResponse em = new EmployeeDailyResponse();
			em.setId(cu.getId());
			em.setDailyDate(cu.getDailyDate().getTime());
			if(cu.getMarketLeader() != null && StringUtils.isNotBlank(cu.getMarketLeader().getId())){
				cu.setMarketLeader(UserUtils.get(cu.getMarketLeader().getId()));
				em.setMarketLeaderName(cu.getMarketLeader().getName());
			}
			if(cu.getOffice() != null){
				em.setOfficeName(cu.getOffice().getName());
			}
			em.setCustVisitCount(cu.getCustVisitCount());
			em.setCustIntentionCount(cu.getCustIntentionCount());
			em.setCustSignCount(cu.getCustSignCount());
			em.setAfterVisitCount(cu.getAfterVisitCount());
			em.setRemarks(cu.getRemarks());
			if(cu.getCreateTime() != null){
				em.setCreateTime(cu.getCreateTime().getTime());
			}
			
			list.add(em);
		}
		newPage.setList(list);
		return newPage;
		
	}
	/**
	 * 详细页面
	 * @param id
	 * @return
	 */
	public EmployeeDailyDetailResponse getEmployeeDailyById(String id) {
		EmployeeDaily cu = employeeDailyService.get(id);
		EmployeeDailyDetailResponse d= new EmployeeDailyDetailResponse();
		d.setId(cu.getId());
		if(cu.getDailyDate() != null){
			d.setDailyDate(cu.getDailyDate().getTime());
		}
		if(cu.getMarketLeader() != null && StringUtils.isNotBlank(cu.getMarketLeader().getId())){
			cu.setMarketLeader(UserUtils.get(cu.getMarketLeader().getId()));
			d.setMarketLeaderId(cu.getMarketLeader().getId());
			d.setMarketLeaderName(cu.getMarketLeader().getName());
		}
		if(cu.getOffice() != null){
			d.setOfficeId(cu.getOffice().getId());
			d.setOfficeName(cu.getOffice().getName());
		}
		d.setRemarks(cu.getRemarks());
		d.setCustVisitCount(cu.getCustVisitCount());
		d.setCustIntentionCount(cu.getCustIntentionCount());
		d.setCustSignCount(cu.getCustSignCount());
		d.setAfterVisitCount(cu.getAfterVisitCount());
		d.setDailyContent(cu.getDailyContent());
		d.setAfterDailyPlan(cu.getAfterDailyPlan());
		return d;
		
	}
	/**
	 * 保存日志信息
	 * @param req
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void saveEmployeeDaily(EmployeeDailySaveReq req,User user){
		EmployeeDaily emp = new EmployeeDaily();
		if(StringUtils.isNotBlank(req.getId()) && !"0".equals(req.getId())){
			emp = employeeDailyService.get(req.getId());
			emp.preUpdateForWechart(user);
		}else{
			emp.setDailyCode(CodeUtils.genCode("E", DateUtils.getDate("yyyyMMdd"), 3));
			emp.preInsertForWeChart(user);
		}
		if(req.getDailyDate() != 0l){
			emp.setDailyDate(DateUtils.parseDateFormUnix(req.getDailyDate(), DateUtils.YYYY_MM_DD));
		}
		emp.setMarketLeader(user);
		if(StringUtils.isNotBlank(req.getOfficeId())){
			emp.setOffice(officeService.get(req.getOfficeId()));
		}
		emp.setCustVisitCount(req.getCustVisitCount());
		emp.setCustSignCount(req.getCustSignCount());
		emp.setCustIntentionCount(req.getCustIntentionCount());
		emp.setAfterVisitCount(req.getAfterVisitCount());
		emp.setDailyContent(req.getDailyContent());
		emp.setAfterDailyPlan(req.getAfterDailyPlan());
		emp.setRemarks(req.getRemarks());
		if(StringUtils.isBlank(req.getId()) && !"0".equals(req.getId())){
			employeeDailyDao.insert(emp);
		}else{
			employeeDailyDao.update(emp);
		}
	}
	/**
	 * 删除工作日志信息
	 * @param employeeDaily
	 */
	@Transactional(readOnly = false)
	public void deleteEmployeeDaily(EmployeeDaily employeeDaily){
		employeeDailyService.delete(employeeDaily);
	}
}
