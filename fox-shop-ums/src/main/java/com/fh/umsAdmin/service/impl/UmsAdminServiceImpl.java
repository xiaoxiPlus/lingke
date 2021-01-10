package com.fh.umsAdmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.SecurityJwtUtils;
import com.fh.umsAdmin.entity.UmsAdmin;
import com.fh.umsAdmin.mapper.UmsAdminMapper;
import com.fh.umsAdmin.service.IUmsAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台用户表 服务实现类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-16
 */
@Service
public class UmsAdminServiceImpl extends ServiceImpl<UmsAdminMapper, UmsAdmin> implements IUmsAdminService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SecurityJwtUtils securityJwtUtils;
    @Resource
    private PasswordEncoder passwordEncoder;

    private static final String pre_token = "ACCESS_TOKEN:";

    private static final String pre_login = "LOGIN:";

    @Override
    public String login(String username, String password) {
        String token = null;
        try {
            //调用登陆认证的方法
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("用户密码不正确");
            }
            //清除之前的token
            deleteToken(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //让这个对象能够实现上下文共享
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            /*生成token*/
            token = securityJwtUtils.createToken(userDetails);
            /*将token保存到redis中*/
            long currentTime = System.currentTimeMillis();
            String accessKey = pre_token + userDetails.getUsername() + ":" + token;
            redisTemplate.opsForValue().set(accessKey, currentTime);
            redisTemplate.expire(accessKey, 2, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    @Override
    public void adminLoginOut(String username) {
        deleteToken(username);
    }

    private void deleteToken(String username) {
        /*删除之前的token*/
        Set keys = redisTemplate.keys(pre_token + username + ":*");
        if (keys.size() != 0) {
            redisTemplate.delete(keys);
        }
        /*删除之前的用户信息*/
        Set adminKeys = redisTemplate.keys(pre_login + username + ":*");
        if (adminKeys.size() != 0) {
            redisTemplate.delete(adminKeys);
        }

    }

    @Override
    public UmsAdmin queryUserByName(String username) {
        String key = pre_login + username + ":admin";
        if (redisTemplate.hasKey(key)) {
            return (UmsAdmin) redisTemplate.opsForValue().get(key);
        }
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        UmsAdmin umsAdmin = super.getOne(wrapper);
        redisTemplate.opsForValue().set(key,umsAdmin);
        redisTemplate.expire(key,3,TimeUnit.HOURS);
        return umsAdmin;
    }
}
