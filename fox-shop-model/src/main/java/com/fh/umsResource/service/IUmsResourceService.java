package com.fh.umsResource.service;

import com.fh.umsResource.entity.UmsResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 后台资源表 服务类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
public interface IUmsResourceService extends IService<UmsResource> {

    List<UmsResource> queryResourceByAdmin(Long id, String username);
}
