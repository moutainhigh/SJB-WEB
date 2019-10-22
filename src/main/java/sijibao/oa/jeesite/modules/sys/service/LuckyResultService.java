package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.sys.dao.LuckyResultDao;
import com.sijibao.oa.modules.sys.entity.LuckyResult;

/**
 * 年会答题结果Service
 * @author xby
 * @version 2019-01-14
 */
@Service
@Transactional(readOnly = true)
public class LuckyResultService extends CrudService<LuckyResultDao, LuckyResult> {

	public LuckyResult get(String id) {
		return super.get(id);
	}
	
	public List<LuckyResult> findList(LuckyResult luckyResult) {
		return super.findList(luckyResult);
	}
	
	public List<LuckyResult> queryResultDesc() {
		return dao.queryResultDesc();
	}
	
	public Page<LuckyResult> findPage(Page<LuckyResult> page, LuckyResult luckyResult) {
		return super.findPage(page, luckyResult);
	}
	
	@Transactional(readOnly = false)
	public void save(LuckyResult luckyResult) {
		super.save(luckyResult);
	}
	
	@Transactional(readOnly = false)
	public void delete(LuckyResult luckyResult) {
		super.delete(luckyResult);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll(){
		dao.deleteAll();
	}
}