/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.OpenAccountFlow;

/**
 * 开户管理DAO接口
 * @author wanxb
 * @version 2018-07-02
 */
@MyBatisDao
public interface OpenAccountFlowDao extends CrudDao<OpenAccountFlow> {
	/**
	 * 更新开户申请状态和审批结束时间
	 * @param resourcesApply
	 */
	void updateOpenAccountFlowStatus(OpenAccountFlow open);

	void updateStatusAndFlowFinshTime(OpenAccountFlow open);

	OpenAccountFlow getByProcInsId(String procInsId);
}