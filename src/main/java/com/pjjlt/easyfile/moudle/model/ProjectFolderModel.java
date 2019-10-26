package com.pjjlt.easyfile.moudle.model;

import com.pjjlt.easyfile.util.VerifyUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


@Data
@Accessors(chain = true)
public class ProjectFolderModel extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    /*** 文件夹id*/
    private Long id;
    /*** 项目id*/
    private Long projectId;
    /*** 父文件夹ID*/
    private Long parentId;
    /*** 文件夹名称*/
    private String folderName;
    /*** 创建者id*/
    private Long userId;
    /*** 文件夹描述*/
    private String description;
    /*** 创建时间*/
    private LocalDateTime createTime;
    /*** 更新时间*/
    private LocalDateTime modifyTime;
    /*** 备注*/
    private String remark;
    /**
     * 新增验参
     * */
    public boolean checkCreate() {
        return VerifyUtil.hasNull(this.projectId,this.folderName);
    }
    /**
     * 数据预处理
     * */
    public void pre() {
        if (Objects.isNull(this.parentId)){
            this.setParentId(0L);
        }
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
     * id验参
     * */
    public boolean checkId() {
        return Objects.isNull(this.id);
    }

    public boolean checkProjectId() {
        return Objects.isNull(this.projectId);
    }
}
