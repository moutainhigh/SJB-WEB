/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.flow.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.CrudDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.AmtDetail;
import com.sijibao.oa.modules.flow.entity.ExpenseFlow;
import com.sijibao.oa.modules.flow.entity.report.ExpenseReport;
import com.sijibao.oa.modules.sys.entity.ApproveTime;

/**
 * 费用报销DAO接口
 * @author Petter
 * @version 2017-12-24
 */
@MyBatisDao
public interface ExpenseFlowDao extends CrudDao<ExpenseFlow> {
	
	/**
	 * 根据流程实例ID查询报销信息
	 * @param procInsId
	 * @return
	 */
	public ExpenseFlow getByProcInsId(String procInsId);
	
	/**
	 * 根据流程实例ID更新报销信息
	 * @param expenseFlow
	 * @return
	 */
	public int updateInsId(ExpenseFlow expenseFlow);
	
	/**
	 * 更新报销状态信息
	 * @param expenseFlow
	 * @return
	 */
	public int updateExpenseStatus(ExpenseFlow expenseFlow);
	
	/**
	 * 更新报销状态及审批结束时间信息
	 * @param expenseFlow
	 * @return
	 */
	public int updateExpenseStatusAndFlowFinshTime(ExpenseFlow expenseFlow);
	/**
	 * 删除报销信息
	 * @param expenseFlow
	 * @return
	 */
	public int  deleteExpenseFlowInfo(ExpenseFlow expenseFlow);
	
	/**
	 * 查询报销明细信息
	 * @param expenseFlow
	 * @return
	 */
	public List<ExpenseFlow> queryExpenseSubInfoDetail(ExpenseFlow expenseFlow);
	/**
	 * 查询负责人
	 * @param employee
	 * @return
	 */
	public String findManagerForEmployee(String employee);


	public List<String> findDeputyPersonForEmployee(List<String> list);
	public String findDeputyPerson(String employee);

	public String getPostId(ExpenseFlow expenseFlow);
	
	/**
	 * 查询所有报销信息(包含删除单据)
	 * @param expenseFlow
	 * @return
	 */
	public List<ExpenseFlow> findAllListIncludeDelete(ExpenseFlow expenseFlow);
	
	/**
	 * 更新提前打款信息
	 * @param expenseFlow
	 */
	public void updateBringInfo(ExpenseFlow expenseFlow);
	
	/**
	 * 查询提前打款列表
	 * @param expenseFlow
	 * @return
	 */
	public List<ExpenseFlow> queryBringRemitList(ExpenseFlow expenseFlow);
	/**
	 * 合并相同科目的费用
	 * @param expenseFlowId
	 * @return
	 */
	public List<AmtDetail> getAmtList(@Param("expenseFlowId") String expenseFlowId);

	/**
	 * 查询所有报销单据信息
	 * @param expenseFlow
	 * @return
	 */
	public List<ExpenseFlow> findAllPage(ExpenseFlow expenseFlow);

	/**
	 * 查询所有报销单据信息报表（不分页）(包含已删除的)
	 * @param expenseFlow 查询条件
	 * @return 导出数据列表
	 */
	List<ExpenseReport> findAllListIncludeDeleteForReport(ExpenseFlow expenseFlow);

	/**
     * 查询单个用户创建的未完结的报销流程的数量
	 */
    int findMyExpenseCount(ExpenseFlow expenseFlow);

	List<ApproveTime> findApproveTimeData(ApproveTime approveTime);

	List<Map<String, Object>> queryMyApplyCount(@Param("statusArr") String[] statusArr, @Param("loginName") String loginName, @Param("id") String id);

}