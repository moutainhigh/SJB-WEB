/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.DemandAssignDao;
import com.sijibao.oa.modules.oa.entity.DemandAssign;

/**
 * 指派记录表Service
 * @author xuby
 * @version 2018-03-28
 */
@Service
@Transactional(readOnly = true)
public class DemandAssignService extends CrudService<DemandAssignDao, DemandAssign> {

	public DemandAssign get(String id) {
		return super.get(id);
	}
	
	public List<DemandAssign> findList(DemandAssign demandAssign) {
		return super.findList(demandAssign);
	}
	
	public Page<DemandAssign> findPage(Page<DemandAssign> page, DemandAssign demandAssign) {
		return super.findPage(page, demandAssign);
	}
	
	@Transactional(readOnly = false)
	public void save(DemandAssign demandAssign) {
		super.save(demandAssign);
	}
	
	@Transactional(readOnly = false)
	public void delete(DemandAssign demandAssign) {
		super.delete(demandAssign);
	}
	
	/**
	 * 根据流程编号物理删除人员指派记录
	 * @param procCode
	 */
	@Transactional(readOnly = false)
	public void deleteByProcCode(String procCode){
		dao.deleteByProcCode(procCode);
	}
	
	/**
	 * 根据被指派人员查询发起指派人员
	 * @param demandAssign
	 * @return
	 */
	public DemandAssign querySourceAssignByTargetAssign(DemandAssign demandAssign){
		return dao.querySourceAssignByTargetAssign(demandAssign);
	}
	
	/**
	 * 根据被指派人员查询发起指派人员
	 * @param demandAssign
	 * @return
	 */
	public List<DemandAssign> querySourceAssignByTargetAssignList(DemandAssign demandAssign, List<DemandAssign> resultList) {
		DemandAssign d = dao.querySourceAssignByTargetAssignAndprocInsId(demandAssign);
		resultList.add(d);
		if(d != null && StringUtils.isNotBlank(d.getSourceTaskName())){
			if(!Constant.DEMAND_MAIN_NODE.equals(d.getSourceTaskName())){
				demandAssign.setTargetAssign(d.getSourceAssign());
				demandAssign.setTargetTaskId(d.getSourceTaskId());
				resultList = this.querySourceAssignByTargetAssignList(demandAssign, resultList);
			}else{
				return resultList;
			}
		}
		return resultList;
	}
	
}