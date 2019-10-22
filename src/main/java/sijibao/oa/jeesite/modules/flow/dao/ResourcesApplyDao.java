/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ResourcesApply;

/**
 * 资源申请DAO接口
 * @author xuby
 * @version 2018-05-30
 */
@MyBatisDao
public interface ResourcesApplyDao extends CrudDao<ResourcesApply> {
	
	/**
	 *  更新资源申请状态
	 * @param resourcesApply
	 */
	void updateResApplyStatus(ResourcesApply resourcesApply);
	
	/**
	 * 更新资源申请状态和审批结束时间
	 * @param resourcesApply
	 */
	void updateResApplyStatusAndFlowFinshTime(ResourcesApply resourcesApply);
	
	/**
	 * 物理删除资源申请信息 
	 * @param resourcesApply
	 */
	void deleteResApplyInfo(ResourcesApply resourcesApply);
	
	/**
	 * 根据流程实例ID查询资源申请信息
	 * @param resourcesApply
	 * @return
	 */
	ResourcesApply getByProcInsId(String procInsId);
	
	/**
	 * 根据流程编号查询资源申请信息
	 * @param resourcesApply
	 * @return
	 */
	ResourcesApply getByProcCode(String procCode);
	
	/**
	 * 查询资源申请关联列表
	 * @param resourcesApply
	 * @return
	 */
	List<ResourcesApply> findRelationList(ResourcesApply resourcesApply);

    /**
     * 查询单个用户创建的未完结的资源申请流程的数量
     */
    int findMyApplyResourcesApplyCount(ResourcesApply r);
}