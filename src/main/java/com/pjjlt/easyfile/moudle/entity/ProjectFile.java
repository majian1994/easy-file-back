package com.pjjlt.easyfile.moudle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.pjjlt.easyfile.enums.FileEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文档表
 * </p>
 *
 * @author pjjlt
 * @since 2019-10-16
 */
@Data
@Accessors(chain = true)
public class ProjectFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文档id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 文件夹ID
     */
    private Long folderId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件状态 (DRAFT 草稿、NORMAL 正式文章、RECYCLE 回收站)
     * */
    private FileEnum fileStatus;

    /**
     * 创建者id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;

    /**
     * 备注
     */
    private String remark;


}
