package com.zx.framework.common.exception;

import lombok.Getter;
import lombok.Setter;

//使用getter和setter后不用对变量进行get和set函数摘写
@Getter
@Setter
public class BizException extends RuntimeException{
    //异常码
    private String errorCode;
    //异常错误信息
    private String errorMessage;

    //引用baseExceptionInterface的抽象函数
    public BizException(BaseExceptionInterface baseExceptionInterface){
         this.errorCode=baseExceptionInterface.getErrorCode();
         this.errorMessage=baseExceptionInterface.getErrorMessage();
    }
}
