package com.fh;

import com.fh.codeGather.CommonReturn;
import com.fh.codeGather.ReturnCode;
import com.fh.user.entity.TSysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    //在配置文件中把数据写成活的，在类中加注解调用
    @Value("${user.jwtset.signatrue}")
    private String signatrue;
    @Value("${user.jwtset.exptime}")
    private Long exptime;
    @Value("${user.jwtset.alg}")
    private String alg;
    @Value("${user.jwtset.type}")
    private String type;


    public String createToken(TSysUser user) {

        //jwtToken 三部分组成，公开的且不安全
        //1.头部信息
        Map<String, Object> headMap = new HashMap<>();
        //加密算法
        headMap.put("alg", alg);
        //生成token的类型
        headMap.put("type", type);
        //2.有效负载,存放用户的信息
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("userId", user.getId());
        payloadMap.put("userName", user.getUsername());
        //3.失效时间
        Long startTime = System.currentTimeMillis();
        Long expTime = startTime + exptime;
        //生成jwt
        String token = Jwts.builder()
                .setHeader(headMap)
                //设置载荷和失效时间，顺序不能乱
                .setClaims(payloadMap)
                .setExpiration(new Date(expTime))
                //解析字符串的签名,密钥不能太短。。。。
                .signWith(SignatureAlgorithm.HS256, signatrue)
                .compact();

        return token;
    }

    //验证token中的数据
    public CommonReturn refToken(String token) {

        try {
            //解析token
            Claims claim = Jwts.parser()
                    //解析token的密钥，签名
                    .setSigningKey(signatrue)
                    .parseClaimsJws(token)
                    //返回有效载荷
                    .getBody();
            //载荷中的数据：自己存的用户名，用户id以及失效时间
            return CommonReturn.success(claim);
        } catch (Exception e) {
            //会有各种的异常：过期异常，签名有误，JWT格式不对
            e.printStackTrace();
            return CommonReturn.error(ReturnCode.LOGIN_DISABLED);
        }
    }

}
