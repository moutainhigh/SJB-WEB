/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.ContractAttachmentDao;
import com.sijibao.oa.modules.flow.entity.ContractAttachment;

/**
 * 合同附件Service
 * @author wanxb
 * @version 2018-07-05
 */
@Service
@Transactional(readOnly = true)
public class ContractAttachmentService extends CrudService<ContractAttachmentDao, ContractAttachment> {

	public ContractAttachment get(String id) {
		return super.get(id);
	}
	
	public List<ContractAttachment> findList(ContractAttachment contractAttachment) {
		return super.findList(contractAttachment);
	}
	
	public Page<ContractAttachment> findPage(Page<ContractAttachment> page, ContractAttachment contractAttachment) {
		return super.findPage(page, contractAttachment);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractAttachment contractAttachment) {
		super.save(contractAttachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractAttachment contractAttachment) {
		super.delete(contractAttachment);
	}
	
	/**
	 * 根据合同编号删除合同附件信息
	 * @param contractAttachment
	 */
	@Transactional(readOnly = false)
	public void deleteContractCode(String contractCode){
		dao.deleteContractCode(contractCode);
	}
}