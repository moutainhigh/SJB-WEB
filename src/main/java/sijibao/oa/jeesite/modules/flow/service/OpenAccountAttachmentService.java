/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.persistence.Page;
import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.OpenAccountAttachmentDao;
import com.sijibao.oa.modules.flow.entity.OpenAccountAttachment;

/**
 * 附件信息Service
 * @author wanxb
 * @version 2018-07-03
 */
@Service
@Transactional(readOnly = true)
public class OpenAccountAttachmentService extends CrudService<OpenAccountAttachmentDao, OpenAccountAttachment> {
	@Autowired
	private OpenAccountAttachmentDao openAccountAttachmentDao;
	public OpenAccountAttachment get(String id) {
		return super.get(id);
	}
	
	public List<OpenAccountAttachment> findList(OpenAccountAttachment openAccountAttachment) {
		return super.findList(openAccountAttachment);
	}
	
	public Page<OpenAccountAttachment> findPage(Page<OpenAccountAttachment> page, OpenAccountAttachment openAccountAttachment) {
		return super.findPage(page, openAccountAttachment);
	}
	
	@Transactional(readOnly = false)
	public void save(OpenAccountAttachment openAccountAttachment) {
		super.save(openAccountAttachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(OpenAccountAttachment openAccountAttachment) {
		super.delete(openAccountAttachment);
	}

	public void deleteAttachments(String openAccountCode) {
		openAccountAttachmentDao.deleteAttachments(openAccountCode);
		
	}
	
}