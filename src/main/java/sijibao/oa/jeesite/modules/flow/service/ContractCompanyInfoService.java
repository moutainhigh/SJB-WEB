/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.flow.dao.ContractCompanyInfoDao;
import com.sijibao.oa.modules.flow.entity.ContractCompanyInfo;
import com.sijibao.oa.modules.oa.utils.CodeUtils;

/**
 * 合同公司信息Service
 * @author xby
 * @version 2018-07-12
 */
@Service
@Transactional(readOnly = true)
public class ContractCompanyInfoService extends CrudService<ContractCompanyInfoDao, ContractCompanyInfo> {
	@Autowired
	private ContractCompanyInfoDao contractCompanyInfoDao;
	
	public ContractCompanyInfo get(String id) {
		return super.get(id);
	}
	
	public List<ContractCompanyInfo> findList(ContractCompanyInfo contractCompanyInfo) {
		return super.findList(contractCompanyInfo);
	}
	
	public Page<ContractCompanyInfo> findPage(Page<ContractCompanyInfo> page, ContractCompanyInfo contractCompanyInfo) {
		return super.findPage(page, contractCompanyInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractCompanyInfo contractCompanyInfo) {
		if(StringUtils.isBlank(contractCompanyInfo.getContractCompanyCode())){
			String code = CodeUtils.genCode("S", DateUtils.getDate("yyyyMMdd"), 3);
			contractCompanyInfo.setContractCompanyCode(code);
		}
		super.save(contractCompanyInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractCompanyInfo contractCompanyInfo) {
		super.delete(contractCompanyInfo);
	}

	public ContractCompanyInfo getContractName(String contractName) {
		return contractCompanyInfoDao.getContractName(contractName);
	}
	
	/**
	 * 根据合同类型编码查询公司信息
	 * @param contractTypeKey
	 * @return
	 */
	public ContractCompanyInfo getForContractCompanyCode(String contractCompanyCode){
		return contractCompanyInfoDao.getForContractCompanyCode(contractCompanyCode);
	}
	
}