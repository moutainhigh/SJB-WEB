/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sijibao.oa.modules.flow.entity.ApproveTimeInfo;
import com.sijibao.oa.modules.flow.service.ApproveTimeInfoService;
import com.sijibao.oa.modules.flow.service.ExpenseFlowService;
import com.sijibao.oa.modules.intfz.utils.Constant;
import com.sijibao.oa.modules.sys.entity.ApproveTime;

/**
 * 定时任务 把流程相关的数据在指定日期审批结束的审批时长统计插入到表中
 * @author huangkai
 * @version 2019-03-21
 */
@Service
@Lazy(false)
public class approveTaskService {
	@Autowired
	private ApproveTimeInfoService approveTimeInfoService;
	@Autowired
	private ExpenseFlowService expenseFlowService;

	@Scheduled(cron = "0 0 1 * * ?") //凌晨一点执行
//	@Scheduled(cron = "0 0/30 * * * ?") //每半小时 用于测试
//	@Scheduled(cron = "0/3 * * * * ?") //每半小时 用于测试
	public void saveLoginInfo() {
//		Log log = new Log();
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		c.add(Calendar.DATE, -1);
		Date beginDate = c.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdfs = new SimpleDateFormat("yyyyMMdd");
		String bDate = sdf.format(beginDate);
		String eDate = sdf.format(date);
//		String logDate = sdfs.format(date);
//		String resultStr = "";

		ApproveTime approveTime = new ApproveTime();
		approveTime.setStartTime(bDate);
		approveTime.setEndTime(eDate);
		List<ApproveTime> list = expenseFlowService.findApproveTimeData(approveTime);
		if (null != list && list.size() > 0) {
			ApproveTimeInfo info = new ApproveTimeInfo();
			info.setCountDate(eDate);
			double totalAvg = 0;
			for(ApproveTime approve : list){


				if(Constant.EXPENSE_FLOW_APPLY.equals(approve.getFlowType())){
					String avg = "0";
					if(null != approve.getFinishTime() && !"".equals(approve.getFinishTime())){
						if(!"0".equals(approve.getFinishCount())) {
							Double result = Double.parseDouble(approve.getFinishTime()) / (Double.parseDouble(approve.getFinishCount())*60);
							avg = result.toString();
							totalAvg = totalAvg + result;
						}
					}
					info.setExpenseAvgTime(avg);
				}

				if(Constant.RECP_FLOW_APPLY.equals(approve.getFlowType())){
					String avg = "0";
					if(null != approve.getFinishTime() && !"".equals(approve.getFinishTime())){
						if(!"0".equals(approve.getFinishCount())) {
							Double result = Double.parseDouble(approve.getFinishTime()) / (Double.parseDouble(approve.getFinishCount())*60);
							avg = result.toString();
							totalAvg = totalAvg + result;
						}
					}
					info.setRecpAvgTime(avg);
				}

				if(Constant.TRAVEL_FLOW_APPLY.equals(approve.getFlowType())){
					String avg = "0";
					if(null != approve.getFinishTime() && !"".equals(approve.getFinishTime())){
						if(!"0".equals(approve.getFinishCount())) {
							Double result = Double.parseDouble(approve.getFinishTime()) / (Double.parseDouble(approve.getFinishCount())*60);
							avg = result.toString();
							totalAvg = totalAvg + result;
						}
					}
					info.setTravelAvgTime(avg);
				}

				if(Constant.CONTRACT_FLOW_APPLY.equals(approve.getFlowType())){
					String avg = "0";
					if(null != approve.getFinishTime() && !"".equals(approve.getFinishTime())){
						if(!"0".equals(approve.getFinishCount())) {
							Double result = Double.parseDouble(approve.getFinishTime()) / (Double.parseDouble(approve.getFinishCount())*60);
							avg = result.toString();
							totalAvg = totalAvg + result;
						}
					}
					info.setContractAvgTime(avg);
				}
			}

			if(totalAvg != 0){
				Double temp = totalAvg/4;
				info.setTotalAvgTime(temp.toString());
			}else{
				info.setTotalAvgTime("0");
			}

			approveTimeInfoService.save(info);
		}
	}
}

		
