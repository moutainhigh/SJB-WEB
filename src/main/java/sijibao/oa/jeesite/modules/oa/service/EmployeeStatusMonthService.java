package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.EmployeeStatusMonthDao;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusMonth;

/**
 * 员工状态月度汇总Service
 * @author xuby
 * @version 2018-09-20
 */
@Service
@Transactional(readOnly = true)
public class EmployeeStatusMonthService extends CrudService<EmployeeStatusMonthDao, EmployeeStatusMonth> {

	public EmployeeStatusMonth get(String id) {
		return super.get(id);
	}
	
	public List<EmployeeStatusMonth> findList(EmployeeStatusMonth EmployeeStatusMonth) {
		return super.findList(EmployeeStatusMonth);
	}
	
	public Page<EmployeeStatusMonth> findPage(Page<EmployeeStatusMonth> page, EmployeeStatusMonth EmployeeStatusMonth) {
		return super.findPage(page, EmployeeStatusMonth);
	}
	
	@Transactional(readOnly = false)
	public void save(EmployeeStatusMonth EmployeeStatusMonth) {
		super.save(EmployeeStatusMonth);
	}
	
	@Transactional(readOnly = false)
	public void delete(EmployeeStatusMonth EmployeeStatusMonth) {
		super.delete(EmployeeStatusMonth);
	}
	
	@Transactional(readOnly = false)
	public void updateMonthStatus(EmployeeStatusMonth employeeStatusMonth){
		dao.updateMonthStatus(employeeStatusMonth);
	}
	
	/**
	 * 查询月报表没有更新过，并且今天最新的一条记录
	 * @param employeeStatusMonth
	 * @return
	 */
	public List<EmployeeStatusMonth> queryMonthInfoNoUpdateAndLastInfo(EmployeeStatusMonth employeeStatusMonth){
		return dao.queryMonthInfoNoUpdateAndLastInfo(employeeStatusMonth);
	}
	
	/**
	 * 根据更新时间和年月查询月报表数据
	 * @param EmployeeStatusMonth
	 * @return
	 */
	public List<EmployeeStatusMonth> findListForUpdateTimeAndDateTime(EmployeeStatusMonth EmployeeStatusMonth){
		return dao.findListForUpdateTimeAndDateTime(EmployeeStatusMonth);
	}
	
	/**
	 * 删除天数为0的数据
	 * @param EmployeeStatusMonth
	 */
	@Transactional(readOnly=false)
	public void deleteMonthZero(EmployeeStatusMonth employeeStatusMonth){
		dao.deleteMonthZero(employeeStatusMonth);
	}
}