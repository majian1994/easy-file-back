package com.pjjlt.easyfile.moudle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.constant.ConstantData;
import com.pjjlt.easyfile.enums.FileEnum;
import com.pjjlt.easyfile.moudle.entity.*;
import com.pjjlt.easyfile.moudle.mapper.ProjectFileMapper;
import com.pjjlt.easyfile.moudle.model.ProjectFileModel;
import com.pjjlt.easyfile.moudle.service.ProjectFileDetailService;
import com.pjjlt.easyfile.moudle.service.ProjectFileService;
import com.pjjlt.easyfile.moudle.service.ProjectFolderService;
import com.pjjlt.easyfile.moudle.service.ProjectInfoService;
import com.pjjlt.easyfile.security.SecurityUserUtil;
import com.pjjlt.easyfile.util.VerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Service
public class ProjectFileServiceImpl extends ServiceImpl<ProjectFileMapper, ProjectFile>
        implements ProjectFileService {

    @Autowired
    ProjectFileDetailService projectFileDetailService;
    @Autowired
    ProjectFolderService projectFolderService;
    @Autowired
    ProjectInfoService projectInfoService;
    /**
     * 新建文档
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProjectFileModel projectFileModel) throws Exception {
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(sysUser)){
            throw new Exception("当前登陆人不存在!");
        }
        ProjectFile projectFile = new ProjectFile();
        BeanUtil.copyProperties(projectFileModel,projectFile, ConstantData.id);
        projectFile.setUserId(sysUser.getId());
        baseMapper.insert(projectFile);
        ProjectFileDetail projectFileDetail = new ProjectFileDetail()
                .setDetail(projectFileModel.getDetail())
                .setId(projectFile.getId());
        projectFileDetailService.save(projectFileDetail);
    }

    /**
     * 获取文档详情
     * */
    @Override
    public JSONObject info(ProjectFileModel projectFileModel) throws Exception {
        JSONObject projectFile = baseMapper.selectInfoById(projectFileModel.getId());
        if (Objects.isNull(projectFile)){
            throw new Exception("未找到该文件");
        }
        try{
            FileEnum fileEnum = FileEnum.valueOf(projectFile.getStr("fileStatus"));
            projectFile.putOpt("fileStatusName",fileEnum.getDoc());
        }catch (Exception e){
            log.error("枚举类转化异常");
        }
        return projectFile;
    }

    /**
     * 更新文档
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFile(ProjectFileModel projectFileModel) throws Exception{
        ProjectFile projectFile = baseMapper.selectById(projectFileModel.getId());
        try {
            checkPerm(projectFileModel,"你没有权力更改此文档!");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        projectFile.setFileStatus(projectFileModel.getFileStatus()).setRemark(projectFileModel.getRemark())
                .setFileName(projectFileModel.getFileName()).setModifyTime(LocalDateTime.now());
        baseMapper.updateById(projectFile);
        ProjectFileDetail projectFileDetail = projectFileDetailService.getById(projectFileModel.getId());
        projectFileDetail.setDetail(projectFileDetail.getDetail()).setModifyTime(LocalDateTime.now());
        projectFileDetailService.updateById(projectFileDetail);
    }

    /**
     * 删除文档
     * */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(ProjectFileModel projectFileModel) throws Exception {
        ProjectFile projectFile = baseMapper.selectById(projectFileModel.getId());
        try {
            checkPerm(projectFileModel,"你没有权力删除此文档!");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        projectFileDetailService.removeById(projectFile.getId());
        baseMapper.deleteById(projectFile);
    }
    /**
     * 分页获取文档列表
     * */
    @Override
    public Page<JSONObject> getPage(ProjectFileModel projectFileModel) throws Exception{
        projectFileModel.pre();
        Page<JSONObject> page = projectFileModel.toPage();
        Page<JSONObject> result = baseMapper.getPage(page,projectFileModel);
        return result;
    }
    /**
     * 加入回收站
     * */
    @Override
    public void recycle(ProjectFileModel projectFileModel) throws Exception{
        ProjectFile projectFile = baseMapper.selectById(projectFileModel.getId());
        try {
            checkPerm(projectFileModel,"你没有权力操作此文档!");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        projectFile.setFileStatus(FileEnum.RECYCLE);
        baseMapper.updateById(projectFile);
    }
    /**
     * 将草稿发布为正式文章
     * */
    @Override
    public void publish(ProjectFileModel projectFileModel) throws Exception {
        ProjectFile projectFile = baseMapper.selectById(projectFileModel.getId());
        try {
            checkPerm(projectFileModel,"你没有权力操作此文档!");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        projectFile.setFileStatus(FileEnum.NORMAL);
        baseMapper.updateById(projectFile);
    }
    /**
     * 回收站还原
     * */
    @Override
    public void restore(ProjectFileModel projectFileModel) throws Exception {
        ProjectFile projectFile = baseMapper.selectById(projectFileModel.getId());
        try {
            checkPerm(projectFileModel,"你没有权力操作此文档!");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        ProjectFolder projectFolder = projectFolderService.getById(projectFile.getFolderId());
        ProjectInfo projectInfo = projectInfoService.getById(projectFile.getProjectId());
        if (VerifyUtil.hasNull(projectFolder,projectInfo)){
            throw new Exception("该文档所在文件夹或者项目已不存在,无法还原!");
        }
        projectFile.setFileStatus(FileEnum.NORMAL);
        baseMapper.updateById(projectFile);
    }

    private void checkPerm(ProjectFileModel projectFileModel,String errorMsg) throws Exception{
        ProjectFile projectFile = baseMapper.selectById(projectFileModel.getId());
        if (Objects.isNull(projectFile)){
            throw new Exception("未找到该文件");
        }
        SysUser currentUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(currentUser)){
            throw new Exception("当前用户不存在");
        }
        if (!currentUser.getUserType() && currentUser.getId()!=projectFileModel.getUserId()){
            throw new Exception(errorMsg);
        }
    }
}
