package sijibao.oa.jeesite.modules.sys.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.LuckyResult;

/**
 * 年会答题结果DAO接口
 * @author xby
 * @version 2019-01-14
 */
@MyBatisDao
public interface LuckyResultDao extends CrudDao<LuckyResult> {

	List<LuckyResult> queryResultDesc();
	void deleteAll();
}