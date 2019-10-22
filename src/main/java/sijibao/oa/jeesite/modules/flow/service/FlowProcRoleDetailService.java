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
import com.sijibao.oa.common.service.ServiceException;
import com.sijibao.oa.modules.flow.dao.FlowProcRoleDetailDao;
import com.sijibao.oa.modules.flow.entity.FlowProcRoleDetail;

/**
 * 流程角色明细表Service
 * @author xby
 * @version 2018-06-20
 */
@Service
@Transactional(readOnly = true)
public class FlowProcRoleDetailService extends CrudService<FlowProcRoleDetailDao, FlowProcRoleDetail> {
	
	@Autowired
	private FlowProcRoleResolveService flowProcRoleResolveService;
	
	public FlowProcRoleDetail get(String id) {
		return super.get(id);
	}
	
	public List<FlowProcRoleDetail> findList(FlowProcRoleDetail flowProcRoleDetail) {
		return super.findList(flowProcRoleDetail);
	}
	
	public Page<FlowProcRoleDetail> findPage(Page<FlowProcRoleDetail> page, FlowProcRoleDetail flowProcRoleDetail) {
		return super.findPage(page, flowProcRoleDetail);
	}
	
	/**
	 * 保存流程角色明细数据和明细解析数据
	 */
	@Transactional(readOnly = false)
	public void save(FlowProcRoleDetail flowProcRoleDetail) {
	    //校验机构是否已经配置了（流程角色）审批人员
        FlowProcRoleDetail paramObj = new FlowProcRoleDetail();
        paramObj.setMainId(flowProcRoleDetail.getMainId());
        paramObj.setOrgId(flowProcRoleDetail.getOrgId());
        List<FlowProcRoleDetail> list = dao.findList(paramObj);

        if(list.size() == 0){
            super.save(flowProcRoleDetail);
        } else if(list.size() == 1){
            if(flowProcRoleDetail.getIsNewRecord()){
                throw new ServiceException("此管辖机构已配置了审批人员");
            } else {
                super.save(flowProcRoleDetail);
            }
        } else {
            throw new ServiceException("此管辖机构已配置了超过一位审批人员，请检查并修复数据");
        }

        //保存明细解析数据
        //flowProcRoleResolveService.batchSave(flowProcRoleDetail);//TODO oa_flow_proc_role_resolve这张表废弃了，不解析，直接查oa_flow_proc_role_detail表获取数据
	}
	
	@Transactional(readOnly = false)
	public void delete(FlowProcRoleDetail flowProcRoleDetail) {
		super.delete(flowProcRoleDetail);
	}
	
}