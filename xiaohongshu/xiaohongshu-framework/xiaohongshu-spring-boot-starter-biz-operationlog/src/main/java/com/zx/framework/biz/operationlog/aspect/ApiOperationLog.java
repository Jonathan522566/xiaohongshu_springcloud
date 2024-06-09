package com.zx.framework.biz.operationlog.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ApiOperationLog {
    /**
     * 方法功能描述
     * @return
     */
    String description() default "";
}
