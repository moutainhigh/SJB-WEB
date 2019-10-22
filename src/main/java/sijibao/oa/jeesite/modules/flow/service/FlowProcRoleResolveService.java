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
import com.sijibao.oa.modules.flow.dao.FlowProcRoleResolveDao;
import com.sijibao.oa.modules.flow.entity.FlowProcRoleDetail;
import com.sijibao.oa.modules.flow.entity.FlowProcRoleResolve;
import com.sijibao.oa.modules.sys.entity.Office;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.service.OfficeService;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 流程角色明细解析Service
 * @author xby
 * @version 2018-06-20
 */
@Service
@Transactional(readOnly = true)
public class FlowProcRoleResolveService extends CrudService<FlowProcRoleResolveDao, FlowProcRoleResolve> {
	
	@Autowired
	private OfficeService officeService;
	
	public FlowProcRoleResolve get(String id) {
		return super.get(id);
	}
	
	public List<FlowProcRoleResolve> findList(FlowProcRoleResolve flowProcRoleResolve) {
		return super.findList(flowProcRoleResolve);
	}
	
	public Page<FlowProcRoleResolve> findPage(Page<FlowProcRoleResolve> page, FlowProcRoleResolve flowProcRoleResolve) {
		return super.findPage(page, flowProcRoleResolve);
	}
	
	/**
	 * 保存明细解析数据（废弃）
	 * @param flowProcRoleDetail
	 */
	public void batchSave(FlowProcRoleDetail flowProcRoleDetail){
		//删除之前的明细解析数据
		dao.deleteForDetailId(flowProcRoleDetail.getId());
		
		User user = UserUtils.get(flowProcRoleDetail.getUserId());
		Office office = officeService.get(flowProcRoleDetail.getOrgId());
		//添加自己本部门
		FlowProcRoleResolve reso = new FlowProcRoleResolve();
		reso.setUserLoginName(user.getLoginName());
		reso.setUserName(user.getName());
		reso.setOrgId(office.getId());
		reso.setOrgName(office.getName());
		reso.setDetailId(flowProcRoleDetail.getId());
		this.save(reso);
		List<Office> officeList = officeService.findListLike(office);
		if(officeList != null && officeList.size() > 0){
			for(Office o:officeList){
				FlowProcRoleResolve flowProcRoleResolve = new FlowProcRoleResolve();
				flowProcRoleResolve.setUserLoginName(user.getLoginName());
				flowProcRoleResolve.setUserName(user.getName());
				flowProcRoleResolve.setOrgId(o.getId());
				flowProcRoleResolve.setOrgName(o.getName());
				flowProcRoleResolve.setDetailId(flowProcRoleDetail.getId());
				this.save(flowProcRoleResolve);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void save(FlowProcRoleResolve flowProcRoleResolve) {
		super.save(flowProcRoleResolve);
	}
	
	@Transactional(readOnly = false)
	public void delete(FlowProcRoleResolve flowProcRoleResolve) {
		super.delete(flowProcRoleResolve);
	}
	
	@Transactional(readOnly = false)
	public void deleteForDetailId(String detailId){
		dao.deleteForDetailId(detailId);
	}
}