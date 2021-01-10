package com.fh.menu.service;

import com.fh.umsMenu.entity.UmsMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 服务类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
public interface IUmsMenuService extends IService<UmsMenu> {

    List<Map<String,Object>> queryMenuTree();

    List<Map<String,Object>> queryErectMenuTree(String username);
}
