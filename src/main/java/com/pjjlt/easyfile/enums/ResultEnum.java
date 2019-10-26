package com.pjjlt.easyfile.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回值code、msg枚举类
 * @author pjjlt
 * */

@Getter
@AllArgsConstructor
public enum  ResultEnum {

    SUCCESS("0000","成功"),
    ERROR("1000","失败"),
    PARAM_ERROR("1001","参数异常"),
    LOGIN_ERROR("1002","登录失败"),
    PASSWORD_ERROR("1003","密码错误"),
    DELETE_ERROR("1004","删除异常"),
    LOGOUT_ERROR("1005","登出失败"),
    DATA_REPEAT("1006","重复数据");

    private String code;
    private String msg;
}
