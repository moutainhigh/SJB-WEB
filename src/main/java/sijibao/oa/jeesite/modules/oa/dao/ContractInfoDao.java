/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.ContractInfo;

/**
 * 合同信息DAO接口
 * @author wanxb
 * @version 2018-07-24
 */
@MyBatisDao
public interface ContractInfoDao extends CrudDao<ContractInfo> {
	void deleteContractInfoByCustId(String id);

	ContractInfo findContractInfoByCsutId(String id);

	List<ContractInfo> getCreditCode(String creditCode);
}