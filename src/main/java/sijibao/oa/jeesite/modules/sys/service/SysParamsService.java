package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.modules.sys.dao.SysParamsDao;
import com.sijibao.oa.modules.sys.entity.SysParams;


/**
 * 系统参数管理Service
 * @author Petter
 * @version 2016-04-13
 */
@Service
@Transactional(readOnly = true)
public class SysParamsService extends CrudService<SysParamsDao, SysParams> {

	public SysParams get(String id) {
		return super.get(id);
	}
	
	public List<SysParams> findList(SysParams sysParams) {
		return super.findList(sysParams);
	}
	
	public Page<SysParams> findPage(Page<SysParams> page, SysParams sysParams) {
		return super.findPage(page, sysParams);
	}
	
	@Transactional(readOnly = false)
	public void save(SysParams sysParams) {
		super.save(sysParams);
		CacheUtils.remove(CacheUtils.SYS_CACHE_PARAMS_MAP);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysParams sysParams) {
		super.delete(sysParams);
		CacheUtils.remove(CacheUtils.SYS_CACHE_PARAMS_MAP);
	}
	
	@Transactional(readOnly = false)
	public void deleteAll(){
		dao.deleteAll();
		CacheUtils.remove(CacheUtils.SYS_CACHE_PARAMS_MAP);
	}
	
}