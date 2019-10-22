/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.FlowProcRoleResolve;

/**
 * 流程角色明细解析DAO接口
 * @author xby
 * @version 2018-06-20
 */
@MyBatisDao
public interface FlowProcRoleResolveDao extends CrudDao<FlowProcRoleResolve> {
	/**
	 * 根据明细ID删除解析数据
	 * @param detailId
	 */
	void deleteForDetailId(String detailId);
	
	/**
	 * 根据流程角色编码和机构ID查询审批人员信息 
	 * @param flowProcRoleResolve
	 * @return
	 */
	List<FlowProcRoleResolve> queryUserForRoleCodeAndOrgId(FlowProcRoleResolve flowProcRoleResolve);

	List<String> queryUserForRoleCode(String string);

	List<FlowProcRoleResolve> queryUserByOrgId(FlowProcRoleResolve flowProcRoleResolve);
}