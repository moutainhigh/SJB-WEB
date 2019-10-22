package sijibao.oa.jeesite.modules.oa.service.standardsCheck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsDetail;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsMain;

/**
 * 交通方式类别解析
 * @author xuby
 *
 */
@Service("standardsTrandsModeCheck")
public class StandardsTrandsModeCheck implements StandardsCheckDetail {
	@Autowired
	private StandardsAmountCheck standardsAmountCheck;
	/**
	 * 根据不同的控制列表校验是否超标
	 * @param sDetailList 控制标准明细
	 * @param smain 控制标准主表
	 * @param expenseDetail 报销明细数据
	 * @return
	 */
	@Override
	public Map<String, Object> checkStandardsDetail(List<ExpensesStandardsDetail> sDetailList,
			ExpensesStandardsMain smain, ExpenseDetail expenseDetail) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("checkFlag", true);
		resultMap.put("message", "");
		
		if(smain.getStandsType().contains(Constant.OA_STANDSTYPE_TRANSMODE)){ //包含交通方式
			//包含交通方式则开始校验是否超标
			int count = 1;
			for(ExpensesStandardsDetail sd:sDetailList){
				//根据报销单据交通方式查找匹配的数据
				if(sd.getTransMode().contains(expenseDetail.getSecondSub())){
					//如果匹配到则证明没有超标，返回true，可以正常提交
					count = 0; 
					return resultMap;
				}
			}
			if(count == 1){
				//匹配不到则说明超标，不允许提交
				resultMap.put("checkFlag",false);
				if(StringUtils.isNotBlank(expenseDetail.getSecondSubName())){
					resultMap.put("message", "提交失败!当前报销的["+expenseDetail.getSecondSubName()+"]已超过差旅标准。");
				}else{
					resultMap.put("message", "提交失败!当前报销的["+expenseDetail.getFirstSubName()+"]已超过差旅标准。");
				}
			}
			return resultMap;
		}else{
			//如果不包含，则继续往下校验
			resultMap = standardsAmountCheck.checkStandardsDetail(sDetailList, smain, expenseDetail);
		}
		return resultMap;
	}

}
