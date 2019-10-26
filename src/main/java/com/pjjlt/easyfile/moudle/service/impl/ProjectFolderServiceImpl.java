package com.pjjlt.easyfile.moudle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.constant.ConstantData;
import com.pjjlt.easyfile.enums.FileEnum;
import com.pjjlt.easyfile.moudle.entity.ProjectFile;
import com.pjjlt.easyfile.moudle.entity.ProjectFolder;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.moudle.mapper.ProjectFolderMapper;
import com.pjjlt.easyfile.moudle.model.ProjectFolderModel;
import com.pjjlt.easyfile.moudle.service.ProjectFileService;
import com.pjjlt.easyfile.moudle.service.ProjectFolderService;
import com.pjjlt.easyfile.security.SecurityUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProjectFolderServiceImpl extends ServiceImpl<ProjectFolderMapper, ProjectFolder>
        implements ProjectFolderService {

    @Autowired
    ProjectFileService projectFileService;
    @Override
    public void create(ProjectFolderModel projectFolderModel) throws Exception {
        projectFolderModel.pre();
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(sysUser)){
            throw new Exception("当前登陆人不存在!");
        }
        if (projectFolderModel.getParentId() != 0){
            LambdaQueryWrapper<ProjectFolder> projectFolderLambdaQueryWrapper = Wrappers.<ProjectFolder>lambdaQuery()
                    .eq(ProjectFolder::getProjectId, projectFolderModel.getProjectId())
                    .eq(ProjectFolder::getId, projectFolderModel.getParentId());
            ProjectFolder p = baseMapper.selectOne(projectFolderLambdaQueryWrapper);
            if (Objects.isNull(p)){
                throw new Exception("本项目不存在指定父文件夹!");
            }
        }
        ProjectFolder projectFolder = new ProjectFolder();
        BeanUtil.copyProperties(projectFolderModel,projectFolder, ConstantData.id);
        projectFolder.setUserId(sysUser.getId());
        baseMapper.insert(projectFolder);
    }

    @Override
    public void updateProjectFolder(ProjectFolderModel projectFolderModel) throws Exception {
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.isNull(sysUser)){
            throw new Exception("当前登陆人不存在!");
        }
        ProjectFolder projectFolder = baseMapper.selectById(projectFolderModel.getId());
        if (Objects.isNull(projectFolder)){
            throw new Exception("文件夹不存在!");
        }
        projectFolder.setDescription(projectFolderModel.getDescription())
                .setFolderName(projectFolderModel.getFolderName())
                .setRemark(projectFolderModel.getRemark())
                .setModifyTime(LocalDateTime.now());
        baseMapper.updateById(projectFolder);
    }

    @Override
    public ProjectFolder info(ProjectFolderModel projectFolderModel) throws Exception{
        ProjectFolder projectFolder = baseMapper.selectById(projectFolderModel.getId());
        if (Objects.isNull(projectFolder)){
            throw new Exception("文件夹不存在!");
        }
        return projectFolder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProjectFolder(ProjectFolderModel projectFolderModel) throws Exception {
        ProjectFolder projectFolder = baseMapper.selectById(projectFolderModel.getId());
        if (Objects.isNull(projectFolder)){
            throw new Exception("文件夹不存在!");
        }
        // 递归找到所有孩子文件夹节点，文件进入回收站 删除文件夹
        LambdaQueryWrapper<ProjectFolder> projectFolderLambdaQueryWrapper = Wrappers.<ProjectFolder>lambdaQuery()
                .eq(ProjectFolder::getProjectId, projectFolder.getProjectId());
        List<ProjectFolder> allFolderList = baseMapper.selectList(projectFolderLambdaQueryWrapper);
        Set<ProjectFolder> needDelFolderList = new HashSet<>();
        getNeedDelFolderList(projectFolderModel.getId(),allFolderList,needDelFolderList);
        for (ProjectFolder pf:needDelFolderList){
            ProjectFile setProjectFile = new ProjectFile().setFileStatus(FileEnum.RECYCLE);
            LambdaUpdateWrapper<ProjectFile> projectFileWrapper = Wrappers.<ProjectFile>lambdaUpdate()
                    .eq(ProjectFile::getFolderId, pf.getId());
            projectFileService.update(setProjectFile,projectFileWrapper);
            baseMapper.deleteById(pf);
        }
    }


    @Override
    public List<JSONObject> tree(ProjectFolderModel projectFolderModel) throws Exception {
        List<JSONObject> result = new ArrayList<>();
        LambdaQueryWrapper<ProjectFolder> projectFolderLambdaQueryWrapper
                = Wrappers.<ProjectFolder>lambdaQuery()
                .eq(ProjectFolder::getProjectId, projectFolderModel.getProjectId());
        List<ProjectFolder> projectFolderList = baseMapper.selectList(projectFolderLambdaQueryWrapper);
        //找到所有根节点 parentId = 0的数据
        List<ProjectFolder> projectFolderRootList = new ArrayList<>();
        for (ProjectFolder pf:projectFolderList){
            if (pf.getParentId() == 0){
                projectFolderRootList.add(pf);
            }
        }
        projectFolderList.removeAll(projectFolderRootList);
        //递归造树
        for (ProjectFolder pf:projectFolderRootList){
            JSONObject node = new JSONObject();
            node.putOpt(ConstantData.id,pf.getId());
            node.putOpt(ConstantData.folderName,pf.getFolderName());
            buildChildren(node,projectFolderList);
            result.add(node);
        }
        return result;
    }
    //递归装孩子节点
    private void buildChildren(JSONObject node, List<ProjectFolder> projectFolderList) {
        List<JSONObject> childrenList = new ArrayList<>();
        for (ProjectFolder pf:projectFolderList){
            if (pf.getParentId() == node.getLong(ConstantData.id)){
                JSONObject childNode = new JSONObject();
                childNode.putOpt(ConstantData.id,pf.getId());
                childNode.putOpt(ConstantData.folderName,pf.getFolderName());
                buildChildren(childNode,projectFolderList);
                childrenList.add(childNode);
            }
        }
        node.putOpt(ConstantData.children,childrenList);
    }
    //找到该id的文件夹，以及子文件夹
    private void getNeedDelFolderList(Long id, List<ProjectFolder> allFolderList,
                                                     Set<ProjectFolder> needDelFolderList) {
        for (ProjectFolder projectFolder:allFolderList){
            if (id == projectFolder.getId()){
                needDelFolderList.add(projectFolder);
            }else if(id == projectFolder.getParentId()){
                needDelFolderList.add(projectFolder);
                getNeedDelFolderList(projectFolder.getId(),allFolderList,needDelFolderList);
            }
        }
    }
}
