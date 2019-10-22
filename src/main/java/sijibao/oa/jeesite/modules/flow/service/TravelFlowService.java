/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.TravelFlowDao;
import com.sijibao.oa.modules.flow.entity.TravelFlow;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 出差申请Service
 * @author xuby
 * @version 2018-05-23
 */
@Service
@Transactional(readOnly = true)
public class TravelFlowService extends CrudService<TravelFlowDao, TravelFlow> {

	public TravelFlow get(String id) {
		return super.get(id);
	}
	
	public List<TravelFlow> findList(TravelFlow oaTravelFlow) {
		List<TravelFlow> resultList = super.findList(oaTravelFlow);
		for(TravelFlow r:resultList){
			r.setTravelStatusValue(DictUtils.getDictLabel(r.getTravelStatus(), "expense_status", "")); //设置单据状态
		}
		return resultList;
	}
	
	public Page<TravelFlow> findPage(Page<TravelFlow> page, TravelFlow travelFlow) {
		Page<TravelFlow> resultPage = super.findPage(page, travelFlow);
		for(TravelFlow r:resultPage.getList()){
			r.setTravelStatusValue(DictUtils.getDictLabel(r.getTravelStatus(), "expense_status", "")); //设置单据状态
		}
		return resultPage;
	}
	
	@Transactional(readOnly = false)
	public void save(TravelFlow oaTravelFlow) {
		super.save(oaTravelFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(TravelFlow oaTravelFlow) {
		super.delete(oaTravelFlow);
	}
	
	
	/**
	 * 根据流程实例ID查询出差申请信息
	 * @param procInsId
	 * @return
	 */
	public TravelFlow getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}
	
	/**
	 * 根据流程实例ID查询出差申请信息
	 * @param procInsId
	 * @return
	 */
	public TravelFlow getByProcCode(String procCode){
		return dao.getByProcCode(procCode);
	}
}