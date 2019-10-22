/**
 * Copyright &copy; 2012-2013 <a href="httparamMap://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package sijibao.oa.jeesite.modules.sys.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 消息定时任务 把sys_log表中登录日志信息写入到txt文件中
 * @author huangkai
 * @version 2019-03-21
 */
@Service
@Lazy(false)

public class ContractHisMessageService {
	/**
	 * 日志对象
	 */
//	protected Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private IntfzWebContractHisService intfzWebContractHisService;
////	@Scheduled(cron = "0/10 * * * * ?") //每10秒 用于测试
//	@Scheduled(cron = "0 0 8 14,28 * ?") //
//	public void saveLoginInfo() {
//		logger.info("===================IntfzQuartzContractHisController ContractHisMessageJob 合同过期消息自动推送 start===================");
//		intfzWebContractHisService.ContractHisMessageJob();
////		intfzWebContractHisService.insertMessage(list);
//		logger.info("===================IntfzQuartzContractHisController ContractHisMessageJob 合同过期消息自动推送 end===================");
//	}
//	//insertMessage

}

		
