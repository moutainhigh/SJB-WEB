/**
\ * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.DemandManagement;

/**
 * 需求管理生成DAO接口
 * @author xuby
 * @version 2018-03-28
 */
@MyBatisDao
public interface DemandManagementDao extends CrudDao<DemandManagement> {
	/**
	 * 更新需求申请状态
	 * @param demandManagement
	 */
	void updateDemandStatus(DemandManagement demandManagement);
	
	/**
	 * 更新需求申请状态及审批结束时间
	 * @param demandManagement
	 */
	void updateDemandStatusAndFlowFinshTime(DemandManagement demandManagement);
	
	/**
	 * 物理删除需求申请信息
	 * @param demandManagement
	 */
	void deleteDemandInfo(DemandManagement demandManagement);
	/**
	 * 根据流程实例ID获取需求表业务信息
	 * @param procInsId
	 * @return
	 */
	DemandManagement getDemandByProcInsId(String procInsId);
}