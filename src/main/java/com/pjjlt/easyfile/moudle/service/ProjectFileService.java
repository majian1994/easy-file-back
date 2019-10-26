package com.pjjlt.easyfile.moudle.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pjjlt.easyfile.moudle.entity.ProjectFile;
import com.pjjlt.easyfile.moudle.model.ProjectFileModel;

public interface ProjectFileService extends IService<ProjectFile> {
    //新增文档
    void create(ProjectFileModel projectFileModel) throws Exception;
    //获取文档详情
    JSONObject info(ProjectFileModel projectFileModel) throws Exception;
    //更新文档
    void updateFile(ProjectFileModel projectFileModel) throws Exception;
    //删除文档
    void deleteFile(ProjectFileModel projectFileModel) throws Exception;
    //获取文档列表
    Page<JSONObject> getPage(ProjectFileModel projectFileModel) throws Exception;
    //正式文章加入回收站
    void recycle(ProjectFileModel projectFileModel) throws Exception;
    //将草稿发布为正式文章
    void publish(ProjectFileModel projectFileModel) throws Exception;
    //回收站还原
    void restore(ProjectFileModel projectFileModel) throws Exception;
}
