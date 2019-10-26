package com.pjjlt.easyfile.moudle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pjjlt.easyfile.moudle.entity.SysMenu;
import com.pjjlt.easyfile.moudle.model.SysMenuModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> getList(SysMenuModel sysMenuModel);
}
