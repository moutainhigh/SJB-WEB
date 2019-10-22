package sijibao.oa.jeesite.modules.intfz.websocket;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * @description: websocket拦截器类
 * @author: xgx
 * @create: 2019-07-29 11:41
 **/
public class WebSocketInterceptor implements HandshakeInterceptor {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = serverHttpRequest.getServletRequest().getSession();
            Map parameterMap = serverHttpRequest.getServletRequest().getParameterMap();
            if (session != null) {
                String[] userIds= (String [])parameterMap.get("userId");
                map.put("userId", userIds[0]);
                logger.info("【websocket】请求参数userId：{}",userIds[0]);
            }

        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
