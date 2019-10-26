package com.pjjlt.easyfile.moudle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.exception.LoginException;
import com.pjjlt.easyfile.moudle.entity.SysUserToken;
import com.pjjlt.easyfile.moudle.mapper.SysUserTokenMapper;
import com.pjjlt.easyfile.moudle.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken>
        implements SysUserTokenService {

    @Autowired
    HttpServletRequest httpServletRequest;
    /**
     * 登出，删除token
     * */
    @Override
    public void logout() throws Exception{
        String token = httpServletRequest.getHeader("token");
        if (Objects.isNull(token)){
            throw new LoginException("token不存在",ResultEnum.LOGOUT_ERROR);
        }
        LambdaQueryWrapper<SysUserToken> sysUserWrapper = Wrappers.<SysUserToken>lambdaQuery()
                .eq(SysUserToken::getToken,token);
        baseMapper.delete(sysUserWrapper);
    }
}
