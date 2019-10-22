package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.sys.dao.LuckyUserDao;
import com.sijibao.oa.modules.sys.entity.LuckyUser;

/**
 * 年会用户Service
 * @author xby
 * @version 2019-01-14
 */
@Service
@Transactional(readOnly = true)
public class LuckyUserService extends CrudService<LuckyUserDao, LuckyUser> {

	public LuckyUser get(String id) {
		return super.get(id);
	}
	
	public List<LuckyUser> findList(LuckyUser luckyUser) {
		return super.findList(luckyUser);
	}
	
	public Page<LuckyUser> findPage(Page<LuckyUser> page, LuckyUser luckyUser) {
		return super.findPage(page, luckyUser);
	}
	
	@Transactional(readOnly = false)
	public void save(LuckyUser luckyUser) {
		super.save(luckyUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(LuckyUser luckyUser) {
		super.delete(luckyUser);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll(){
		dao.deleteAll();
	}
}