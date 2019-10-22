package sijibao.oa.jeesite.modules.oa.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.flow.entity.ExpenseDetail;
import com.sijibao.oa.modules.oa.dao.ExpenseSubInfoReportDao;
import com.sijibao.oa.modules.oa.entity.AllData;
import com.sijibao.oa.modules.oa.entity.ExpensesReportInfo;
import com.sijibao.oa.modules.oa.entity.ReportSpan;
import com.sijibao.oa.modules.sys.utils.UserUtils;

@Service
@Transactional(readOnly = false)
public class ExpenseSubInfoReportService extends BaseService {
	@Autowired
	private ExpenseSubInfoReportDao expenseSubInfoReportDao;

	// 查询第一行列名
	public List<ReportSpan> findReportSpan(List<Integer> hideNum) {
		String[] inFirstCol = null;
		String[] notInCol = null;
		List<String> notInColList = hideColName(hideNum);
		if(notInColList != null && notInColList.size() > 0){
			notInCol = notInColList.toArray(new String[notInColList.size()]);
			Set<String> firstList = new HashSet<>();
			for(String first : notInCol){
				firstList.add(first.substring(0, 2));
			}
			List<String> inFirst = expenseSubInfoReportDao.findNotInFirst(notInCol, 
					firstList.toArray(new String[firstList.size()]));
			inFirstCol = inFirst.toArray(new String[inFirst.size()]);
		}
		// 封装数据
		List<ReportSpan> reportList = expenseSubInfoReportDao.findReportSpan(notInCol, inFirstCol);
		for (ReportSpan rs : reportList) {
			if("0".equals(rs.getColspan())){
				rs.setRowspan("2");
				rs.setColspan("1");
			}
		}
		// 添加合计的列
		ReportSpan reportSpan = new ReportSpan();
		reportSpan.setRowCode("-1");
		reportSpan.setRowName("个人费用小计");
		reportSpan.setRowspan("2");
		reportSpan.setColspan("1");
		reportList.add(reportSpan);
		return reportList;
	}

	// 查询第二列的列名
	public List<ReportSpan> findSecondRow(List<Integer> hideNum) {
		String[] notInCol = null;
		List<String> notInColList = hideColName(hideNum);
		if(notInColList != null && notInColList.size() > 0){
			notInCol = notInColList.toArray(new String[notInColList.size()]);
		}
		// 查询第二列
		List<ReportSpan> secondRowList = expenseSubInfoReportDao.findAllNameList(notInCol);
		return secondRowList;
	}

	// 数据
	public List<List<String>> findData(ExpensesReportInfo expensesReportInfo) {
		// 金额格式化
		DecimalFormat df = new DecimalFormat("0.00");
		// 获取所有的列
		List<String> list = expenseSubInfoReportDao.findSecondList();

		Map<String, String> map = Maps.newHashMap();
		String sql = dataScopeFilter(UserUtils.getUser(), "o", "u");
		if(StringUtils.isNotBlank(expensesReportInfo.getQuerOffice())){
			List<String> userNameList = expenseSubInfoReportDao.findUserNameListByOffice(expensesReportInfo.getQuerOffice());

			userNameList.toString().substring(0,userNameList.size()-1);
			StringBuilder s = new StringBuilder();
			for (String string : userNameList) {
				s.append("'"+string+"',");
			}
			map.put("querOffice",s.substring(0, s.toString().length()-1).toString() );
		}




		map.put("projectName", expensesReportInfo.getProjectName());
		if(StringUtils.isNotBlank(expensesReportInfo.getQuerDateStart())){
			expensesReportInfo.setQuerDateStart(expensesReportInfo.getQuerDateStart()+" 00:00:00");
		}
		map.put("start", expensesReportInfo.getQuerDateStart());
		if(StringUtils.isNotBlank(expensesReportInfo.getQuerDateEnd())){
			expensesReportInfo.setQuerDateEnd(expensesReportInfo.getQuerDateEnd()+" 23:59:59");
		}
		map.put("applyPerCode",expensesReportInfo.getApplyPerCode());
		map.put("end", expensesReportInfo.getQuerDateEnd());
		map.put("status", expensesReportInfo.getStatus());
		map.put("userName", expensesReportInfo.getUserName());
		if(!"2".equals(expensesReportInfo.getIsInit())){
			map.put("sql",sql);
		}
		// 在数据库查询数据
		List<AllData> all = expenseSubInfoReportDao.findAllDetailList(map);
		//遍历所有的数据，组成map
		Map<String,List<String>> dataMap = Maps.newHashMap();
		for (AllData allData : all) {
			String key = null;
			if(StringUtils.isNotBlank(allData.getProjectName())){
				key = allData.getOfficeName() + "_" + allData.getUserName() + "_" + allData.getProjectName();
			}else{
				key = allData.getOfficeName() + "_" + allData.getUserName() + "_ 无";
			}
			
			List<String> alist = dataMap.get(key);
			if(alist == null){
				alist = Lists.newArrayList();
			}
			if(StringUtils.isNotBlank(allData.getSecondSubCode()) && !"0".equals(allData.getSecondSubCode())){
				alist.add(allData.getSecondSubCode()+"|"+allData.getAmt());
			}else{
				alist.add(allData.getFirstSubCode()+"|"+allData.getAmt());
			}
			dataMap.put(key, alist);
		}
		//组合数据 list
		List<List<String>> rtnList = Lists.newArrayList();
		Map<String, BigDecimal> totalMap = Maps.newHashMap();
		BigDecimal allTotal = new BigDecimal(0);
		for (String string : dataMap.keySet()) {
			List<String> rtn = Lists.newArrayList();
			rtn.add(string.split("_")[0]);
			rtn.add(string.split("_")[1]);
			rtn.add(string.split("_")[2]);
			BigDecimal total = new BigDecimal(0);
			for(String par : list){
				boolean flag = false;
				BigDecimal parTotal = totalMap.get(par);
				if(parTotal == null){
					parTotal = new BigDecimal(0);
				}
				for(String data : dataMap.get(string)){
					String subCode = data.split("\\|")[0];
					if(par.equals(subCode)){
						rtn.add(data.split("\\|")[1]);
						total = total.add(new BigDecimal(data.split("\\|")[1]));
						parTotal = parTotal.add(new BigDecimal(data.split("\\|")[1]));
						flag = true;
					}
				}
				totalMap.put(par, parTotal);
				if(!flag){
					rtn.add("0");
				}
			}
			allTotal = allTotal.add(total);
			rtn.add(df.format(total).toString());
			rtnList.add(rtn);
		}
		List<String> lastLine = Lists.newArrayList();
		lastLine.add("");
		lastLine.add("");
		lastLine.add("合计");
		for(String par : list){
			if(totalMap.size() == 0){
				lastLine.add("0");
			}else{
				lastLine.add(totalMap.get(par).toString());
			}
		}
		lastLine.add(allTotal.toString());
		rtnList.add(lastLine);
		return rtnList;
	}

	// 隐藏列名
	private List<String> hideColName(List<Integer> hideNum) {
		// 判断
		if (hideNum == null) {
			return null;
		}

		// 获取所有的列
		List<String> list = expenseSubInfoReportDao.findSecondList();
		list.add(0, "部门");
		list.add(1, "姓名");
		list.add(2, "项目名称");
		 
		list.add("合计");
		
		List<String> notInList = Lists.newArrayList();
		       
		for(int num : hideNum){
			notInList.add(list.get(num));
		}
		
		return notInList;
	}
	
	//隐藏列中的数据
	public List<Integer> hideNum(List<List<String>> data){
		List<Integer> list = Lists.newArrayList();
		if (data == null || data.size() == 0 ) {
			return null;
		} else if (StringUtils.isBlank(data.get(data.size() - 1).get(data.get(data.size() - 1).size() - 1))
				|| data.get(data.size() - 1).get(data.get(data.size() - 1).size() - 1).equals("0") 
				|| data.get(data.size() - 1).get(data.get(data.size() - 1).size() - 1).equals("0.00%")) {
			
			return null;
		}
		for (int i = 0; i < data.get(data.size()-1).size(); i++) {
			String da = data.get(data.size()-1).get(i);
			if ("0".equals(da) || "0.00%".equals(da)) {
				list.add(i);
			}
		}
		for(List<String> rowList : data){
			for(int i=list.size()-1;i>=0;i--){ //倒序  
                if(i<=rowList.size()){  
                	rowList.remove(list.get(i).intValue());  
                }   
            }
		}
		return list;
	}
	//百分比统计中隐藏列中的数据
	public List<Integer> hideNumPCT(List<List<String>> data){
		List<Integer> list = Lists.newArrayList();
		if (data == null || data.size() == 0 ) {
			return null;
		} else if (StringUtils.isBlank(data.get(data.size() - 1).get(data.get(data.size() - 1).size() - 1))
				|| data.get(data.size() - 1).get(data.get(data.size() - 1).size() - 1).equals("0.00%")) {
			
			return null;
		}
		for (int i = 0; i < data.get(data.size()-1).size(); i++) {
			String da = data.get(data.size()-1).get(i);
			if ("0.00%".equals(da)) {
				list.add(i);
			}
		}
		for(List<String> rowList : data){
			for(int i=list.size()-1;i>=0;i--){ //倒序  
                if(i<=rowList.size()){  
                	rowList.remove(list.get(i).intValue());  
                }   
            }
		}
		
		return list;
	}
	/**
	 * 查询个人明细
	 * @param expensesReportInfo
	 * @return
	 */
	public List<ExpenseDetail> findOwnDetail(ExpensesReportInfo expensesReportInfo) {
		return expenseSubInfoReportDao.findOwnDetail(expensesReportInfo);
	}

}
