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
import com.sijibao.oa.modules.sys.dao.PostInfoDao;
import com.sijibao.oa.modules.sys.entity.PostInfo;
import com.sijibao.oa.modules.sys.utils.UserUtils;

/**
 * 岗位类别解析
 * @author xuby
 *
 */
@Service("standardsPostCheck")
public class StandardsPostCheck implements StandardsCheckDetail {
	@Autowired
	private StandardsCityTypeCheck standardsCityTypeCheck;
	@Autowired
	private PostInfoDao postInfoDao;
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
		
		if(smain.getStandsType().contains(Constant.OA_STANDSTYPE_POST)){ //包含岗位
			List<ExpensesStandardsDetail> newStandardsDetailList = new ArrayList<ExpensesStandardsDetail>();
			int count = 1;
			//如果包含则进行数据过滤
			for(ExpensesStandardsDetail sd:sDetailList){
				//根据报销单据岗位查找匹配的数据
				//根据岗位ID获取岗位信息
				PostInfo post = new PostInfo();
				post.setId(UserUtils.getByLoginName(expenseDetail.getExpenseFlow().getApplyPerCode()).getPostIds());
				post = postInfoDao.get(post);
				if(post != null && StringUtils.isNotBlank(post.getPostCode())
						&& sd.getPostCode().contains(post.getPostCode())){
					newStandardsDetailList.add(sd);
					count = 0;
				}
			}
			if(count == 1){
				//匹配不到则直接返回false
				resultMap.put("checkFlag", false);
				resultMap.put("message", "当前报销的["+expenseDetail.getFirstSubName()+"]科目未匹配到对应的控制标准，禁止提交!");
				return resultMap;
			}
			resultMap = standardsCityTypeCheck.checkStandardsDetail(newStandardsDetailList, smain, expenseDetail);
		}else{ 
			//不包含则继续往下解析
			resultMap = standardsCityTypeCheck.checkStandardsDetail(sDetailList, smain, expenseDetail);
		}
		return resultMap;
	}

}
