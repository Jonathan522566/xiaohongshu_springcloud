package com.zx.xiaohongshu.auth.controller;


import com.zx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zx.framework.common.response.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {
    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口1")
    public Response<String> test(){
        return Response.success("Hello, wzx!");
    }

    @PostMapping("/test2")
    @ApiOperationLog(description = "测试接口2")  //打印到控制台的日志
    public Response<User> test2(@RequestBody @Validated User user){
        int i=1/0;
        return Response.success(user);
    }
}
