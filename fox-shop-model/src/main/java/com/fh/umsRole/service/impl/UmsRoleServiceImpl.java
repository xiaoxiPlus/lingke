package com.fh.umsRole.service.impl;

import com.fh.umsRole.entity.UmsRole;
import com.fh.umsRole.mapper.UmsRoleMapper;
import com.fh.umsRole.service.IUmsRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 后台用户角色表 服务实现类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@Service
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements IUmsRoleService {
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String pre_login = "LOGIN:";
    @Override
    public List<UmsRole> queryRoleByAdmin(Long id, String username) {
        String key = pre_login+username+":role";
        if (redisTemplate.hasKey(key)){
            return (List<UmsRole>) redisTemplate.opsForValue().get(key);
        }
        List<UmsRole> roleList = roleMapper.queryRoleByAdmin(id);
        redisTemplate.opsForValue().set(key,roleList);
        redisTemplate.expire(key,3,TimeUnit.HOURS);
        return roleList;
    }

}
