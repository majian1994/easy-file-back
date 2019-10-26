package com.pjjlt.easyfile.util;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

/**
 * 一些验证信息
 * */

public class VerifyUtil {

    /**
     * 验证是否有数据为空
     * */
    public static boolean hasNull(Object... objects){
        return Arrays.stream(objects).anyMatch(StrUtil::isBlankIfStr);
    }


    /**
     * 验证是否有数据为空
     * */
    public static boolean allNull(Object... objects){
        return Arrays.stream(objects).allMatch(StrUtil::isBlankIfStr);
    }

}
