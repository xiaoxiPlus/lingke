package com.fh.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {
    /*首先要注入ServerEndpointExporter，这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
    要注意，如果使用独立的servlet容器，而不是直接使用springboot的内置容器，
    就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理*/
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        /*获取 HandshakeRequest 中的token值，这里request与HttpServletRequest不同，这是WebSocket中的request
        * request中的 token 值存在 sec-websocket-protocol 中*/
        String token = request.getHeaders().get("sec-websocket-protocol").get(0);
        /*将token值存到websocket中*/
        sec.getUserProperties().put("token", token);
        /*将token值通过response返回前端*/
        response.getHeaders().put("sec-websocket-protocol", request.getHeaders().get("sec-websocket-protocol"));
        super.modifyHandshake(sec, request, response);
    }

}
