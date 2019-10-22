/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ContractCompanyInfo;

/**
 * 合同公司信息DAO接口
 * @author xby
 * @version 2018-07-12
 */
@MyBatisDao
public interface ContractCompanyInfoDao extends CrudDao<ContractCompanyInfo> {

	
	/**
	 * 根据合同类型编码查询公司信息
	 * @param contractTypeKey
	 * @return
	 */
	ContractCompanyInfo getForContractCompanyCode(String contractCompanyCode);

	ContractCompanyInfo getContractName(String contractName);
	
}