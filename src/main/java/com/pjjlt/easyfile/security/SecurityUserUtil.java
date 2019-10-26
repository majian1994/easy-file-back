package com.pjjlt.easyfile.security;

import com.pjjlt.easyfile.moudle.entity.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 获取Security主体工具类
 *
 * @author pjjlt
 * */

public class SecurityUserUtil {

    public static SysUser getCurrentUser(){
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (Objects.nonNull(securityUser) && Objects.nonNull(securityUser.getSysUser())){
            return securityUser.getSysUser();
        }
        return null;
    }

}
