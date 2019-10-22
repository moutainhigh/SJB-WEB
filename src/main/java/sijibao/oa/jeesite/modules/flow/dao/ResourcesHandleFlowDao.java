/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ResourcesAssign;
import com.sijibao.oa.modules.flow.entity.ResourcesHandleFlow;

/**
 * 资源办理办理AO接口
 * @author xuby
 * @version 2018-05-29
 */
@MyBatisDao
public interface ResourcesHandleFlowDao extends CrudDao<ResourcesHandleFlow> {
	/**
	 *  更新资源办理状态
	 * @param resourcesHandle
	 */
	void updateResHandleStatus(ResourcesHandleFlow resourcesHandleFlow);
	
	/**
	 * 更新资源办理状态和审批结束时间
	 * @param resourcesHandle
	 */
	void updateResHandleStatusAndFlowFinshTime(ResourcesHandleFlow resourcesHandleFlow);
	
	/**
	 * 物理删除资源办理信息 
	 * @param resourcesHandle
	 */
	void deleteResHandleInfo(ResourcesHandleFlow resourcesHandleFlow);
	
	/**
	 * 根据流程实例ID查询资源办理信息
	 * @param resourcesHandle
	 * @return
	 */
	ResourcesHandleFlow getByProcInsId(String procInsId);
	
	/**
	 * 根据流程编号查询资源办理信息
	 * @param resourcesHandle
	 * @return
	 */
	ResourcesHandleFlow getByProcCode(String procCode);
	/**
	 * 根据流程编号查找指派列表
	 * @param procCode
	 * @return
	 */
	List<ResourcesAssign> findResourcesAssignByProcCode(String procCode);
	
	/**
	 * 查询资源办理关联列表
	 * @param resourcesHandleFlow
	 * @return
	 */
	List<ResourcesHandleFlow> findRelationList(ResourcesHandleFlow resourcesHandleFlow);

    /**
     * 查询单个用户创建的未完结的资源办理流程的数量
     */
    int findMyApplyResourcesHandleCount(ResourcesHandleFlow r);
}