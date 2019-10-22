package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.intfz.request.bi.QueryEmployeeStatusRecordTableForBIReq;
import com.sijibao.oa.modules.intfz.response.bi.EmployStatusRecordRespForBI;
import com.sijibao.oa.modules.oa.entity.EmployeeStatusrecord;
import com.sijibao.oa.modules.sys.entity.User;

/**
 * 员工状态记录DAO接口
 * @author xuby
 * @version 2018-09-14
 */
@MyBatisDao
public interface EmployeeStatusrecordDao extends CrudDao<EmployeeStatusrecord> {
	
	/**
	 * 根据用户ID获取用户最新的一条变更记录
	 * @param employeeStatusrecord
	 * @return
	 */
	EmployeeStatusrecord getNewTimeForUserId(EmployeeStatusrecord employeeStatusrecord);
	
	/**
	 * 用户状态日报表查询
	 * @param employeeStatusrecord
	 * @return
	 */
	List<EmployeeStatusrecord> queryEmpStatusDayReportInfo(EmployeeStatusrecord employeeStatusrecord);
	
	/**
	 * 查询包含项目中的最后一条状态状态中数据
	 * @param employeeStatusrecord
	 * @return
	 */
	List<EmployeeStatusrecord> queryEmpStatusProjectLastInfo(EmployeeStatusrecord employeeStatusrecord);
	
	/**
	 * 查询不包含项目中的人员最后一条状态数据
	 * @param employeeStatusrecord
	 * @return
	 */
	List<EmployeeStatusrecord> queryEmpStatusLastInfo(EmployeeStatusrecord employeeStatusrecord);
	
	/**
	 * 根据用户更新报表处理状态
	 * @param employeeStatusrecord
	 */
	void updateReportStatusForUser(EmployeeStatusrecord employeeStatusrecord);
	/**
	 * 查询离职人员是否有实施信息
	 * @param user
	 * @return
	 */
	List<EmployeeStatusrecord> queryEmployee(User user);

    /**
     * 查询全部字段数据
     * @param req
     * @return
     */
    List<EmployStatusRecordRespForBI> findAllColumnsForBI(QueryEmployeeStatusRecordTableForBIReq req);
}