/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.RecpParamsDao;
import com.sijibao.oa.modules.flow.entity.RecpParams;

/**
 * 招待申请参数Service
 * @author xuby
 * @version 2018-04-17
 */
@Service
@Transactional(readOnly = true)
public class RecpParamsService extends CrudService<RecpParamsDao, RecpParams> {

	public RecpParams get(String id) {
		return super.get(id);
	}
	
	public List<RecpParams> findList(RecpParams recpParams) {
		return super.findList(recpParams);
	}
	
	public Page<RecpParams> findPage(Page<RecpParams> page, RecpParams recpParams) {
		return super.findPage(page, recpParams);
	}
	
	@Transactional(readOnly = false)
	public void save(RecpParams recpParams) {
		super.save(recpParams);
	}
	
	@Transactional(readOnly = false)
	public void delete(RecpParams recpParams) {
		super.delete(recpParams);
	}

    public List<RecpParams> findListForApp(RecpParams recpParams) {
		return dao.findListForApp(recpParams);
    }
}