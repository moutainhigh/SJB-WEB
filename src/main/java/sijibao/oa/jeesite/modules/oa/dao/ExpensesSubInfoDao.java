/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;

/**
 * 费用科目信息管理DAO接口
 * @author Petter
 * @version 2017-12-24
 */
@MyBatisDao
public interface ExpensesSubInfoDao extends CrudDao<ExpensesSubInfo> {
	
	public ExpensesSubInfo getBySubCode(String subCode);

    void enableChange(ExpensesSubInfo expensesSubInfo);

    List<ExpensesSubInfo> findListNew(ExpensesSubInfo expensesSubInfo);

    List<String> queryIdsById(ExpensesSubInfo expensesSubInfo);

    int querySubByIds(@Param("ids") List<String> ids);

    public ExpensesSubInfo findBySubCode(String subCode);
}