package com.zx.xiaohongshu.auth.controller;


import com.zx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zx.framework.common.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class TestController {
    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口1")
    public Response<String> test(){
        return Response.success("Hello, wzx!");
    }

    @GetMapping("/test2")
    @ApiOperationLog(description = "测试接口2")
    public Response<User> test2(){
            return Response.success(User.builder()
                                        .nickName("wzx")
                                        .createTime(LocalDateTime.now())
                                        .build());
    }
}
