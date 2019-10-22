/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.RecpFlow;

/**
 * 接待申请DAO接口
 * @author xuby
 * @version 2018-04-17
 */
@MyBatisDao
public interface RecpFlowDao extends CrudDao<RecpFlow> {
	/**
	 * 更新接待申请状态信息
	 * @param recpFlow
	 * @return
	 */
	public int updateRecpStatus(RecpFlow recpFlow);
	
	/**
	 * 更新接待申请状态及审批结束时间信息
	 * @param recpFlow
	 * @return
	 */
	public int updateRecpStatusAndFlowFinshTime(RecpFlow recpFlow);
	/**
	 * 删除接待信息
	 * @param recpFlow
	 * @return
	 */
	public int  deleteRecpFlowInfo(RecpFlow recpFlow);
	
	
	/**
	 * 根据流程实例ID查询接待申请信息
	 * @param procInsId
	 * @return
	 */
	public RecpFlow getByProcInsId(String procInsId);
	
	/**
	 * 根据流程编号查询接待申请信息
	 * @param procCode
	 * @return
	 */
	public RecpFlow getByProcCode(String procCode);

    /**
     * 查询单个用户创建的未完结的接待申请流程的数量
     */
	public int findMyRecpFlowCount(RecpFlow recpFlow);

	public List<String> queryLoginNames(@Param("employees") String[] employees);
}