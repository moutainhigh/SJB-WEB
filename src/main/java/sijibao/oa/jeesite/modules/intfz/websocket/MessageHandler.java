package sijibao.oa.jeesite.modules.intfz.websocket;

import java.io.EOFException;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.alibaba.fastjson.JSON;
import com.sijibao.oa.modules.intfz.bean.BaseResp;
import com.sijibao.oa.modules.intfz.enums.IntfzRs;
import com.sijibao.oa.modules.intfz.response.common.RedCountResp;
import com.sijibao.oa.modules.sys.entity.User;
import com.sijibao.oa.modules.sys.utils.UserUtils;
import com.sijibao.oa.office.api.IntfzCommonMessageService;
import com.sijibao.oa.office.api.request.common.MessageHandleReq;

/**
 * @description: 消息处理类
 * @author: xgx
 * @create: 2019-07-29 11:40
 **/
@Component
public class MessageHandler extends TextWebSocketHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    //在线用户列表
    private static final ConcurrentHashMap<String, WebSocketSession> users;
    //用户标识
    private static final String CLIENT_ID = "userId";
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private IntfzCommonMessageService intfzCommonMessageService;

    static {
        users = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getClientId(session);
        logger.info("【websocket】用户:{}成功建立连接",userId);
        if (userId != null) {
            users.put(userId, session);
            session.sendMessage(new TextMessage(getMessage(userId)));
        }
    }

    /**可以对H5 Websocket的send方法进行处理
     * @param session
     * @param message
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.info("【websocket】客户端发送的消息：{}",message);
        WebSocketMessage message1 = new TextMessage("server:"+message);
        try {
            session.sendMessage(message1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送信息给指定用户
     * @param clientId
     * @return
     */
    public boolean sendMessageToUser(String clientId) {
        if (users.get(clientId) == null) return false;
        WebSocketSession session = users.get(clientId);
        logger.info("【websocket】服务端发送消息给指定用户：{}",session);
        if (!session.isOpen()) return false;
        try {
            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        String message = getMessage(clientId);
                        TextMessage textMessage=new TextMessage(message);
                        session.sendMessage(textMessage);
                    } catch (Exception e) {
                        logger.error("【websocket】发送消息异常",e);
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            logger.error("【websocket】发送消息异常",e);
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(String message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = users.keySet();
        WebSocketSession session = null;
        for (String clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    TextMessage textMessage=new TextMessage(message);
                    session.sendMessage(textMessage);
                }
            } catch (IOException e) {
                logger.error("【websocket】广播消息异常",e);
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return  allSendSuccess;
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(exception instanceof  EOFException){
            logger.info("【websocket】会话超时{}",session,exception);
        }else{
            if (session.isOpen()) {
                session.close();
            }
            logger.error("【websocket】连接出错",exception);
            users.remove(Objects.requireNonNull(getClientId(session)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if(status.getCode()==1006){
            logger.info("【websocket】用户：{},会话超时：{}",getClientId(session),status);
        }else{
            logger.info("【websocket】用户：{},连接已关闭：{}",getClientId(session),status);
            users.remove(getClientId(session));
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private String getClientId(WebSocketSession session) {
        try {
            String clientId = (String) session.getAttributes().get(CLIENT_ID);
            return clientId;
        } catch (Exception e) {
            logger.error("【websocket】获取用户标识异常",e);
            return null;
        }
    }

    /**
     *封装消息
     * @param clientId
     * @return
     */

    public String getMessage(String clientId){
        User user = UserUtils.getByPhone(clientId);
        MessageHandleReq req=new MessageHandleReq("1",user.getId());
        int redCount = 0;
        try {
            redCount = intfzCommonMessageService.findRedCount(req);
        } catch (Exception e) {
           logger.error("【websocket】调用office服务异常",e);
        }
        RedCountResp red = new RedCountResp();
        if (redCount > 99) {
            red.setRedCount("99+");
        } else {
            red.setRedCount(String.valueOf(redCount));
        }
        BaseResp<RedCountResp> response=new BaseResp<>(IntfzRs.SUCCESS, "ok",red);
        String message= JSON.toJSONString(response);
        logger.info("【websocket】发送消息内容：{}",message);
        return message;
    }
}
