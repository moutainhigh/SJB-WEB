/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.EmployeeDailyDao;
import com.sijibao.oa.modules.oa.entity.EmployeeDaily;

/**
 * 工作日志Service
 * @author wanxb
 * @version 2018-05-31
 */
@Service
@Transactional(readOnly = true)
public class EmployeeDailyService extends CrudService<EmployeeDailyDao, EmployeeDaily> {

	public EmployeeDaily get(String id) {
		return super.get(id);
	}
	/**
	 * 列表页面
	 */
	public List<EmployeeDaily> findList(EmployeeDaily employeeDaily) {
		return super.findList(employeeDaily);
	}
	/**
	 * 分页查询
	 * 
	 */
	public Page<EmployeeDaily> findPage(Page<EmployeeDaily> page, EmployeeDaily employeeDaily) {
		String sql = dataScopeFilter(employeeDaily.getUser(),"o","u");
		employeeDaily.setSql(sql);
		return super.findPage(page, employeeDaily);
	}
	/**
	 * 保存页面
	 */
	@Transactional(readOnly = false)
	public void save(EmployeeDaily employeeDaily) {
		super.save(employeeDaily);
	}
	/**
	 * 删除页面
	 */
	@Transactional(readOnly = false)
	public void delete(EmployeeDaily employeeDaily) {
		super.delete(employeeDaily);
	}
	
}