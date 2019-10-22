package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.sys.dao.LuckyWeightDao;
import com.sijibao.oa.modules.sys.entity.LuckyWeight;

/**
 * 年会体重信息Service
 * @author xby
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class LuckyWeightService extends CrudService<LuckyWeightDao, LuckyWeight> {

	public LuckyWeight get(String id) {
		return super.get(id);
	}
	
	public List<LuckyWeight> findList(LuckyWeight luckyWeight) {
		return super.findList(luckyWeight);
	}
	
	public Page<LuckyWeight> findPage(Page<LuckyWeight> page, LuckyWeight luckyWeight) {
		return super.findPage(page, luckyWeight);
	}
	
	@Transactional(readOnly = false)
	public void save(LuckyWeight luckyWeight) {
		super.save(luckyWeight);
	}
	
	@Transactional(readOnly = false)
	public void delete(LuckyWeight luckyWeight) {
		super.delete(luckyWeight);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll(){
		dao.deleteAll();
	}
	
	public LuckyWeight getForName(LuckyWeight luckyWeight){
		return dao.getForName(luckyWeight);
	}
}