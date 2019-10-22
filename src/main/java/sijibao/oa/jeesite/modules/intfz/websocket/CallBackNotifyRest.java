package sijibao.oa.jeesite.modules.intfz.websocket;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;

/**
 * @description: 通知发送websocket消息
 * @author: xgx
 * @create: 2019-07-30 18:48
 **/
@RestController
@RequestMapping("/websocket")
public class CallBackNotifyRest {
    @Autowired
    private MessageHandler messageHandler;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/callBack/notify")
    public BaseResp<String> notifySendMessage(String userId,boolean isAll){
        logger.info("【websocket】回调请求，通知用户:{},是否批量用户：{}",userId,isAll);
        boolean result=false;
        if(!isAll){
            result = messageHandler.sendMessageToUser(userId);
            return new BaseResp<>(IntfzRs.SUCCESS, "ok", result+"");
        }else{
            List<String> userids = JSON.parseArray(userId, String.class);
            int count=0;
            for(String clientId:userids){
                boolean isOnline = messageHandler.sendMessageToUser(clientId);
                if(isOnline){
                    count++;
                }
            }
            return new BaseResp<>(IntfzRs.SUCCESS, "ok","在线："+count+",不在线:"+(userids.size()-count));
        }

    }

}
