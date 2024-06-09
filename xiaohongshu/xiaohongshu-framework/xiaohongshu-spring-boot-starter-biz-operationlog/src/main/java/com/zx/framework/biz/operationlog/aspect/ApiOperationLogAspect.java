package com.zx.framework.biz.operationlog.aspect;

import com.zx.framework.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.MissingResourceException;
import java.util.function.Function;
import java.util.stream.Collectors;

//@Slf4j添加了该注释之后，就可以在代码中直接饮用log.info( ) 打印日志了
@Aspect
@Slf4j
public class ApiOperationLogAspect {

       /**以自定义@ApiOperation注解为切点，凡是添加@ApiOperationLog的方法，都会执行环绕中的代码**/
      // @Pointcut("@annotation(com.zx.framework.biz.operationlog.aspect.ApiOperationLog)")
       @Pointcut("@annotation(com.zx.framework.biz.operationlog.aspect.ApiOperationLog)")
       public void apiOperationLog(){}

    /**
     * 环绕
     * @param joinPoint
     * @return
     * @throws Throwable
     */
       @Around("apiOperationLog()")
       public Object doAround(ProceedingJoinPoint joinPoint)throws Throwable{
           //请求开始时间 以毫秒为单位返回当前时间。返回值的时间单位是毫秒
           long startTime=System.currentTimeMillis();

           //获取被请求的类和方法
           String className=joinPoint.getTarget().getClass().getSimpleName();
           String methodName=joinPoint.getSignature().getName();

           //请求入参
           Object[] args= joinPoint.getArgs();
           //入参转json字符串
           String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(","));

           //功能描述信息
           String description =getApiOperationLogDescription(joinPoint);

           //打印相关参数
           log.info("===========请求开始:[{}]，入参:{},请求类:{},请求方法:{}==================",
                   description,argsJsonStr,className,methodName);

           //执行切点方法
           Object result=joinPoint.proceed();

          //执行耗时
           long executionTime=System.currentTimeMillis()-startTime;

           //打印出参等相关信息
           log.info("===========请求结束:[{}],耗时:{}ms,出参:{}==============================",
                   description,executionTime,JsonUtils.toJsonString(result));

           return result;
       }

    /**
     * 获取注解藐视信息
      * @param joinPoint
     * @return
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
      //1.从ProceedingJoinPoint获取 MethodSignature
        MethodSignature signature=(MethodSignature) joinPoint.getSignature();

      //2.使用MethodSignature 获取当前被注解的method
      Method method= signature.getMethod();

      //3.从method中提取LogExection注解(获得为空)
       ApiOperationLog apiOperationLog=method.getAnnotation(ApiOperationLog.class);

      //4.从LogExecution注解中获取description属性
      return apiOperationLog.description();

    }

    /**
     * 转json字符串
      * @return
     */
    private Function<Object,String> toJsonStr() {
        return JsonUtils::toJsonString;
    }

}



