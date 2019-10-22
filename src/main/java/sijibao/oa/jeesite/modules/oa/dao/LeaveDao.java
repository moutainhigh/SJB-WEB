package sijibao.oa.jeesite.modules.oa.dao;

import sijibao.oa.jeesite.modules.oa.entity.Leave;
import sijibao.oa.jeesite.persistence.CrudDao;
import sijibao.oa.jeesite.persistence.annotation.MyBatisDao;

/**
 * 请假DAO接口
 * @author liuj
 * @version 2013-8-23
 */
@MyBatisDao
public interface LeaveDao extends CrudDao<Leave> {
	
	/**
	 * 更新流程实例ID
	 * @param leave
	 * @return
	 */
	public int updateProcessInstanceId(Leave leave);
	
	/**
	 * 更新实际开始结束时间
	 * @param leave
	 * @return
	 */
	public int updateRealityTime(Leave leave);
	
}
