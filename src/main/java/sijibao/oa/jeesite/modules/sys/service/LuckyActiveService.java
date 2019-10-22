package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.sys.dao.LuckyActiveDao;
import com.sijibao.oa.modules.sys.entity.LuckyActive;

/**
 * 年会活动Service
 * @author xby
 * @version 2019-01-14
 */
@Service
@Transactional(readOnly = true)
public class LuckyActiveService extends CrudService<LuckyActiveDao, LuckyActive> {

	public LuckyActive get(String id) {
		return super.get(id);
	}
	
	public List<LuckyActive> findList(LuckyActive luckyActive) {
		return super.findList(luckyActive);
	}
	
	public Page<LuckyActive> findPage(Page<LuckyActive> page, LuckyActive luckyActive) {
		return super.findPage(page, luckyActive);
	}
	
	@Transactional(readOnly = false)
	public void save(LuckyActive luckyActive) {
		super.save(luckyActive);
	}
	
	@Transactional(readOnly = false)
	public void delete(LuckyActive luckyActive) {
		super.delete(luckyActive);
	}
	
}