package com.pjjlt.easyfile.moudle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件夹表
 * </p>
 *
 * @author pjjlt
 * @since 2019-10-16
 */
@Data
@Accessors(chain = true)
public class ProjectFolder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件夹id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 创建者id
     */
    private Long userId;

    /**
     * 文件夹描述
     */
    private String description;

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
