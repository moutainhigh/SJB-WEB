/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ContractAttachment;

/**
 * 合同附件DAO接口
 * @author wanxb
 * @version 2018-07-05
 */
@MyBatisDao
public interface ContractAttachmentDao extends CrudDao<ContractAttachment> {
	
	/**
	 * 根据合同编号删除合同附件信息
	 * @param contractAttachment
	 */
	void deleteContractCode(String contractCode);
}