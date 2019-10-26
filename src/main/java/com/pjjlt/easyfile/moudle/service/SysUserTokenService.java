package com.pjjlt.easyfile.moudle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pjjlt.easyfile.moudle.entity.SysUserToken;

public interface SysUserTokenService extends IService<SysUserToken> {
    //登出删除token
    void logout() throws Exception;
}
