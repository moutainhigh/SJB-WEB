package sijibao.oa.jeesite.modules.sys.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.sijibao.oa.common.utils.CacheUtils;
import com.sijibao.oa.common.utils.DateUtils;
import com.sijibao.oa.common.utils.SpringContextHolder;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.oa.dao.ExpensesSubConfDao;
import com.sijibao.oa.modules.oa.entity.ExpensesSubConf;
import com.sijibao.oa.modules.oa.entity.ExpensesSubInfo;
import com.sijibao.oa.modules.oa.service.ExpensesSubInfoService;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class SubInfoUtils {
	
    protected static Logger logger = LoggerFactory.getLogger(SubInfoUtils.class.getName());

	private static ExpensesSubInfoService expensesSubInfoService = SpringContextHolder.getBean(ExpensesSubInfoService.class) ;
	private static ExpensesSubConfDao expensesSubConfDao = SpringContextHolder.getBean(ExpensesSubConfDao.class) ;


	//session中缓存的用户相关信息
    public static final String SUB_INFO_LIST = "subInfoList";//费用科目列表
	public static final String SUB_CONF_LIST = "subConfList";//费用科目图片配置列表

	/**
	 * 获取科目列表，带图片配置
	 * @param expensesSubInfo
	 * @return
	 */
	public static List<ExpensesSubInfo> findListNew(ExpensesSubInfo expensesSubInfo) {
		List<ExpensesSubInfo> expensesSubInfolist = (List<ExpensesSubInfo>) CacheUtils.get(SUB_INFO_LIST);
		if(expensesSubInfolist == null){
			expensesSubInfolist =  expensesSubInfoService.findListNew(expensesSubInfo);
			CacheUtils.put(SUB_INFO_LIST,expensesSubInfolist);
		}
		return expensesSubInfolist;
	}

	/**
	 * 获取图片配置信息
	 * @param query
	 * @return
	 */
	public static List<ExpensesSubConf> querySubConfList(ExpensesSubConf query){
		@SuppressWarnings("unchecked")
		List<ExpensesSubConf> expensesSubInfolist = (List<ExpensesSubConf>) CacheUtils.get(SUB_CONF_LIST);
		List<ExpensesSubConf> subConf = Lists.newArrayList();
		if(expensesSubInfolist == null || expensesSubInfolist.size() == 0){
			expensesSubInfolist =  expensesSubConfDao.findList(new ExpensesSubConf());
			CacheUtils.put(SUB_CONF_LIST,expensesSubInfolist);
		}
		if(expensesSubInfolist != null){
			for(ExpensesSubConf conf : expensesSubInfolist){
				if(query.getSubCode().equals(conf.getSubCode())){
					subConf.add(conf);
				}
			}
		}
		return subConf;
	}

	/**
	 * 自动计算天数
	 * @return
	 */
	public static String getDayCalculation(Date start, Date end, String subId){
		if(start == null || end == null){
			return "0";
		}
		double dayNum = DateUtils.getDistanceOfTwoDate(start, end);
		BigDecimal dn = new BigDecimal(dayNum);
		ExpensesSubInfo sub = expensesSubInfoService.get(subId);
		if(sub == null){
			return "0";
		}
		//根据科目配置，查询计算方法
		if(Constant.DAY_CALCULATION_ROUTINE.equals(sub.getDayCalculation())){
			dn = dn.add(new BigDecimal(1));
		}
		return String.valueOf(dn);
	}


}
