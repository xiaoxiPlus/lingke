package com.fh.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.umsAdmin.entity.UmsAdmin;
import com.fh.umsAdmin.service.IUmsAdminService;
import com.fh.umsMenu.entity.UmsMenu;
import com.fh.umsMenu.mapper.UmsMenuMapper;
import com.fh.menu.service.IUmsMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台菜单表 服务实现类
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@Service
public class UmsMenuServiceImpl extends ServiceImpl<UmsMenuMapper, UmsMenu> implements IUmsMenuService {

    @Autowired
    private IUmsAdminService adminService;
    @Resource
    private UmsMenuMapper menuMapper;

    @Override
    public List<Map<String, Object>> queryErectMenuTree(String username) {
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        UmsAdmin umsAdmin = adminService.getOne(wrapper);

       List<UmsMenu> list = menuMapper.queryErectMenuTree(umsAdmin.getId());

        return queryErectMenuTreeDiGui(list, 0);
    }

    private List<Map<String, Object>> queryErectMenuTreeDiGui(List<UmsMenu> menuList, Integer pid) {
        List<Map<String, Object>> list = new ArrayList<>();
        menuList.forEach(menu -> {
            Map<String, Object> map = null;
            if (pid.equals(menu.getParentId())) {
                map = new HashMap<>();
                map.put("icon", menu.getIcon());
                map.put("title", menu.getTitle());
                if (menu.getParentId() == 0) {
                    if (!menu.getTitle().equals("系统首页")) {
                        map.put("index", menu.getId());
                        map.put("subs", queryErectMenuTreeDiGui(menuList, menu.getId()));
                    } else {
                        map.put("index", menu.getName());
                    }
                } else {
                    map.put("index", menu.getName());
                }

            }
            if (map != null) {
                list.add(map);
            }
        });

        return list;
    }


    @Override
    public List<Map<String, Object>> queryMenuTree() {
        List<UmsMenu> list = super.list();
        return queryMenuTreeDiGui(list, 0);
    }

    private List<Map<String, Object>> queryMenuTreeDiGui(List<UmsMenu> menuList, Integer pid) {
        List<Map<String, Object>> listMap = new ArrayList<>();
        menuList.forEach(menu -> {
            Map<String, Object> map = null;
            if (pid.equals(menu.getParentId())) {
                map = new HashMap<>();
                map.put("id", menu.getId());
                map.put("label", menu.getTitle());
                map.put("children", queryMenuTreeDiGui(menuList, menu.getId()));
            }
            if (map != null) {
                listMap.add(map);
            }
        });
        return listMap;
    }
}
