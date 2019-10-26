package com.pjjlt.easyfile.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.entity.SysUserToken;
import com.pjjlt.easyfile.moudle.service.SysUserService;
import com.pjjlt.easyfile.moudle.service.SysUserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysUserTokenService sysUserTokenService;

    /**
     * 认证授权
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("访问的链接是:{}",httpServletRequest.getRequestURL());
        try {
            final String token = httpServletRequest.getHeader("token");
            LambdaQueryWrapper<SysUserToken> condition = Wrappers.<SysUserToken>lambdaQuery().eq(SysUserToken::getToken, token);
            SysUserToken sysUserToken = sysUserTokenService.getOne(condition);
            if (Objects.nonNull(sysUserToken)){
                SysUser sysUser = sysUserService.getById(sysUserToken.getUserId());
                if (Objects.nonNull(sysUser)){
                    SecurityUser securityUser = securityUserService.loadUserByUsername(sysUser.getLoginName());
                    //将主体放入内存
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (Exception e){
            log.error("认证授权时出错:{}", Arrays.toString(e.getStackTrace()));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
