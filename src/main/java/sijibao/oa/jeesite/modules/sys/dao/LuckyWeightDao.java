package sijibao.oa.jeesite.modules.sys.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.LuckyWeight;

/**
 * 年会体重信息DAO接口
 * @author xby
 * @version 2019-01-24
 */
@MyBatisDao
public interface LuckyWeightDao extends CrudDao<LuckyWeight> {
	
	void deleteAll();
	
	LuckyWeight getForName(LuckyWeight luckyWeight);
}