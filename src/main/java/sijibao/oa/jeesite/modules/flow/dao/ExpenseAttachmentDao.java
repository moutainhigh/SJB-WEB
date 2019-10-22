/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ExpenseAttachment;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;

/**
 * 费用附件DAO接口
 * @author xuby
 * @version 2017-12-24
 */
@MyBatisDao
public interface ExpenseAttachmentDao extends CrudDao<ExpenseAttachment> {
	/**
	 * 根据报销流程编码查询附件信息
	 * @param procCode
	 * @return
	 */
	public ExpenseAttachment getByProcCode(String procCode);
	
	/**
	 * 根据报销流程编码删除附件信息
	 * @param expenseAttachment
	 * @return
	 */
	public int  deleteForExpenseCode(ExpenseAttachment expenseAttachment);
	
	/**
	 * 根据报销流程编码删除所有科目附件信息
	 * @param expenseAttachment
	 * @return
	 */
	public int  deleteForExpenseCodeAndExpenseDetailId(ExpenseAttachment expenseAttachment);
	
	
	/**
	 * 根据流程编号和科目编号查询附件信息 
	 * @param expenseDetail
	 * @return
	 */
	public List<ExpenseAttachment> queryAttachmentForEcodeAndSubCode(ExpenseDetail expenseDetail);
}