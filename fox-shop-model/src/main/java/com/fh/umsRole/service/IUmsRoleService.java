package com.fh.umsRole.service;

import com.fh.umsRole.entity.UmsRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
public interface IUmsRoleService extends IService<UmsRole> {

    List<UmsRole> queryRoleByAdmin(Long id, String username);
}
