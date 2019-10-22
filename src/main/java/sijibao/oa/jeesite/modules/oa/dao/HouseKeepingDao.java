/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.HouseKeeping;

/**
 * 内务管理DAO接口
 * @author Petter
 * @version 2017-12-24
 */
@MyBatisDao
public interface HouseKeepingDao extends CrudDao<HouseKeeping> {
	
	Integer checkMonth(@Param(value = "userId") String userId, @Param(value = "workDate") String workDate);

	HouseKeeping getByUserIdAndWordDate(@Param(value = "userId") String userId, @Param(value = "workDate") String workDate);

	List<HouseKeeping> findMonthList(HouseKeeping houseKeeping);

	List<HouseKeeping> findDayList(HouseKeeping houseKeeping);

	HouseKeeping getById(@Param(value = "id") String id);
	
}