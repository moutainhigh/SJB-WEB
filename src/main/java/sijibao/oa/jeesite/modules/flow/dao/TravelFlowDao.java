/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.TravelFlow;

/**
 * 出差申请DAO接口
 * @author xuby
 * @version 2018-05-23
 */
@MyBatisDao
public interface TravelFlowDao extends CrudDao<TravelFlow> {
	/**
	 * 更新出差申请状态信息
	 * @param travelFlow
	 * @return
	 */
	public int updateTravelStatus(TravelFlow travelFlow);
	
	/**
	 * 更新出差申请状态及审批结束时间信息
	 * @param travelFlow
	 * @return
	 */
	public int updateTravelStatusAndFlowFinshTime(TravelFlow travelFlow);
	/**
	 * 删除出差信息
	 * @param travelFlow
	 * @return
	 */
	public int  deleteTravelFlowInfo(TravelFlow travelFlow);
	
	
	/**
	 * 根据流程实例ID查询出差申请信息
	 * @param procInsId
	 * @return
	 */
	public TravelFlow getByProcInsId(String procInsId);
	
	/**
	 * 根据流程编号查询出差申请信息
	 * @param procCode
	 * @return
	 */
	public TravelFlow getByProcCode(String procCode);

    /**
     * 查询单个用户创建的未完结的出差申请流程的数量
     */
    int findMyApplyTravelFlowCount(TravelFlow r);
}