package sijibao.oa.jeesite.modules.intfz.websocket;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sijibao.oa.common.utils.StringUtils;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.sys.utils.SysParamsUtils;

/**
 * @description: 用于和客服端保持连接的心跳任务
 * @author: xgx
 * @create: 2019-08-07 11:18
 **/

@Service
@Lazy(false)
public class HeartbeatTask implements SchedulingConfigurer {
    @Autowired
    private MessageHandler messageHandler;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                BaseResp<String> response=new BaseResp<>(IntfzRs.ERROR, "会话心跳检测","");
                String message= JSON.toJSONString(response);
                messageHandler.sendMessageToAllUsers(message);
            }
        }, new Trigger(){
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                //任务触发，可修改任务的执行周期
                String cron = SysParamsUtils.getParamValue("cron_task","sysParam","");
                if(StringUtils.isBlank(cron)){
                    cron="0/15 * * * * ?";
                }
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        });
    }


/* @Scheduled(cron = "0/15 * * * * ?") //每隔20秒检测一次
    public  void notifyAllUser(){
        BaseResp<String> response=new BaseResp<>(IntfzRs.ERROR, "会话心跳检测","");
        String message=JSON.toJSONString(response);
        messageHandler.sendMessageToAllUsers(message);
    }*/


}
