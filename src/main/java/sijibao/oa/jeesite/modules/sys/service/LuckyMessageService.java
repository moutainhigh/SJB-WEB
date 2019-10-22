package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.sys.dao.LuckyMessageDao;
import com.sijibao.oa.modules.sys.entity.LuckyMessage;

/**
 * 年会消息Service
 * @author xby
 * @version 2019-01-14
 */
@Service
@Transactional(readOnly = true)
public class LuckyMessageService extends CrudService<LuckyMessageDao, LuckyMessage> {

	public LuckyMessage get(String id) {
		return super.get(id);
	}
	
	public List<LuckyMessage> findList(LuckyMessage luckyMessage) {
		return super.findList(luckyMessage);
	}
	
	public Page<LuckyMessage> findPage(Page<LuckyMessage> page, LuckyMessage luckyMessage) {
		return super.findPage(page, luckyMessage);
	}
	
	@Transactional(readOnly = false)
	public void save(LuckyMessage luckyMessage) {
		super.save(luckyMessage);
	}
	
	@Transactional(readOnly = false)
	public void delete(LuckyMessage luckyMessage) {
		super.delete(luckyMessage);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll(){
		dao.deleteAll();
	}
}