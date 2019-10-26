package com.pjjlt.easyfile.moudle.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pjjlt.easyfile.moudle.entity.SysUser;
import com.pjjlt.easyfile.security.SecurityUserUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 将所有model共有的属性抽离出来，比如说分页
 *
 * @author pjjlt
 * */

@Data
public class BaseModel implements Serializable {
    protected int pageNum = 1;
    protected int pageSize = 10;
    /**
     * 将分页参数 转换为 简单的Page对象
     *
     * @param <T> 泛型
     * @return {@link Page}
     */
    public <T> Page<T> toPage() {
        return new Page<>(pageNum, pageSize);
    }
    /**
     * 如果不是超级管理员，需要传productId
     * */
    public boolean checkProductId(Object projectId) {
        SysUser sysUser = SecurityUserUtil.getCurrentUser();
        if (Objects.nonNull(sysUser) && sysUser.getUserType() == false && Objects.isNull(projectId)){
            return true;
        }
        return false;
    }
}
