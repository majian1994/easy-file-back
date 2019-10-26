package com.pjjlt.easyfile.moudle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjjlt.easyfile.moudle.entity.SysRole;
import com.pjjlt.easyfile.moudle.model.SysRoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    List<SysRole> getList(SysRoleModel sysRoleModel);
    //分页获取角色
    Page<SysRole> getPage(@Param("page") Page<SysRole> page,@Param("sysRoleModel") SysRoleModel sysRoleModel);
}
