package com.softserve.maklertaboo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Interceptor.
 *
 * @author Mykola Borovets
 */
@Slf4j
@Component
public class HttpHandshakeInterceptor implements HandshakeInterceptor {
    /**
     * @param serverHttpRequest ServerHttpRequest
     * @param serverHttpResponse ServerHttpResponse
     * @param webSocketHandler WebSocketHandler
     * @param map Map<String, Object>
     * @return boolean
     * @throws Exception
     * @author MykolaBorovets
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        log.info("before handshake");

        if (serverHttpRequest instanceof ServletServerHttpRequest) {

            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletServerHttpRequest.getServletRequest().getSession();
            map.put("sessionId", session.getId());
        }
        return true;
    }

    /**
     * @param serverHttpRequest ServerHttpRequest
     * @param serverHttpResponse ServerHttpResponse
     * @param webSocketHandler WebSocketHandler
     * @param e Exception
     * @author MykolaBorovets
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                               WebSocketHandler webSocketHandler, Exception e) {
        log.info("after handshake");
    }
}

