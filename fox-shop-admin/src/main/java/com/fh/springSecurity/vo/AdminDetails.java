package com.fh.springSecurity.vo;

import com.fh.umsAdmin.entity.UmsAdmin;
import com.fh.umsResource.entity.UmsResource;
import com.fh.umsRole.entity.UmsRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AdminDetails implements UserDetails {

    private UmsAdmin umsAdmin;

    private List<UmsRole> roleList;

    private List<UmsResource> resourceList;

    public AdminDetails(UmsAdmin umsAdmin, List<UmsRole> roleList, List<UmsResource> resourceList) {
        this.umsAdmin = umsAdmin;
        this.roleList = roleList;
        this.resourceList = resourceList;
    }

    /*权限认证
     * 返回权限和角色的集合*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        roleList.forEach(role -> {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.getKeyword()));
        });
        resourceList.forEach(resource -> {
            list.add(new SimpleGrantedAuthority(resource.getKeyword()));
        });
        return list;
    }

    /*获取密码*/
    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    /*获取用户名*/
    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    /*是否过期
     * token认证不用这个方法
     * cokie——session需要重写*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*是否锁定*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*凭证是否过期*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*是否禁用*/
    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1);
    }
}
