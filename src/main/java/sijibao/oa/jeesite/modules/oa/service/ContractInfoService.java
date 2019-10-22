/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.ContractInfoDao;
import com.sijibao.oa.modules.oa.entity.ContractInfo;

/**
 * 合同信息Service
 * @author wanxb
 * @version 2018-07-24
 */
@Service
@Transactional(readOnly = true)
public class ContractInfoService extends CrudService<ContractInfoDao, ContractInfo> {

	public ContractInfo get(String id) {
		return super.get(id);
	}
	
	public List<ContractInfo> findList(ContractInfo contractInfo) {
		return super.findList(contractInfo);
	}
	
	public Page<ContractInfo> findPage(Page<ContractInfo> page, ContractInfo contractInfo) {
		return super.findPage(page, contractInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractInfo contractInfo) {
		super.save(contractInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractInfo contractInfo) {
		super.delete(contractInfo);
	}
	
}