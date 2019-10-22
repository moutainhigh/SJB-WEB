/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.ApproveTimeInfoDao;
import com.sijibao.oa.modules.flow.entity.ApproveTimeInfo;

/**
 * 审批时长统计数据Service
 * @author huangkai
 * @version 2019-06-06
 */
@Service
@Transactional(readOnly = true)
public class ApproveTimeInfoService extends CrudService<ApproveTimeInfoDao, ApproveTimeInfo> {

	public ApproveTimeInfo get(String id) {
		return super.get(id);
	}
	
	public List<ApproveTimeInfo> findList(ApproveTimeInfo flow) {
		return super.findList(flow);
	}

	@Transactional(readOnly = false)
	public void save(ApproveTimeInfo flow) {
		super.save(flow);
	}

}