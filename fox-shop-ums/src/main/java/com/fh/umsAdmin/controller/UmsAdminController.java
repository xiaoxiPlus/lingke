package com.fh.umsAdmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.codeGather.CommonReturn;
import com.fh.umsAdmin.entity.UmsAdmin;
import com.fh.umsAdmin.service.IUmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 后台用户表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/umsAdmin/ums-admin")
public class UmsAdminController {

    @Autowired
    private IUmsAdminService adminService;

    @GetMapping
    public CommonReturn queryAdmin(Page<UmsAdmin> page, UmsAdmin umsAdmin) {
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        if (umsAdmin.getUsername() != null && umsAdmin.getUsername() != "") {
            wrapper.like("username", umsAdmin.getUsername());
        }
        IPage<UmsAdmin> ipage = adminService.page(page, wrapper);
        return CommonReturn.success(ipage);
    }

    @PutMapping
    public CommonReturn updateAdminStatus(UmsAdmin admin) {
        adminService.updateById(admin);
        return CommonReturn.success();
    }

    @PostMapping
    public CommonReturn queryAdmin(UmsAdmin admin) {
        String encode = new BCryptPasswordEncoder().encode("123456");
        if (admin.getId() == null) {
            admin.setPassword(encode);
            admin.setCreateTime(new Date());
            admin.setLoginTime(new Date());
        }
        adminService.saveOrUpdate(admin);
        return CommonReturn.success();
    }

    @GetMapping("/{id}")
    public CommonReturn queryAdminById(@PathVariable("id") Integer id) {
        UmsAdmin admin = adminService.getById(id);
        return CommonReturn.success(admin);
    }

    @DeleteMapping
    public CommonReturn deleteAdmin(Integer id) {
        adminService.removeById(id);
        return CommonReturn.success();
    }

    @GetMapping("/queryAdminByUsername/{username}")
    public CommonReturn queryAdminByUsername(@PathVariable("username") String username) {
        QueryWrapper<UmsAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        UmsAdmin umsAdmin = adminService.getOne(wrapper);
        return CommonReturn.success(umsAdmin);
    }


}
