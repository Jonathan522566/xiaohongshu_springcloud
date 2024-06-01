package com.zx.xiaohongshu.auth.exception;

import com.zx.framework.common.exception.BizException;
import com.zx.framework.common.response.Response;
import com.zx.xiaohongshu.auth.enums.ResponseCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@ControllerAdvice  //可以理解为一个controller的aop
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获自定义业务异常
     * @return
     */
    @ExceptionHandler({BizException.class}) //类似于aop的异常拦截controller层出现异常时，进行拦截
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request,BizException e){
         //把异常打印到控制台上
        log.warn("{} request fail,errorCode: {},errorMessage: {}",
                request.getRequestURI(),e.getErrorCode(),e.getErrorMessage());
        return Response.fail(e);
    }

    /**
     * 捕获参数校验异常
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody //@ResponseBody的作用其实是将java对象转为json格式的数据
    public Response<Object> handlMethodArgumentNotValidException(HttpServletRequest request
                                                   ,MethodArgumentNotValidException e){
        //参数错误异常码
        String errorCode= ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();

        //获取BindingResult
        BindingResult bindingResult=e.getBindingResult();

        StringBuilder sb=new StringBuilder();

        //获取校验不通过的字段，并组合错误信息
        //格式为:email邮箱格式不正常,当前值:'13qq.com'
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors->{
               errors.forEach(error->
                          sb.append(error.getField())
                                  .append(" ")
                                  .append(error.getDefaultMessage())
                                  .append(",当前值:''")
                                  .append(error.getRejectedValue())
                                  .append("';")
               );
        });

        //错误信息
        String errorMessage=sb.toString();

        log.warn("{} request error,errorCode: {},errorMessage: {}",request.getRequestURI(),errorCode,errorMessage);
        return Response.fail(errorCode,errorMessage);
    }

    /**
     * 其他类型异常--捕获controller层代码异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request,Exception e){
        log.error("{} request error,",request.getRequestURI(),e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }


}
















