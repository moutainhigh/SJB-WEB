package sijibao.oa.jeesite.modules.oa.service.standardsCheck;

import java.util.ArrayList;
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
import com.sijibao.oa.modules.sys.dao.AreaDao;
import com.sijibao.oa.modules.sys.entity.Area;

/**
 * 城市类型类别解析
 * @author xuby
 *
 */
@Service("standardsCityTypeCheck")
public class StandardsCityTypeCheck implements StandardsCheckDetail {
	@Autowired
	private StandardsTrandsModeCheck standardsTrandsModeCheck;
	@Autowired
	private AreaDao areaDao; 
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
		
		if(smain.getStandsType().contains(Constant.OA_STANDSTYPE_CITYTYPE)){ //包含城市类别
			//如果包含则根据当前报销单据的城市类别过滤数据
			List<ExpensesStandardsDetail> newStandardsDetailList = new ArrayList<ExpensesStandardsDetail>();
			//如果包含则进行数据过滤
			int count = 1;
			for(ExpensesStandardsDetail sd:sDetailList){
				//根据报销单据城市类别查找匹配的数据
				if(StringUtils.isNotBlank(expenseDetail.getStartPoint())){
					//查询城市类别
					Area area = new Area();
					area.setCode(expenseDetail.getStartPoint());
					area = areaDao.getForCode(area);
					if(area != null && StringUtils.isNotBlank(area.getGrade()) &&
							sd.getCityType().contains(area.getGrade())){
						newStandardsDetailList.add(sd);
						count = 0;
					}
				}
			}
			if(count == 1){
				//匹配不到则直接返回true
				return resultMap;
			}
			resultMap = standardsTrandsModeCheck.checkStandardsDetail(newStandardsDetailList, smain, expenseDetail);
		}else{
			//不包含则继续向下解析
			resultMap = standardsTrandsModeCheck.checkStandardsDetail(sDetailList, smain, expenseDetail);
		}
		return resultMap;
	}

}
