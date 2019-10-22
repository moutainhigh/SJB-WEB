/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.ContractFlowDao;
import com.sijibao.oa.modules.flow.entity.ContractFlow;

/**
 * 合同管理流程Service
 * @author wanxb
 * @version 2018-07-05
 */
@Service
@Transactional(readOnly = true)
public class ContractFlowService extends CrudService<ContractFlowDao, ContractFlow> {

	public ContractFlow get(String id) {
		return super.get(id);
	}
	
	public List<ContractFlow> findList(ContractFlow contractFlow) {
		return super.findList(contractFlow);
	}
	
	public Page<ContractFlow> findPage(Page<ContractFlow> page, ContractFlow contractFlow) {
		String sql = dataScopeFilter(contractFlow.getUser(), "o", "u");
		contractFlow.setSql(sql);
		return super.findPage(page, contractFlow);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractFlow contractFlow) {
		super.save(contractFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractFlow contractFlow) {
		super.delete(contractFlow);
	}
	
	/**
	 * 根据流程编号查询合同管理信息
	 * @param contractFlow
	 * @return
	 */
	public ContractFlow getByProcCode(ContractFlow contractFlow){
		return dao.getByProcCode(contractFlow);
	}
	
	/**
	 * 根据流程编号查询合同管理信息
	 * @param contractFlow
	 * @return
	 */
	public ContractFlow getByProcCode(String procCode){
		return dao.getByProcCode(procCode);
	}
	
	/**
	 * 根据流程实例查询合同管理信息
	 * @param contractFlow
	 * @return
	 */
	public ContractFlow getByProcInsId(ContractFlow contractFlow){
		return dao.getByProcInsId(contractFlow);
	}
	
	/**
	 * 根据流程实例查询合同管理信息
	 * @param contractFlow
	 * @return
	 */
	public ContractFlow getByProcInsId(String procInsId){
		return dao.getByProcInsId(procInsId);
	}
}