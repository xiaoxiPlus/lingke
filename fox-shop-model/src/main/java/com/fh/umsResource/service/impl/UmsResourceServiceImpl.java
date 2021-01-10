package com.fh.umsResource.service.impl;

import com.fh.umsResource.entity.UmsResource;
import com.fh.umsResource.mapper.UmsResourceMapper;
import com.fh.umsResource.service.IUmsResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台资源表 服务实现类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@Service
public class UmsResourceServiceImpl extends ServiceImpl<UmsResourceMapper, UmsResource> implements IUmsResourceService {
    @Autowired
    private UmsResourceMapper resourceMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String pre_login = "LOGIN:";

    @Override
    public List<UmsResource> queryResourceByAdmin(Long id, String username) {
        String key = pre_login+username+":resource";
        if (redisTemplate.hasKey(key)){
            return (List<UmsResource>) redisTemplate.opsForValue().get(key);
        }
        List<UmsResource> resourceList = resourceMapper.queryResourceByAdmin(id);
        redisTemplate.opsForValue().set(key,resourceList);
        redisTemplate.expire(key,3,TimeUnit.HOURS);
        return resourceList;
    }

}
