/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.oa.dao.CustInfoClueDao;
import com.sijibao.oa.modules.oa.entity.CustInfoClue;

/**
 * 客户线索Service
 * @author wanxb
 * @version 2018-05-29
 */
@Service
@Transactional(readOnly = true)
public class CustInfoClueService extends CrudService<CustInfoClueDao, CustInfoClue> {

	public CustInfoClue get(String id) {
		return super.get(id);
	}
	/**
	 * 列表查询
	 */
	public List<CustInfoClue> findList(CustInfoClue custInfoClue) {
		return super.findList(custInfoClue);
	}
	/**
	 * 分页查询
	 */
	public Page<CustInfoClue> findPage(Page<CustInfoClue> page, CustInfoClue custInfoClue) {
		String sql = dataScopeFilter(custInfoClue.getUser(),"o","u");
		custInfoClue.setSql(sql);
		return super.findPage(page, custInfoClue);
	}
	/**
	 * 保存
	 */
	@Transactional(readOnly = false)
	public void save(CustInfoClue custInfoClue) {
		super.save(custInfoClue);
	}
	/**
	 * 删除
	 */
	@Transactional(readOnly = false)
	public void delete(CustInfoClue custInfoClue) {
		super.delete(custInfoClue);
	}
	
}