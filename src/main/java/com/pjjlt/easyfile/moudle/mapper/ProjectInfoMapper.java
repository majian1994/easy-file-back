package com.pjjlt.easyfile.moudle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjjlt.easyfile.moudle.entity.ProjectInfo;
import com.pjjlt.easyfile.moudle.model.ProjectInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {
    //项目列表
    Page<ProjectInfo> getPage(@Param("page") Page<ProjectInfo> page
            ,@Param("projectInfoModel") ProjectInfoModel projectInfoModel);
}
