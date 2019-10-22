package sijibao.oa.jeesite.modules.sys.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.LuckyVote;

/**
 * 年会投票DAO接口
 * @author xby
 * @version 2019-01-24
 */
@MyBatisDao
public interface LuckyVoteDao extends CrudDao<LuckyVote> {
	
	/**
	 * 查询投票结果
	 * @param luckyVote
	 * @return
	 */
	List<LuckyVote> queryVoteResultList(LuckyVote luckyVote);
	
	void deleteAll();
}