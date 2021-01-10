package com.fh.role.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.codeGather.CommonReturn;
import com.fh.umsRole.entity.RoleMenuVo;
import com.fh.umsRole.entity.UmsRoleMenuRelation;
import com.fh.umsRole.service.IUmsRoleMenuRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台角色菜单关系表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@RestController
@RequestMapping("/umsRole/ums-role-menu-relation")
public class UmsRoleMenuRelationController {

    @Autowired
    private IUmsRoleMenuRelationService roleMenuRelationService;

    @PostMapping
    public CommonReturn saveOrUpdateRoleMenuRelation(@RequestBody RoleMenuVo roleMenuVo) {
        QueryWrapper<UmsRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleMenuVo.getParentId());
        roleMenuRelationService.remove(wrapper);

        for (int i = 0; i < roleMenuVo.getMenuIds().size(); i++) {
            UmsRoleMenuRelation roleMenuRelation = new UmsRoleMenuRelation();
            roleMenuRelation.setRoleId(roleMenuVo.getParentId());
            roleMenuRelation.setMenuId((Integer) roleMenuVo.getMenuIds().get(i));
            roleMenuRelationService.save(roleMenuRelation);
        }

        return CommonReturn.success();
    }

}
