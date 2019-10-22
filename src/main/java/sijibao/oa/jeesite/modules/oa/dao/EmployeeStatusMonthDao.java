package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusMonth;

/**
 * 员工状态月度汇总DAO接口
 * @author xuby
 * @version 2018-09-20
 */
@MyBatisDao
public interface EmployeeStatusMonthDao extends CrudDao<EmployeeStatusMonth> {
	
	/**
	 * 查询月报表没有更新过，并且今天最新的一条记录
	 * @param employeeStatusMonth
	 * @return
	 */
	List<EmployeeStatusMonth> queryMonthInfoNoUpdateAndLastInfo(EmployeeStatusMonth employeeStatusMonth);
	
	/**
	 * 员工月报表查询统计 
	 * @param employeeStatusMonth
	 * @return
	 */
	List<EmployeeStatusMonth> queryEmpMonthReportCount(EmployeeStatusMonth employeeStatusMonth);
	
	/**
	 * 员工月报表明细查询统计
	 * @param employeeStatusMonth
	 * @return
	 */
	List<EmployeeStatusMonth> queryEmpMonthDetailReportCount(EmployeeStatusMonth employeeStatusMonth);
	
	/**
	 * 更新员工月报表封存状态
	 * @param EmployeeStatusMonth
	 */
	void updateMonthStatus(EmployeeStatusMonth EmployeeStatusMonth);
	
	/**
	 * 根据更新时间和年月查询月报表数据
	 * @param EmployeeStatusMonth
	 * @return
	 */
	List<EmployeeStatusMonth> findListForUpdateTimeAndDateTime(EmployeeStatusMonth EmployeeStatusMonth);
	
	/**
	 * 删除天数为0的数据
	 * @param EmployeeStatusMonth
	 */
	void deleteMonthZero(EmployeeStatusMonth EmployeeStatusMonth);
}