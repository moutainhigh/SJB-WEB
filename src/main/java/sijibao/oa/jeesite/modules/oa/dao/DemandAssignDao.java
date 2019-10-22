/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;


import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.DemandAssign;

/**
 * 指派记录表DAO接口
 * @author xuby
 * @version 2018-03-28
 */
@MyBatisDao
public interface DemandAssignDao extends CrudDao<DemandAssign> {
	
	/**
	 * 根据被指派人员查询发起指派人员
	 * @param demandAssign
	 * @return
	 */
	DemandAssign querySourceAssignByTargetAssign(DemandAssign demandAssign);
	
	/**
	 * 根据被指派人员查询发起指派人员
	 * @param demandAssign
	 * @return
	 */
	DemandAssign querySourceAssignByTargetAssignAndprocInsId(DemandAssign demandAssign);
	
	/**
	 * 物理删除人员指派记录
	 * @param procCode
	 */
	void deleteByProcCode(String procCode);
}