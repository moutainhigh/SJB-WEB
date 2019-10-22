package sijibao.oa.jeesite.modules.oa.service.standardsCheck;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsDetail;
import com.sijibao.oa.modules.oa.entity.ExpensesStandardsMain;
import com.sijibao.oa.modules.sys.utils.DictUtils;

/**
 * 金额类别解析
 * @author xuby
 *
 */
@Service("standardsAmountCheck")
public class StandardsAmountCheck implements StandardsCheckDetail {
	
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
		DecimalFormat df = new DecimalFormat("0.00");
		if(smain.getStandsType().contains(Constant.OA_STANDSTYPE_AMOUNT)){ //包含金额
			int count = 1;
			//如果包含金额，则判断当前报销金额是否超过控制标准金额
			for(ExpensesStandardsDetail sd:sDetailList){
				//判断金额单位
				if(Constant.OA_AMOUNTUNIT_ONE.equals(sd.getAmountUnit()) || Constant.OA_AMOUNTUNIT_FOUR.equals(sd.getAmountUnit())){//元/天
					//根据天数计算金额平均值
					double dayNum = DateUtils.getDistanceOfTwoDate(expenseDetail.getStartDate(), expenseDetail.getEndDate());
					BigDecimal dn = new BigDecimal(dayNum);
					BigDecimal expenseAmt = expenseDetail.getExpenseAmt();
					//判断科目，如果是差旅补助，则天数加一，否则不加
                    String code = DictUtils.getDictLabel("0","travel_allowance","");
					if(code.equals(expenseDetail.getFirstSub()) || dn.compareTo(BigDecimal.ZERO) == 0){
						dn = dn.add(new BigDecimal(1));
					}
					
					//获取报销人数
					if(StringUtils.isNotBlank(expenseDetail.getPersonNum()) && Integer.parseInt(expenseDetail.getPersonNum()) > 1){
						//计算平均金额  报销金额/人数/天数
//						expenseAmt = expenseAmt.divide(new BigDecimal(expenseDetail.getPersonNum()),2,BigDecimal.ROUND_HALF_DOWN).divide(dn,2,BigDecimal.ROUND_HALF_DOWN);
						//计算平均金额 报销金额/天数
						expenseAmt = expenseAmt.divide(dn,2,BigDecimal.ROUND_HALF_DOWN);
					}else{
						//计算平均金额 报销金额/天数
						expenseAmt = expenseAmt.divide(dn,2,BigDecimal.ROUND_HALF_DOWN); 
					}
					BigDecimal standsAmount = sd.getStandsAmount();
					if(Constant.OA_AMOUNTUNIT_FOUR.equals(sd.getAmountUnit())){
						standsAmount = standsAmount.divide(new BigDecimal(2),2,BigDecimal.ROUND_HALF_DOWN);
					}
					
					if(expenseAmt.compareTo(standsAmount) == 1 ){ 
						//如果大于标准金额则说明超标,返回false
						resultMap.put("checkFlag", false);
						if(Constant.OA_AMOUNTUNIT_ONE.equals(sd.getAmountUnit())){
							resultMap.put("message", "提交失败!当前报销的[" + expenseDetail.getFirstSubName() + "]已超过标准金额("
								+ df.format(sd.getStandsAmount()) + "元/天)");
						}
						if(Constant.OA_AMOUNTUNIT_FOUR.equals(sd.getAmountUnit()) && Integer.parseInt(expenseDetail.getPersonNum()) > 1){
							resultMap.put("message", "提交失败!当前报销的[" + expenseDetail.getFirstSubName() + "]已超过标准金额("
									+ df.format(sd.getStandsAmount()) + "元/2人/天)");
						}
//						return resultMap;
					}else{
						count = 0;
						break;
					}
				}else if(Constant.OA_AMOUNTUNIT_FIVE.equals(sd.getAmountUnit())){//元/人/天
                    //根据天数计算金额平均值
                    double dayNum = DateUtils.getDistanceOfTwoDate(expenseDetail.getStartDate(), expenseDetail.getEndDate());
                    BigDecimal dn = new BigDecimal(dayNum);
                    BigDecimal expenseAmt = expenseDetail.getExpenseAmt();
                    //判断科目，如果是差旅补助，则天数加一，否则不加
                    String code = DictUtils.getDictLabel("0","travel_allowance","");
                    if(code.equals(expenseDetail.getFirstSub()) || dn.compareTo(BigDecimal.ZERO) == 0){
                        dn = dn.add(new BigDecimal(1));
                    }

                    //获取报销人数
                    if(StringUtils.isNotBlank(expenseDetail.getPersonNum()) && Integer.parseInt(expenseDetail.getPersonNum()) > 1){
                        //计算平均金额  报销金额/人数/天数
						expenseAmt = expenseAmt.divide(new BigDecimal(expenseDetail.getPersonNum()),2,BigDecimal.ROUND_HALF_DOWN).divide(dn,2,BigDecimal.ROUND_HALF_DOWN);
                    }else{
                        //计算平均金额 报销金额/天数
                        expenseAmt = expenseAmt.divide(dn,2,BigDecimal.ROUND_HALF_DOWN);
                    }
                    BigDecimal standsAmount = sd.getStandsAmount();
                    if(Constant.OA_AMOUNTUNIT_FOUR.equals(sd.getAmountUnit())){
                        standsAmount = standsAmount.divide(new BigDecimal(2),2,BigDecimal.ROUND_HALF_DOWN);
                    }

                    if(expenseAmt.compareTo(standsAmount) == 1 ){
                        //如果大于标准金额则说明超标,返回false
                        resultMap.put("checkFlag", false);
                        if(Constant.OA_AMOUNTUNIT_ONE.equals(sd.getAmountUnit())){
                            resultMap.put("message", "提交失败!当前报销的[" + expenseDetail.getFirstSubName() + "]已超过标准金额("
                                    + df.format(sd.getStandsAmount()) + "元/天)");
                        }
                        if(Constant.OA_AMOUNTUNIT_FOUR.equals(sd.getAmountUnit()) && Integer.parseInt(expenseDetail.getPersonNum()) > 1){
                            resultMap.put("message", "提交失败!当前报销的[" + expenseDetail.getFirstSubName() + "]已超过标准金额("
                                    + df.format(sd.getStandsAmount()) + "元/2人/天)");
                        }
						if(Constant.OA_AMOUNTUNIT_FIVE.equals(sd.getAmountUnit())){
							resultMap.put("message", "提交失败!当前报销的[" + expenseDetail.getFirstSubName() + "]已超过标准金额("
									+ df.format(sd.getStandsAmount()) + "元/人/天)");
						}
//						return resultMap;
                    }else{
                        count = 0;
                        break;
                    }
                }else if(Constant.OA_AMOUNTUNIT_TWO.equals(sd.getAmountUnit())){ //元/月
					BigDecimal monthNum = new BigDecimal(DateUtils.getMonthDiff(expenseDetail.getEndDate(), expenseDetail.getStartDate())+1);
					BigDecimal maxAmount = new BigDecimal(0.00);
					if(StringUtils.isNotBlank(expenseDetail.getPersonNum())){
						//计算最大报销金额  月数*标准金额*人数
						maxAmount = monthNum.multiply(sd.getStandsAmount()).multiply(new BigDecimal(expenseDetail.getPersonNum()));
					}else{
						//计算最大报销金额  月数*标准金额
						maxAmount = monthNum.multiply(sd.getStandsAmount());
					}
					
					if(expenseDetail.getExpenseAmt().compareTo(maxAmount) == 1){
						//如果大于标准金额则说明超标,返回false
						resultMap.put("checkFlag", false);
						resultMap.put("message", "提交失败!当前报销的[" + expenseDetail.getFirstSubName() + "]已超过标准金额("
								+ df.format(sd.getStandsAmount()) + "元/月)。");
//						return resultMap;
					}else{
						count = 0;
						break;
					}
				}else if(Constant.OA_AMOUNTUNIT_THREE.equals(sd.getAmountUnit())){ //元/年
					int startYear = Integer.parseInt(DateUtils.getYear(expenseDetail.getStartDate()));
					int endYead = Integer.parseInt(DateUtils.getYear(expenseDetail.getEndDate()));
					BigDecimal yearNum = new BigDecimal(endYead-startYear+1);
					BigDecimal maxAmount = new BigDecimal(0.00);
					if(StringUtils.isNotBlank(expenseDetail.getPersonNum())){
						//计算最大报销金额  年数*标准金额*人数
						maxAmount = yearNum.multiply(sd.getStandsAmount()).multiply(new BigDecimal(expenseDetail.getPersonNum()));
					}else{
						//计算最大报销金额  年数*标准金额
						maxAmount = yearNum.multiply(sd.getStandsAmount());
					}
					
					if(expenseDetail.getExpenseAmt().compareTo(maxAmount) == 1){
						//如果大于标准金额则说明超标,返回false
						resultMap.put("checkFlag", false);
						resultMap.put("message", "提交失败!当前报销的[" + expenseDetail.getFirstSubName() + "]已超过标准金额("
								+ df.format(sd.getStandsAmount()) + "元/年)。");
//						return resultMap;
					}else{
						count = 0;
						break;
					}
				}else{
					resultMap.put("checkFlag", false);
					resultMap.put("message", "校验控制标准失败!");
				}
			}
			if(count == 0){ //任意一条校验成功则返回true
				resultMap.put("checkFlag", true);
				resultMap.put("message", "");
			}
		}
		return resultMap;
	}

}
