/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.BatchMoveHiDao;
import com.sijibao.oa.modules.oa.entity.BatchMoveHi;

/**
 * 客户批量移动历史Service
 * @author wanxb
 * @version 2018-08-20
 */
@Service
@Transactional(readOnly = true)
public class BatchMoveHiService extends CrudService<BatchMoveHiDao, BatchMoveHi> {

	public BatchMoveHi get(String id) {
		return super.get(id);
	}
	
	public List<BatchMoveHi> findList(BatchMoveHi batchMoveHi) {
		return super.findList(batchMoveHi);
	}
	
	public Page<BatchMoveHi> findPage(Page<BatchMoveHi> page, BatchMoveHi batchMoveHi) {
		return super.findPage(page, batchMoveHi);
	}
	
	@Transactional(readOnly = false)
	public void save(BatchMoveHi batchMoveHi) {
		super.save(batchMoveHi);
	}
	
	@Transactional(readOnly = false)
	public void delete(BatchMoveHi batchMoveHi) {
		super.delete(batchMoveHi);
	}
	
}