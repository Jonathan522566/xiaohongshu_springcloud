package com.zx.framework.common.response;

import com.zx.framework.common.exception.BaseExceptionInterface;
import com.zx.framework.common.exception.BizException;
import lombok.Data;

import java.io.Serializable;

//一个类只有实现了Serializable接口，它的对象才能被序列化。
//序列化:字符变字节

//poseman以及前端返回包装类
@Data
public class Response<T> implements Serializable {
    //是否成功,默认为true
    private boolean success=true;
    //响应信息
    private String message;
    //异常码
    private String errorCode;
    //响应数据
    private T data;

    //成功======================================================================
    //<T>表示该方法为范形方法,response<T>表示为返回值
    public static <T> Response<T> success(T data){
        Response<T> response=new Response<>();
        response.setData(data);
        return  response;
    }
    public static <T> Response<T> success(){
        Response<T> response = new Response<>();
        return response;
    }

    //失败======================================================================
    public static <T> Response<T> fail(){
        Response<T> response=new Response<>();
        response.setSuccess(false);
        return  response;
    }

    public static <T> Response<T> fail(String errorMessage){
         Response<T> response=new Response<>();
         response.setSuccess(false);
         response.setMessage(errorMessage);
         return  response;
    }

    public static <T> Response<T> fail(String errorCode,String errorMessage){
         Response<T> response=new Response<>();
         response.setSuccess(false);
         response.setMessage(errorMessage);
         response.setErrorCode(errorCode);
         return response;
    }

    public static <T> Response<T> fail(BizException bizException){
         Response<T> response=new Response<>();
         response.setSuccess(false);
         response.setErrorCode(bizException.getErrorCode());
         response.setMessage(bizException.getMessage());
         return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface){
        Response<T> response=new Response<>();
        response.setSuccess(false);
        response.setMessage(baseExceptionInterface.getErrorMessage());
        response.setErrorCode(baseExceptionInterface.getErrorCode());
        return response;
    }
}
