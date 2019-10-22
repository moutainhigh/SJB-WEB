/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.TreeDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.Area;

/**
 * 鏍戠粨鏋勭敓鎴怐AO鎺ュ彛
 * @author wanxiongbo
 * @version 2018-03-22
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	/**
	 * 根据编码查询城市信息
	 * @param area
	 * @return
	 */
	public Area getForCode(Area area);

	public List<String> findAreaNameByIds(@Param(value = "areaId") String[] areaId);
}