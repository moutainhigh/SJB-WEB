package sijibao.oa.jeesite.modules.intfz.service.demand;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.request.demand.DemandManagementBudgetRequest;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.DemandManagementBudgetDao;
import com.sijibao.oa.modules.oa.entity.DemandManagementBudget;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.SubInfoUtils;

/**
 * 需求申请流程服务层
 * @author Petter
 */
@Service
@Transactional(readOnly = true)
public class IntfzDemandManagementBudgetService extends BaseService {
	
	@Autowired
	private DemandManagementBudgetDao demandManagementBudgetDao;
	
	/**
	 * 设置预算明细信息
	 * @param demandManagementBudgetRequest
	 * @param demandManagementBudget
	 * @param clientType
	 */
	public void setDemandManagementBudgetInfo(DemandManagementBudgetRequest demandManagementBudgetRequest,DemandManagementBudget demandManagementBudget,String clientType){
		if(Constant.CLIENT_TYPE_APP.equals(clientType)){ //APP端接口
			if(demandManagementBudgetRequest.getSubject() != null){
				demandManagementBudget.setFirstSub(demandManagementBudgetRequest.getSubject()[0]); //一级科目
			}
			if(demandManagementBudgetRequest.getSubject() != null && demandManagementBudgetRequest.getSubject().length > 1
					&& !demandManagementBudgetRequest.getSubject()[1].startsWith("no_")){
				demandManagementBudget.setSecondSub(demandManagementBudgetRequest.getSubject()[1]); //二级科目
			}
		}else{
			demandManagementBudget.setFirstSub(demandManagementBudgetRequest.getFirstSub()); //一级科目
			demandManagementBudget.setSecondSub(demandManagementBudgetRequest.getSecondSub()); //二级科目
		}
	}
	
	/**
	 * 保存预算明细信息
	 * @param flowCode
	 * @param user
	 * @param demandManagementBudgetRequestList
	 * @param clientType
	 */
	@Transactional(readOnly = false)
	public void saveDemandManagementBudgetInfo(String flowCode,User user,List<DemandManagementBudgetRequest> demandManagementBudgetRequestList,String clientType){
		DemandManagementBudget deleteBudget = new DemandManagementBudget();
		deleteBudget.setProcCode(flowCode);
		demandManagementBudgetDao.deleteForProcCode(deleteBudget); //删除之前的明细数据，再重新保存
		if(demandManagementBudgetRequestList !=null && demandManagementBudgetRequestList.size() > 0){
			for(int i = 0; i < demandManagementBudgetRequestList.size();i++){
				DemandManagementBudgetRequest demandManagementBudgetRequest = demandManagementBudgetRequestList.get(i);
				DemandManagementBudget demandManagementBudget = new DemandManagementBudget();	
				demandManagementBudget.setProcCode(flowCode); //流程编号
				demandManagementBudget.setUserCode(user.getLoginName());
				demandManagementBudget.setStartDate(DateUtils.parseDateFormUnix(demandManagementBudgetRequest.getStartDate(), DateUtils.YYYY_MM_DD)); //开始日期
				if(demandManagementBudgetRequest.getStartPoint() != null && demandManagementBudgetRequest.getStartPoint().length > 1){
					demandManagementBudget.setStartPoint(demandManagementBudgetRequest.getStartPoint()[1]); //起点
				}else if(demandManagementBudgetRequest.getStartPoint() != null && demandManagementBudgetRequest.getStartPoint().length > 0){
					demandManagementBudget.setStartPoint(demandManagementBudgetRequest.getStartPoint()[0]); //起点
				}
				demandManagementBudget.setEndDate(DateUtils.parseDateFormUnix(demandManagementBudgetRequest.getEndDate(), DateUtils.YYYY_MM_DD)); //结束日期
				if(demandManagementBudgetRequest.getEndPoint() != null && demandManagementBudgetRequest.getEndPoint().length > 1){
					demandManagementBudget.setEndPoint(demandManagementBudgetRequest.getEndPoint()[1]); //终点
				}else if(demandManagementBudgetRequest.getEndPoint() != null && demandManagementBudgetRequest.getEndPoint().length > 0){
					demandManagementBudget.setEndPoint(demandManagementBudgetRequest.getEndPoint()[0]); //终点
				}
				this.setDemandManagementBudgetInfo(demandManagementBudgetRequest, demandManagementBudget, clientType);
				demandManagementBudget.setPersonNum(Integer.toString(demandManagementBudgetRequest.getPersonNum())); //人数
				//计算天数
				String dayNum = null;
				if(StringUtils.isNotBlank(demandManagementBudgetRequest.getSecondSub())){
					dayNum =  SubInfoUtils.getDayCalculation(DateUtils.parseDate(demandManagementBudgetRequest.getStartDate()),DateUtils.parseDate(demandManagementBudgetRequest.getEndDate()),demandManagementBudget.getSecondSub());
				}else{
					dayNum =  SubInfoUtils.getDayCalculation(DateUtils.parseDate(demandManagementBudgetRequest.getStartDate()),DateUtils.parseDate(demandManagementBudgetRequest.getEndDate()),demandManagementBudget.getFirstSub());
				}
				demandManagementBudget.setDayNum(String.valueOf(dayNum)); //天数
                if(demandManagementBudgetRequest.getExpenseAmt() != null){
                    demandManagementBudget.setExpenseAmt(demandManagementBudgetRequest.getExpenseAmt().toString()); //预算金额
                }else {
                    demandManagementBudget.setExpenseAmt(null);
                }
				demandManagementBudget.setRemarks(demandManagementBudgetRequest.getRemarks()); //备注
				demandManagementBudget.setRowNum(i);
				demandManagementBudget.preInsertForWeChart(user);
				demandManagementBudgetDao.insert(demandManagementBudget); //保存预算明细
			}
		}
	}
	
}
