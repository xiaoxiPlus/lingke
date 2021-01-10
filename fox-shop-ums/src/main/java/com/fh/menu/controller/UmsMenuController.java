package com.fh.menu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.codeGather.CommonReturn;
import com.fh.umsMenu.entity.UmsMenu;
import com.fh.menu.service.IUmsMenuService;
import com.fh.umsRole.entity.UmsRoleMenuRelation;
import com.fh.umsRole.service.IUmsRoleMenuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 后台菜单表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/umsMenu/ums-menu")
public class UmsMenuController {

    @Autowired
    private IUmsMenuService menuService;
    @Autowired
    private IUmsRoleMenuRelationService menuRelationService;

    @GetMapping
    public CommonReturn queryMenu(Page<UmsMenu> page) {
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("level", 0);
        IPage<UmsMenu> ipage = menuService.page(page, wrapper);
        return CommonReturn.success(ipage);
    }

    @PutMapping
    public CommonReturn updateMenuStatus(UmsMenu menu) {
        menuService.updateById(menu);
        return CommonReturn.success();
    }

    @PostMapping
    public CommonReturn saveOrUpdateMenu(UmsMenu menu) {
        if (menu.getId() == null) {
            menu.setCreateTime(new Date());
        }
        if (menu.getParentId() == 0) {
            menu.setLevel(0);
        } else {
            menu.setLevel(1);
        }

        menuService.saveOrUpdate(menu);
        return CommonReturn.success();
    }

    @GetMapping("/{id}")
    public CommonReturn queryMenuById(@PathVariable("id") Integer id) {
        UmsMenu menu = menuService.getById(id);
        return CommonReturn.success(menu);
    }

    @DeleteMapping
    public CommonReturn deleteMenu(Integer id) {
        menuService.removeById(id);
        return CommonReturn.success();
    }

    @GetMapping("/level")
    public CommonReturn queryLevelMenu(Page<UmsMenu> page, Integer id) {
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        wrapper.eq("level", 1);
        IPage<UmsMenu> ipage = menuService.page(page, wrapper);
        return CommonReturn.success(ipage);
    }

    @GetMapping("/queryMenuTree")
    public List<Map<String, Object>> queryMenuTree() {
        List<Map<String, Object>> list = menuService.queryMenuTree();
        return list;
    }

    @GetMapping("/queryMenuChecked/{id}")
    public CommonReturn queryMenuChecked(@PathVariable("id") Integer id){
        QueryWrapper<UmsRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",id);
        List<UmsRoleMenuRelation> roleMenulist = menuRelationService.list(wrapper);
        List<Object> list = new ArrayList<>();
        if (roleMenulist.size()!=0){
            roleMenulist.forEach(roleMenu->{
                list.add(roleMenu.getMenuId());
            });
        }
        return CommonReturn.success(list);
    }

    @GetMapping("/queryErectMenuTree/{username}")
    public List<Map<String, Object>> queryErectMenuTree(@PathVariable("username") String username) {
        List<Map<String, Object>> list = menuService.queryErectMenuTree(username);
        return list;
    }

}
