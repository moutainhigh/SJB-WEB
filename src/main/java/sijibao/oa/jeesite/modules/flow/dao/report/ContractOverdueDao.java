/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao.report;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ContractOverdue;

/**
 * 合同逾期报表DAO接口
 * @author huangkai
 * @version 2019-03-04
 */
@MyBatisDao
public interface ContractOverdueDao extends CrudDao<ContractOverdue> {

    ContractOverdue queryContractOverdue(ContractOverdue contractOverdue);
}