package sijibao.oa.jeesite.modules.oa.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sijibao.oa.common.service.BaseService;
import com.sijibao.oa.modules.oa.entity.ExpensesReportInfo;

@Service
@Transactional(readOnly = false)
public class ExpenseSubInfoReportPCTService extends BaseService {
	@Autowired
	private ExpenseSubInfoReportService expenseSubInfoReportService;

	// 处理数据
	public List<List<String>> findData(ExpensesReportInfo expensesReportInfo) {
		// 获取所有的数字信息
		List<List<String>> data = expenseSubInfoReportService.findData(expensesReportInfo);
		List<List<String>> dataPCT = new ArrayList<List<String>>();
		DecimalFormat df = new DecimalFormat("0.00%");
		for (int i = 0; i < data.size(); i++) {
			ArrayList<String> dataPCTList = new ArrayList<String>();
			dataPCTList.add(data.get(i).get(0));
			dataPCTList.add(data.get(i).get(1));
			dataPCTList.add(data.get(i).get(2));
			for (int j = 3; j < data.get(i).size(); j++) {
				double count = Double.parseDouble(data.get(i).get(j))
						/ Double.parseDouble(data.get(data.size() - 1).get(data.get(data.size() - 1).size() - 1));
				dataPCTList.add(String.valueOf(df.format(count)));
			}
			dataPCT.add(dataPCTList);
		}
		return dataPCT;
	}
}
