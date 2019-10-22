/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.act.entity.TaskInfoEntity;
import com.sijibao.oa.modules.act.service.ActTaskService;
import com.sijibao.oa.modules.flow.dao.FlowProcOrgDao;
import com.sijibao.oa.modules.flow.entity.FlowProcOrg;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 流程机构Service
 * @author xby
 * @version 2018-06-25
 */
@Service
@Transactional(readOnly = true)
public class FlowProcOrgService extends CrudService<FlowProcOrgDao, FlowProcOrg> {
	
	@Autowired
	private ActTaskService actTaskService;
	
	/**
	 * 根据ID查询流程机构信息
	 */
	public FlowProcOrg get(String id) {
		return super.get(id);
	}
	
	/**
	 * 查询流程机构配置
	 */
	public List<FlowProcOrg> findList(FlowProcOrg flowProcOrg) {
		return super.findList(flowProcOrg);
	}
	
	/**
	 * 分页查询流程机构配置
	 */
	public Page<FlowProcOrg> findPage(Page<FlowProcOrg> page, FlowProcOrg flowProcOrg) {
		Page<FlowProcOrg> resultPage = super.findPage(page, flowProcOrg);
		for(FlowProcOrg f:resultPage.getList()){
			f.setBillTypeName(DictUtils.getDictLabel(f.getBillType(), "oa_bill_type", ""));
			f.setProcCode(DictUtils.getDictLabel(f.getProcCode(), "act_category", ""));
		}
		return super.findPage(page, flowProcOrg);
	}
	
	/**
	 * 保存流程机构信息
	 */
	@Transactional(readOnly = false)
	public void save(FlowProcOrg flowProcOrg) {
		String procCode = flowProcOrg.getProcCode();
		TaskInfoEntity taskInfoEntity = actTaskService.queryActModel(procCode);
		flowProcOrg.setProcKey(taskInfoEntity.getProcKey());
		super.save(flowProcOrg);
	}
	
	/**
	 * 删除流程机构信息
	 */
	@Transactional(readOnly = false)
	public void delete(FlowProcOrg flowProcOrg) {
		super.delete(flowProcOrg);
	}
	
	/**
	 * 根据业务单据的申请人机构ID查询流程机构配置信息
	 * @param busOrgId
	 * @return
	 */
	public List<FlowProcOrg> findProcOrgForBusOrgId(String busOrgId,String billType){
		return dao.findProcOrgForBusOrgId(busOrgId,billType);
	}
	
}