/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.modules.intfz.response.bi.DictRespForBI;
import com.sijibao.oa.modules.sys.dao.DictDao;
import com.sijibao.oa.modules.sys.entity.Dict;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	public List<DictRespForBI> queryDictTableForBI(){
	    return dao.findAllColumnsForBI();
    }

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(CacheUtils.SYS_CACHE_DICT_MAP);
		CacheUtils.remove(CacheUtils.WFW_SYS_CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(CacheUtils.SYS_CACHE_DICT_MAP);
		CacheUtils.remove(CacheUtils.WFW_SYS_CACHE_DICT_MAP);
	}

}
