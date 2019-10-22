/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.ExpensesSubConf;

/**
 * 费用科目配置管理DAO接口
 * @author Petter
 * @version 2017-12-24
 */
@MyBatisDao
public interface ExpensesSubConfDao extends CrudDao<ExpensesSubConf> {
	
}