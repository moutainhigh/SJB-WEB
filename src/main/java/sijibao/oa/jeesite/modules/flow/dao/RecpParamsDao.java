/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.RecpParams;

/**
 * 找到申请参数DAO接口
 * @author xuby
 * @version 2018-04-17
 */
@MyBatisDao
public interface RecpParamsDao extends CrudDao<RecpParams> {
	
	/**
	 * 根据流程编号删除招待申请参数表信息
	 * @param procCode
	 */
	void deleteForProcCode(String procCode);

    List<RecpParams> findListForApp(RecpParams recpParams);
}