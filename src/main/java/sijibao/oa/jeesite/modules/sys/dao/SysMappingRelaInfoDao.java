package sijibao.oa.jeesite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.sys.entity.SysMappingRelaInfo;

/**
 * 映射关系信息管理DAO接口
 * @author Petter
 * @version 2016-03-07
 */
@MyBatisDao
public interface SysMappingRelaInfoDao extends CrudDao<SysMappingRelaInfo> {
	
	/**
	 * 通过类型和映射值查映射关系
	 * @param type
	 * @param value
	 * @return
	 */
	public SysMappingRelaInfo getByTypeNValue(SysMappingRelaInfo queryInfo);
	
	/**
	 * function: 通过类型和映射值或映射名称查映射关系
	 * @param mappingType
	 * @param mappingName
	 * @param mappingValue
	 * @return
	 */
	public SysMappingRelaInfo getByValueOrName(@Param("mappingType") String mappingType, @Param("mappingName") String mappingName, @Param("mappingValue") String mappingValue);
	
}