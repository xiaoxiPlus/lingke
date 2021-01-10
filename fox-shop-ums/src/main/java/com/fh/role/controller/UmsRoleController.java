package com.fh.role.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.codeGather.CommonReturn;
import com.fh.umsRole.entity.UmsRole;
import com.fh.umsRole.service.IUmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 后台用户角色表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/umsRole/ums-role")
public class UmsRoleController {

    @Autowired
    private IUmsRoleService roleService;

    @GetMapping
    public CommonReturn queryRole(Page<UmsRole> page, UmsRole UmsRole) {
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        if (UmsRole.getName() != null && UmsRole.getName() != "") {
            wrapper.like("name", UmsRole.getName());
        }
        IPage<UmsRole> ipage = roleService.page(page, wrapper);
        return CommonReturn.success(ipage);
    }

    @PutMapping
    public CommonReturn updateAdminStatus(UmsRole role) {
        roleService.updateById(role);
        return CommonReturn.success();
    }

    @PostMapping
    public CommonReturn queryRole(UmsRole role) {
        if (role.getId() == null){
            role.setCreateTime(new Date());
            role.setAdminCount(0);
        }
        roleService.saveOrUpdate(role);
        return CommonReturn.success();
    }

    @GetMapping("/{id}")
    public CommonReturn queryAdminById(@PathVariable("id") Integer id) {
        UmsRole role = roleService.getById(id);
        return CommonReturn.success(role);
    }

    @DeleteMapping
    public CommonReturn deleteAdmin(Integer id) {
        roleService.removeById(id);
        return CommonReturn.success();
    }

    @GetMapping("/queryAdminByOptions")
    public CommonReturn queryAdminByOptions() {
        List<UmsRole> list = roleService.list();
        List<Map<String,Object>> roleList = new ArrayList<>();
        list.forEach(role->{
            Map<String,Object> roleMap = new HashMap<>();
            roleMap.put("value",role.getId());
            roleMap.put("label",role.getName());
            roleList.add(roleMap);
        });
        return CommonReturn.success(roleList);
    }

}
