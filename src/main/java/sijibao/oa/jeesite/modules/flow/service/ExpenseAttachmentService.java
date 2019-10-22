/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.CrudService;
import com.sijibao.oa.modules.flow.dao.ExpenseAttachmentDao;
import com.sijibao.oa.modules.flow.entity.ExpenseAttachment;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;

/**
 * 报销附件Service
 * @author xuby
 * @version 2018-01-22
 */
@Service
@Transactional(readOnly = true)
public class ExpenseAttachmentService extends CrudService<ExpenseAttachmentDao, ExpenseAttachment> {
	
	@Autowired
	private ExpenseAttachmentDao expenseAttachmentDao;
	
	/**
	 * 根据报销流程编码查询附件信息
	 * @param expenseAttachment
	 * @return
	 */
	public List<ExpenseAttachment> findListByProcCode(ExpenseAttachment expenseAttachment){
		List<ExpenseAttachment> resultList = expenseAttachmentDao.findAllList(expenseAttachment);
		return resultList;
	};
	
	/**
	 * 根据流程编号和科目编号查询附件信息 
	 * @param expenseDetail
	 * @return
	 */
	public List<ExpenseAttachment> queryAttachmentForEcodeAndSubCode(ExpenseDetail expenseDetail){
		return expenseAttachmentDao.queryAttachmentForEcodeAndSubCode(expenseDetail);
	}
}