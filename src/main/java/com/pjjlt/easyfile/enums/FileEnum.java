package com.pjjlt.easyfile.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态枚举类
 * @author majian
 * */
@Getter
@AllArgsConstructor
public enum FileEnum {

    DRAFT("草稿"),
    NORMAL("正式文章"),
    RECYCLE("回收站");


    private String doc;
}
