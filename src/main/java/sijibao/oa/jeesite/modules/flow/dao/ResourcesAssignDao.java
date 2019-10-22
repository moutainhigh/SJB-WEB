package sijibao.oa.jeesite.modules.flow.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ResourcesAssign;

/**
 * 资源申请人员指派DAO接口
 * @author xuby
 * @version 2018-05-29
 */
@MyBatisDao
public interface ResourcesAssignDao extends CrudDao<ResourcesAssign> {
	
	/**
	 * 根据流程编号删除指派人员信息
	 * @param resourcesAssign
	 */
	void deleteForProcCode(ResourcesAssign resourcesAssign);
	
	/**
	 * 根据指派发起人员删除对应的指派数据
	 * @param resourcesAssign
	 */
	void deleteResourcesAssignForSourceAssign(ResourcesAssign resourcesAssign);
}