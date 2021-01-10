package com.fh.springSecurity.filter;

import com.fh.codeGather.CommonReturn;
import com.fh.SecurityJwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final String pre_token = "ACCESS_TOKEN:";

    @Autowired
    private SecurityJwtUtils securityJwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Resource
    private HttpServletRequest request;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头部中的token值
        String token = request.getHeader("Authorization-token");
        //判断是否为空
        if (StringUtils.isNotBlank(token)) {
            //解析token值
            CommonReturn commonReturn = securityJwtUtils.authToken(token);
            //
            if (commonReturn.getCode() == 200 && SecurityContextHolder.getContext().getAuthentication() == null) {
                //解析token中的用户名
                String username = securityJwtUtils.getUsername(token);
                //根据用户名获取整个用户信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //拼接redis中的token值key
                String accessKey = pre_token + userDetails.getUsername() + ":" + token;
                //判断redis中否包含 token
                if (redisTemplate.hasKey(accessKey)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    Long currentTime = System.currentTimeMillis();
                    //续签
                    redisTemplate.opsForValue().set(accessKey, currentTime);
                    redisTemplate.expire(accessKey, 2, TimeUnit.HOURS);

                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
