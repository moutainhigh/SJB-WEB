/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.CustMergeDao;
import com.sijibao.oa.modules.oa.entity.CustMerge;


/**
 * 客户信息表Service
 * @author wanxb
 * @version 2018-05-25
 */
@Service
@Transactional(readOnly = true)
public class CustMergeService extends CrudService<CustMergeDao, CustMerge> {
	
	public CustMerge get(String id) {
		return super.get(id);
	}
	/**
	 * 获取列表
	 */
	public List<CustMerge> findList(CustMerge custMerge) {
		return super.findList(custMerge);
	}
	/**
	 * 分页查询
	 */
	public Page<CustMerge> findPage(Page<CustMerge> page, CustMerge custMerge) {
		return super.findPage(page, custMerge);
	}
	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save(CustMerge custMerge) {
		super.save(custMerge);
	}
	/**
	 * 删除
	 */
	@Transactional(readOnly = false)
	public void delete(CustMerge custMerge) {
		super.delete(custMerge);
	}
	
}