/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.FlowProcOrg;

/**
 * 流程机构DAO接口
 * @author xby
 * @version 2018-06-25
 */
@MyBatisDao
public interface FlowProcOrgDao extends CrudDao<FlowProcOrg> {
	/**
	 * 根据业务单据的申请人机构ID查询流程机构配置信息
	 * @param busOrgId
	 * @return
	 */
	List<FlowProcOrg> findProcOrgForBusOrgId(String busOrgId, String billType);
}