/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.OpenAccountAttachment;
import com.sijibao.oa.modules.flow.entity.OpenAccountFlow;

/**
 * 附件信息DAO接口
 * @author wanxb
 * @version 2018-07-03
 */
@MyBatisDao
public interface OpenAccountAttachmentDao extends CrudDao<OpenAccountAttachment> {

	void deleteAttachments(String openAccountCode);

	List<OpenAccountAttachment> findByopenAccountCode(OpenAccountFlow open);

	
}