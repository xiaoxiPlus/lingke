package com.fh.umsAdmin.service;

import com.fh.umsAdmin.entity.UmsAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 后台用户表 服务类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-16
 */
public interface IUmsAdminService extends IService<UmsAdmin> {

    UmsAdmin queryUserByName(String wrapper);

    String login(String username, String password);

    void adminLoginOut(String username);
}
