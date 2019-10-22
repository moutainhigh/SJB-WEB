package sijibao.oa.jeesite.modules.sys.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.LuckyMessage;

/**
 * 年会消息DAO接口
 * @author xby
 * @version 2019-01-14
 */
@MyBatisDao
public interface LuckyMessageDao extends CrudDao<LuckyMessage> {
	void deleteAll();
}