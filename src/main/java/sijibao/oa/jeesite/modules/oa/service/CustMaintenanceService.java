/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.CustMaintenanceDao;
import com.sijibao.oa.modules.oa.entity.CustMaintenance;


/**
 * 客户维护Service
 * @author wanxb
 * @version 2018-05-31
 */
@Service
@Transactional(readOnly = true)
public class CustMaintenanceService extends CrudService<CustMaintenanceDao, CustMaintenance> {

	public CustMaintenance get(String id) {
		return super.get(id);
	}
	/**
	 * 列表
	 */
	public List<CustMaintenance> findList(CustMaintenance custMaintenance) {
		return super.findList(custMaintenance);
	}
	/**
	 * 分页查询
	 */
	public Page<CustMaintenance> findPage(Page<CustMaintenance> page, CustMaintenance custMaintenance) {
		return super.findPage(page, custMaintenance);
	}
	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save(CustMaintenance custMaintenance) {
		super.save(custMaintenance);
	}
	/**
	 * 删除
	 */
	@Transactional(readOnly = false)
	public void delete(CustMaintenance custMaintenance) {
		super.delete(custMaintenance);
	}
	
}