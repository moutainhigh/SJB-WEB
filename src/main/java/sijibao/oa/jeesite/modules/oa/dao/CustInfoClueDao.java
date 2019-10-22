/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.CustInfoClue;

/**
 * 线索表DAO接口
 * @author wanxb
 * @version 2018-05-29
 */
@MyBatisDao
public interface CustInfoClueDao extends CrudDao<CustInfoClue> {

	List<CustInfoClue> getCustInfoClueByCustName(String custName);
	
}