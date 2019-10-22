/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ContractFlow;

/**
 * 合同管理流程DAO接口
 * @author wanxb
 * @version 2018-07-05
 */
@MyBatisDao
public interface ContractFlowDao extends CrudDao<ContractFlow> {
	
	/**
	 * 根据流程编号查询合同管理信息
	 * @param contractFlow
	 * @return
	 */
	ContractFlow getByProcCode(ContractFlow contractFlow);
	ContractFlow getByProcCode(String procCode);
	
	/**
	 * 根据流程实例查询合同管理信息
	 * @param contractFlow
	 * @return
	 */
	ContractFlow getByProcInsId(ContractFlow contractFlow);
	ContractFlow getByProcInsId(String procInsId);
	
	/**
	 * 更新合同管理流程状态 
	 * @param contractFlow
	 */
	void updateContractFlowStatus(ContractFlow contractFlow);
	
	/**
	 * 更新合同管理状态和审批结束时间 
	 * @param contractFlow
	 */
	void updateContractStatusAndFlowFinshTime(ContractFlow contractFlow);
	
	/**
	 * 物理删除合同管理信息
	 * @param contractFlow
	 */
	void deleteContractFlowInfo(ContractFlow contractFlow);
	
	/**
	 * 更新合同编号
	 * @param contractFlow
	 */
	void updateContractCode(ContractFlow contractFlow);
}