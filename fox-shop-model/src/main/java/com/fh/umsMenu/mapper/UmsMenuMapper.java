package com.fh.umsMenu.mapper;

import com.fh.umsMenu.entity.UmsMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 后台菜单表 Mapper 接口
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
public interface UmsMenuMapper extends BaseMapper<UmsMenu> {

    List<UmsMenu> queryErectMenuTree(Long id);
}
