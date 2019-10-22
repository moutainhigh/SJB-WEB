/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.sys.dao.SysVersionDao;
import com.sijibao.oa.modules.sys.entity.SysVersion;

/**
 * 系统版本维护Service
 * @author wanxb
 * @version 2018-06-06
 */
@Service
@Transactional(readOnly = true)
public class SysVersionService extends CrudService<SysVersionDao, SysVersion> {

	public SysVersion get(String id) {
		return super.get(id);
	}
	
	public List<SysVersion> findList(SysVersion sysVersion) {
		return super.findList(sysVersion);
	}
	
	public Page<SysVersion> findPage(Page<SysVersion> page, SysVersion sysVersion) {
		return super.findPage(page, sysVersion);
	}
	
	@Transactional(readOnly = false)
	public void save(SysVersion sysVersion) {
		super.save(sysVersion);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysVersion sysVersion) {
		super.delete(sysVersion);
	}

    public String queryNewVersion() {
		return dao.queryNewVersion();
    }
}