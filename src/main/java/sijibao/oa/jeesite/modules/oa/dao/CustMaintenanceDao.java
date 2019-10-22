/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.CustMaintenance;

/**
 * 贸易结构DAO接口
 * @author wanxb
 * @version 2018-05-31
 */
@MyBatisDao
public interface CustMaintenanceDao extends CrudDao<CustMaintenance> {

	void deleteByCustId(String custId);
	
}