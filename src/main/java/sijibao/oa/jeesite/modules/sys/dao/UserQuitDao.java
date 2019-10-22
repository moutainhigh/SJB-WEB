/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.dao;

import java.util.List;
import java.util.Map;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.UserQuit;

/**
 * 离职表DAO接口
 * @author wanxb
 * @version 2019-01-04
 */
@MyBatisDao
public interface UserQuitDao extends CrudDao<UserQuit> {

	void batchInsert(List<UserQuit> list);

	int queryCustInfoByUserId(String id);

	int queryProjectByUserId(String id);

	int queryContractByUserId(String id);

	int queryNeedByUserId(String id);

	int queryExpenseFlow(String id);

	List<UserQuit> queryQuit(String id);

	List<String> queryHandovers(String id);

	void deleteQuit(String id);

	int queryExpenseFlowForMy(String id);
	
	int queryExpenseFlowForMyNeed(String id);

    int queryLeaderCount(String id);

	List<String> queryRoleLeader(String id);

	List<Map<String,String>> queryMap(String id);
}