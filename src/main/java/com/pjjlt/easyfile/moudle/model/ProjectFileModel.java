package com.pjjlt.easyfile.moudle.model;

import com.pjjlt.easyfile.enums.FileEnum;
import com.pjjlt.easyfile.util.VerifyUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class ProjectFileModel extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /*** 文档id */
    private Long id;
    /*** 项目id */
    private Long projectId;
    /*** 文件夹ID*/
    private Long folderId;
    /*** 文件名称*/
    private String fileName;
    /*** 文件状态 (DRAFT 草稿、NORMAL 正式文章、RECYCLE 回收站)* */
    private FileEnum fileStatus;
    /*** 创建者id*/
    private Long userId;
    /*** 创建时间*/
    private LocalDateTime createTime;
    /*** 更新时间*/
    private LocalDateTime modifyTime;
    /*** 备注*/
    private String remark;
    /** 详情*/
    private String detail;
    /** 关键词*/
    private String keyword;
    /**
     * 新增验参
     * */
    public boolean checkCreate() {
        return VerifyUtil.hasNull(this.projectId,this.folderId,this.fileName,this.fileStatus,this.detail);
    }
    /**
     * 验证id是否为空
     * */
    public boolean checkId() {
        return Objects.isNull(this.id);
    }
    /**
     * 更新验参
     * */
    public boolean checkUpdate() {
        boolean check = checkCreate();
        if (check){
            return true;
        }
        return Objects.isNull(this.id);
    }
    /**
     * 列表数据验参
     * */
    public boolean checkList() {
        return VerifyUtil.hasNull(this.projectId);
    }
    /**数据预处理*/
    public void pre() {
        if (Objects.isNull(this.fileStatus)){
            this.setFileStatus(FileEnum.NORMAL);
        }
    }
}
