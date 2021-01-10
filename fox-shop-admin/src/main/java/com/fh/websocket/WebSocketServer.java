package com.fh.websocket;

import cn.hutool.json.JSONUtil;
import com.fh.SecurityJwtUtils;
import com.fh.umsAdmin.entity.UmsAdmin;
import com.fh.websocket.vo.WebSocketReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Component
@ServerEndpoint(value = "/socket", configurator = WebSocketConfig.class)
public class WebSocketServer {
    /*websocket 中引入类的写法*/
    private static SecurityJwtUtils securityJwtUtils;
    private static RedisTemplate redisTemplate;
    //AtomicInteger  类似于Integer数据类型，具有原子性，在++的时候不会被多线程干扰
    public static AtomicInteger online = new AtomicInteger();
    /*redis 前缀*/
    private static final String admin_login = "LOGIN:";
    /*创建map集合  key值用户名  value值记录所有登陆用户操作的session值*/
    private static Map<String, Session> sessionPools = new HashMap<String, Session>();
    /*返回值*/
    private static WebSocketReturn webSocketReturn;

    @OnOpen/*与前端建立连接*/
    public void onOpen(Session session) throws IOException {
        String token = (String) session.getUserProperties().get("token");
        String username = securityJwtUtils.getUsername(token);
        /*拼接redis中的用户key值*/
        String adminKey = admin_login + username + ":admin";
        UmsAdmin umsAdmin = (UmsAdmin) redisTemplate.opsForValue().get(adminKey);
        sessionPools.put(username, session);
        /*AtomicInteger的+1方法 */
        online.incrementAndGet();
        /*循环集合中的所有session*/
        for (Session s : sessionPools.values()
        ) {
            /*判断当前session和集合中的session是否相等*/
            if (!s.equals(session)) {
                webSocketReturn.setCode(200);
                webSocketReturn.setMsg("用户" + username + "已上线,当前在线人数"+online);
                /*向当前session发送自定义的字符串*/
                s.getBasicRemote().sendText(JSONUtil.toJsonStr(webSocketReturn));
            }
        }

    }

    @OnClose/*关闭连接*/
    public void onClose(Session session) throws IOException {
        String token = (String) session.getUserProperties().get("token");
        /*获取用户名*/
        String username = securityJwtUtils.getUsername(token);
        /*AtomicInteger的 -1 方法 */
        online.decrementAndGet();
        System.out.println(username + "已下线");
        /*移除集合中的 用户*/
        sessionPools.remove(username);
        for (Session s: sessionPools.values()
             ) {
            if (!s.equals(session)) {
                webSocketReturn.setCode(300);
                webSocketReturn.setMsg("用户" + username + "已下线,当前在线人数"+online);
                /*向当前session发送自定义的字符串*/
                s.getBasicRemote().sendText(JSONUtil.toJsonStr(webSocketReturn));
            }
        }
    }

    @OnMessage/*websocket 的主要功能 用于给客户端推送信息*/
    public void onMessage(String message) {


    }

    /*判断用户是否在异地登陆*/
    public void checkCurrUserLogin(String username) throws IOException {

        Session session = sessionPools.get(username);
        if (session != null) {
            webSocketReturn.setCode(100);
            webSocketReturn.setMsg("你的账号 " + username + " ，正在进行异地登录，请确定是否是本人操作");
            session.getBasicRemote().sendText(JSONUtil.toJsonStr(webSocketReturn));
        }

    }

    @OnError/*报错提示*/
    public void onError(Session session, Throwable throwable) {
        System.out.println("系统出错");
        /*打印异常*/
        throwable.printStackTrace();
    }

    @Autowired
    public void setSecurityJwtUtils(SecurityJwtUtils securityJwtUtils) {
        this.securityJwtUtils = securityJwtUtils;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setWebSocketReturn(WebSocketReturn webSocketReturn) {
        this.webSocketReturn = webSocketReturn;
    }



}
