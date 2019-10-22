package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.sys.dao.LuckyVoteDao;
import com.sijibao.oa.modules.sys.entity.LuckyVote;

/**
 * 年会投票Service
 * @author xby
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class LuckyVoteService extends CrudService<LuckyVoteDao, LuckyVote> {

	public LuckyVote get(String id) {
		return super.get(id);
	}
	
	public List<LuckyVote> findList(LuckyVote luckyVote) {
		return super.findList(luckyVote);
	}
	
	public Page<LuckyVote> findPage(Page<LuckyVote> page, LuckyVote luckyVote) {
		return super.findPage(page, luckyVote);
	}
	
	@Transactional(readOnly = false)
	public void save(LuckyVote luckyVote) {
		super.save(luckyVote);
	}
	
	@Transactional(readOnly = false)
	public void delete(LuckyVote luckyVote) {
		super.delete(luckyVote);
	}
	
	/**
	 * 查询投票结果
	 * @param luckyVote
	 * @return
	 */
	public List<LuckyVote> queryVoteResultList(LuckyVote luckyVote){
		return dao.queryVoteResultList(luckyVote);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll(){
		dao.deleteAll();
	}
}