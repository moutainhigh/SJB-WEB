package sijibao.oa.jeesite.modules.intfz.web.intfzquartz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sijibao.oa.common.web.BaseController;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.service.util.AppMessageService;
import com.sijibao.oa.office.api.IntfzWebContractHisService;

/**
 * 定时任务客户信息状态更新
 * @author wxb
 * @date 2018-11-15 08:59:53
 */
@Controller
@RequestMapping(value = "quartz/contractHis")
@Lazy(false)
public class IntfzQuartzContractHisController extends BaseController {
	@Autowired
	private AppMessageService appMessageService;
	
	@Autowired
	private IntfzWebContractHisService intfzWebContractHisService;
	/**
	 * 合同过期自动更新 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "ContractHisOverdueTaskJob")
	@ResponseBody
	public BaseResp<String> ContractHisOverdueTaskJob(HttpServletRequest request, HttpServletResponse response){
		logger.info("===================IntfzQuartzContractHisController ContractHisOverdueTaskJob 合同过期自动更新 start===================");
		intfzWebContractHisService.contractHisOverdue();
		logger.info("===================IntfzQuartzContractHisController ContractHisOverdueTaskJob 合同过期自动更新 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}

	/**
	 * 合同逾期消息通知
	 * @param REQUEST
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "ContractHisMessageJob")
	@ResponseBody
//	@Scheduled(cron = "10 * * * * ?") //每10秒 用于测试
//	@Scheduled(cron = "0 0 8 14,28 * ?") //每10秒 用于测试
	public BaseResp<String> ContractHisMessageJob(HttpServletRequest request, HttpServletResponse response){
		logger.info("===================IntfzQuartzContractHisController ContractHisMessageJob 合同过期消息自动推送 start===================");
		intfzWebContractHisService.ContractHisMessageJob();
//		intfzWebContractHisService.insertMessage(list);
		logger.info("===================IntfzQuartzContractHisController ContractHisMessageJob 合同过期消息自动推送 end===================");
		return new BaseResp<String>(IntfzRs.SUCCESS,"执行成功!","");
	}
}
