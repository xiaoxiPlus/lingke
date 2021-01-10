package com.fh.umsAdmin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.codeGather.CommonReturn;
import com.fh.umsAdmin.entity.UmsAdminRoleRelation;
import com.fh.umsAdmin.service.IUmsAdminRoleRelationService;
import com.fh.umsRole.entity.RoleMenuVo;
import com.fh.umsRole.entity.UmsRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/umsAdmin/ums-admin-role-relation")
public class UmsAdminRoleRelationController {

    @Autowired
    private IUmsAdminRoleRelationService roleRelationService;

    @GetMapping("/{adminId}")
    public CommonReturn queryRoleByAdminId(@PathVariable("adminId") Integer adminId) {
        QueryWrapper<UmsAdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id", adminId);
        List<UmsAdminRoleRelation> adminRoleRelations = roleRelationService.list(wrapper);
        return CommonReturn.success(adminRoleRelations);
    }

    @PostMapping
    public CommonReturn saveOrUpdateRoleRelation(@RequestBody RoleMenuVo roleMenuVo) {
        List<Object> roleIds = roleMenuVo.getMenuIds();
        Integer adminId = roleMenuVo.getParentId();
        QueryWrapper<UmsAdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("admin_id", adminId);
        roleRelationService.remove(wrapper);

        for (int i = 0; i < roleIds.size(); i++) {
            UmsAdminRoleRelation adminRoleRelation = new UmsAdminRoleRelation();
            adminRoleRelation.setAdminId(adminId.longValue());
            adminRoleRelation.setRoleId(Long.valueOf(String.valueOf(roleIds.get(i))));
            roleRelationService.saveOrUpdate(adminRoleRelation);
        }

        return CommonReturn.success();
    }

}
