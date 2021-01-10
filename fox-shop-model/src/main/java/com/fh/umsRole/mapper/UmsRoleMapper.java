package com.fh.umsRole.mapper;

import com.fh.umsRole.entity.UmsRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 Mapper 接口
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@Mapper
@Repository
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    List<UmsRole> queryRoleByAdmin(Long id);
}
