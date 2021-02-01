package com.atguigu.servicebase.exception;

import com.atguigu.Result.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class MyExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R exception(Exception e){
        e.printStackTrace();
        return R.error().message("调用了公共异常处理模块");
    }
}
