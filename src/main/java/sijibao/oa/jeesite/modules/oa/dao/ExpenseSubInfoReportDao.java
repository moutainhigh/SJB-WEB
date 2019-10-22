package sijibao.oa.jeesite.modules.oa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sijibao.oa.common.persistence.BaseDao;
import com.sijibao.oa.common.persistence.annotation.MyBatisDao;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.oa.entity.AllData;
import com.sijibao.oa.modules.oa.entity.ExpensesReportInfo;
import com.sijibao.oa.modules.oa.entity.ReportSpan;


@MyBatisDao
public interface ExpenseSubInfoReportDao extends BaseDao {

	public String findParSubCode(String form);

	public List<String> findCont(String form);

	public List<String> findNotInFirst(@Param(value = "notInCol") String[] notInCol, @Param(value = "notInFirst") String[] notInFirst);

	public List<ReportSpan> findAllNameList(@Param(value = "notInCol") String[] notInCol);

	public List<String> findUserNameListByOffice(String querOffice);

	public String findUserData(Map<String, Object> paramMap);

	public String findOfficeName(String querOffice);

	public String findMyParName(String userName);

	public String findPar(String name);

	public void getName(ReportSpan reportSpan);

	public String getName(String rowName);

	public List<ReportSpan> findReportSpan(@Param(value = "notInCol") String[] notInCol, @Param(value = "inCol") String[] inCol);

	public List<String> findSecondList();

	public List<AllData> findAllDetailList(Map<String, String> map);

	public List<ExpenseDetail> findOwnDetail(ExpensesReportInfo expensesReportInfo);

	
}
