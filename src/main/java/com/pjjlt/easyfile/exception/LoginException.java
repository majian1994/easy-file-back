package com.pjjlt.easyfile.exception;

import com.pjjlt.easyfile.enums.ResultEnum;
import lombok.Data;

/**
 * 登录登出异常
 * */

@Data
public class LoginException extends Exception {
    ResultEnum resultEnum;

    /** 无参的构造方法 */
    public LoginException() {
        super();
    }
    /**
     * @param message 错误信息
     * @param resultEnum 错误枚举类
     */
    public LoginException(String message,ResultEnum resultEnum) {
        super(message);
        this.resultEnum = resultEnum;
    }
    /**
     * @param message 错误信息
     */
    public LoginException(String message) {
        super(message);
    }
    /**
     * 用指定的详细信息和原因构造一个新的异常
     *
     * @param message   错误信息
     * @param throwable 新的异常
     */
    public LoginException(String message, Throwable throwable) {
        super(message, throwable);
    }
    /**
     * 用指定原因构造一个新的异常
     *
     * @param throwable 新的异常
     */
    public LoginException(Throwable throwable) {
        super(throwable);
    }
}
