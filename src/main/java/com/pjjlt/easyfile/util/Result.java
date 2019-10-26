package com.pjjlt.easyfile.util;

import cn.hutool.json.JSONObject;
import com.pjjlt.easyfile.enums.ResultEnum;

/**
 * 返回信息类
 * */

public class Result<T> extends JSONObject {

    private static final long serialVersionUID = 1L;

    private String CODE = "code";
    private String MSG = "msg";
    private String DATA = "data";
    private String EMPTY_JSON = "{}";
    private String EMPTY_STR = "";

    public Result(){
        this.putOpt(CODE,ResultEnum.SUCCESS.getCode());
        this.putOpt(MSG,ResultEnum.SUCCESS.getMsg());
        this.putOpt(DATA,EMPTY_JSON);
    }

    public Result(ResultEnum resultEnum){
        this.putOpt(CODE,resultEnum.getCode());
        this.putOpt(MSG,resultEnum.getMsg());
        this.putOpt(DATA,EMPTY_JSON);
    }

    public Result(T data) {
        this.putOpt(CODE,ResultEnum.SUCCESS.getCode());
        this.putOpt(MSG,ResultEnum.SUCCESS.getMsg());
        this.putOpt(DATA,data);
    }

    public Result(ResultEnum resultEnum,T data) {
        this.putOpt(CODE,resultEnum.getCode());
        this.putOpt(MSG,resultEnum.getMsg());
        this.putOpt(DATA,data);
    }

}
