package com.pjjlt.easyfile.exception;

import com.pjjlt.easyfile.enums.ResultEnum;
import com.pjjlt.easyfile.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@RestControllerAdvice
public class ResultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        return new Result(ResultEnum.ERROR,e.getMessage());
    }


}
