package sijibao.oa.jeesite.modules.intfz.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sijibao.oa.common.producer.ProducerService;
import com.sijibao.oa.message.api.IntfzMessageService;
import com.sijibao.oa.message.api.request.EmailMessageRequest;
import com.sijibao.oa.message.api.request.MesAppMessageRequest;
import com.sijibao.oa.message.api.request.SmsMessageRequest;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.DictUtils;
import com.sijibao.oa.modules.sys.utils.UserUtils;


@Service
public class AppMessageService {
	/**
	 * 日志对象
	 */
	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private IntfzMessageService intfzMessageService;
	@Autowired
	private ProducerService producerService;

	@Async
	public void sendAsync(MesAppMessageRequest req){

		try {
			req.setFromAppCode(DictUtils.getDictLabel("1", "app_code", ""));
			req.setToAppCode(DictUtils.getDictLabel("1", "app_code", ""));
			req.setType(0x1000);
			req.setToCode(UserUtils.getByLoginName(req.getToCode()).getMobile());
//			intfzMessageService.sendAsync(req);
			log.info("=============== AppMessageService 消息推送成功！================"+req.toString());
		}catch (Exception e){
			log.info("=============== AppMessageService Error 消息推送失败：================",e.getMessage());
		}

	}

	public void sendSms(SmsMessageRequest req){
		log.info("【消息通知】短信体内容：{}",req.toString());
		try {
			User usr = UserUtils.getByLoginName(req.getMobile());
			req.setMobile(usr.getMobile());
			String reqJson = JSON.toJSONString(req);
			boolean isStart = producerService.messageNotify2SMS(reqJson);
			if(!isStart){
				intfzMessageService.sendSms(req);
			}
			log.info("【消息通知】AppMessageService 短信发送成功！");
		} catch (Exception e) {
			log.info("【消息通知】AppMessageService Error 短信发送失败：",e.getMessage());

		}

	}


	public void sendEmail(EmailMessageRequest req){
		log.info("【消息通知】邮件体内容：{}",req.toString());
		try {
			User usr = UserUtils.getByLoginName(req.getLoginName());
			req.setEmail(usr.getEmail());
			String reqJson = JSON.toJSONString(req);
			boolean isStart = producerService.messageNotify2Email(reqJson);
			if(!isStart){
				intfzMessageService.sendEmail(req);
			}
			log.info("【消息通知】AppMessageService 邮件发送成功！");
		} catch (Exception e) {
			log.info("【消息通知】AppMessageService Error 邮件发送失败：",e.getMessage());

		}

	}
}
