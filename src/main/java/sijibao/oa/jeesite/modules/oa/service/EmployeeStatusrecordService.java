package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.EmployeeStatusrecordDao;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusrecord;

/**
 * 员工状态记录Service
 * @author xuby
 * @version 2018-09-14
 */
@Service
@Transactional(readOnly = true)
public class EmployeeStatusrecordService extends CrudService<EmployeeStatusrecordDao, EmployeeStatusrecord> {
	
	@Autowired
	private EmployeeStatusrecordDao employeeStatusrecordDao;
	
	public EmployeeStatusrecord get(String id) {
		return super.get(id);
	}
	
	public List<EmployeeStatusrecord> findList(EmployeeStatusrecord EmployeeStatusrecord) {
		return super.findList(EmployeeStatusrecord);
	}
	
	public Page<EmployeeStatusrecord> findPage(Page<EmployeeStatusrecord> page, EmployeeStatusrecord EmployeeStatusrecord) {
		return super.findPage(page, EmployeeStatusrecord);
	}
	
	@Transactional(readOnly = false)
	public void save(EmployeeStatusrecord EmployeeStatusrecord) {
		super.save(EmployeeStatusrecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(EmployeeStatusrecord EmployeeStatusrecord) {
		super.delete(EmployeeStatusrecord);
	}
	
	public List<EmployeeStatusrecord> queryEmpStatusDayReportInfo(EmployeeStatusrecord employeeStatusrecord){
		return employeeStatusrecordDao.queryEmpStatusDayReportInfo(employeeStatusrecord);
	}
	
	public EmployeeStatusrecord getNewTimeForUserId(EmployeeStatusrecord employeeStatusrecord){
		return employeeStatusrecordDao.getNewTimeForUserId(employeeStatusrecord);
	}
	
	/**
	 * 查询包含项目中的最后一条状态状态中数据
	 * @param employeeStatusrecord
	 * @return
	 */
	public List<EmployeeStatusrecord> queryEmpStatusProjectLastInfo(EmployeeStatusrecord employeeStatusrecord){
		return employeeStatusrecordDao.queryEmpStatusProjectLastInfo(employeeStatusrecord);
	}
	
	/**
	 * 查询不包含项目中的人员最后一条状态数据
	 * @param employeeStatusrecord
	 * @return
	 */
	public List<EmployeeStatusrecord> queryEmpStatusLastInfo(EmployeeStatusrecord employeeStatusrecord){
		return employeeStatusrecordDao.queryEmpStatusLastInfo(employeeStatusrecord);
	}
	
	/**
	 * 根据用户更新报表处理状态
	 * @param employeeStatusrecord
	 */
	@Transactional(readOnly = false)
	public void updateReportStatusForUser(EmployeeStatusrecord employeeStatusrecord){
		employeeStatusrecordDao.updateReportStatusForUser(employeeStatusrecord);
	}
}