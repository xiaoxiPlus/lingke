package com.fh.umsRole.entity;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuVo {

    private  Integer parentId;

    private List<Object> menuIds;
}
