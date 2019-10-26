package com.pjjlt.easyfile.moudle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.moudle.entity.SysUserProject;
import com.pjjlt.easyfile.moudle.mapper.SysUserProjectMapper;
import com.pjjlt.easyfile.moudle.service.SysUserProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SysUserProjectServiceImpl extends ServiceImpl<SysUserProjectMapper, SysUserProject>
        implements SysUserProjectService {

    @Autowired
    HttpServletRequest httpServletRequest;

}
