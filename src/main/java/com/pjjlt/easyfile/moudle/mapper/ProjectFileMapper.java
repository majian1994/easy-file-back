package com.pjjlt.easyfile.moudle.mapper;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjjlt.easyfile.moudle.entity.ProjectFile;
import com.pjjlt.easyfile.moudle.model.ProjectFileModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProjectFileMapper extends BaseMapper<ProjectFile> {
    //通过id查询详情
    JSONObject selectInfoById(Long id);
    //分页获取数据列表
    Page<JSONObject> getPage(@Param("page") Page<JSONObject> page
            ,@Param("projectFileModel") ProjectFileModel projectFileModel);
}
