package com.fh.umsResource.mapper;

import com.fh.umsResource.entity.UmsResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 后台资源表 Mapper 接口
 * </p>
 *
 * @author liqiang
 * @since 2020-12-17
 */
@Mapper
@Repository
public interface UmsResourceMapper extends BaseMapper<UmsResource> {

    List<UmsResource> queryResourceByAdmin(Long id);
}
