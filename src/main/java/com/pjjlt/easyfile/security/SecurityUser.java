package com.pjjlt.easyfile.security;

import com.pjjlt.easyfile.moudle.entity.SysMenu;
import com.pjjlt.easyfile.moudle.entity.SysRole;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.service.SysRoleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Security 要求需要实现的User类
 * */
@Data
public class SecurityUser implements UserDetails {
    @Autowired
    private SysRoleService sysRoleService;
    //用户登录名(注意此处的username和SysUser的loginName是一个值)
    private String username;
    //登录密码
    private String password;
    //用户id
    private SysUser sysUser;
    //该用户的所有权限
    private List<SysMenu> sysMenuList;


    /**构造函数*/
    public SecurityUser(SysUser sysUser){
        this.username = sysUser.getLoginName();
        this.password = sysUser.getPassword();
        this.sysUser = sysUser;
    }
    public SecurityUser(SysUser sysUser,List<SysMenu> sysMenuList){
        this.username = sysUser.getLoginName();
        this.password = sysUser.getPassword();
        this.sysMenuList = sysMenuList;
        this.sysUser = sysUser;
    }

    /**需要实现的方法*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(SysMenu menu : sysMenuList) {
            authorities.add(new SimpleGrantedAuthority(menu.getPerms()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    //默认账户未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //默认账户没有带锁
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //默认凭证没有过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //默认账户可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
