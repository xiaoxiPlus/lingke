package com.fh.ums_member.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.codeGather.CommonReturn;
import com.fh.ums_member.entity.Level;
import com.fh.ums_member.service.ILevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 会员等级表 前端控制器
 * </p>
 *
 * @author liqiang
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/ums_member/level")
public class LevelController {

    @Autowired
    private ILevelService iLevelService;

    @GetMapping("/{defaultStatus}")
    public CommonReturn queryLevelOptions(@PathVariable("defaultStatus")Integer defaultStatus){
        QueryWrapper<Level> wrapper = new QueryWrapper<>();
        wrapper.eq("default_status",defaultStatus);
        List<Level> list = iLevelService.list(wrapper);
        return CommonReturn.success(list);
    }
}
