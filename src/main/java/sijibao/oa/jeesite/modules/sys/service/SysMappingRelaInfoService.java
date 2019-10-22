package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.modules.sys.dao.SysMappingRelaInfoDao;
import com.sijibao.oa.modules.sys.entity.SysMappingRelaInfo;
import com.sijibao.oa.modules.sys.utils.MappingRelaUtils;

/**
 * 映射关系信息管理Service
 * @author Petter
 * @version 2016-03-07
 */
@Service
@Transactional(readOnly = true)
public class SysMappingRelaInfoService extends CrudService<SysMappingRelaInfoDao, SysMappingRelaInfo> {
	
	public SysMappingRelaInfo get(String id) {
		return super.get(id);
	}
	
//	public SysMappingRelaInfo getByTypeNValue(String type,String value){
//		SysMappingRelaInfo queryObj = new SysMappingRelaInfo();
//		queryObj.setMappingType(type);
//		queryObj.setMappingValue(value);
//		return dao.getByTypeNValue(queryObj);
//	}
//	
//	public SysMappingRelaInfo getByValueOrName(String type,String value, String name){
//		return dao.getByValueOrName(type, name, value);
//	}
	
	public List<SysMappingRelaInfo> findList(SysMappingRelaInfo sysMappingRelaInfo) {
		return super.findList(sysMappingRelaInfo);
	}
	
	public SysMappingRelaInfo findMapping(SysMappingRelaInfo sysMappingRelaInfo){
		List<SysMappingRelaInfo> list = findList(sysMappingRelaInfo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public Page<SysMappingRelaInfo> findPage(Page<SysMappingRelaInfo> page, SysMappingRelaInfo sysMappingRelaInfo) {
		return super.findPage(page, sysMappingRelaInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SysMappingRelaInfo sysMappingRelaInfo) {
		super.save(sysMappingRelaInfo);
		CacheUtils.remove(MappingRelaUtils.CACHE_MAPPING_RELA_MAP);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysMappingRelaInfo sysMappingRelaInfo) {
		super.delete(sysMappingRelaInfo);
		CacheUtils.remove(MappingRelaUtils.CACHE_MAPPING_RELA_MAP);
	}
	
}