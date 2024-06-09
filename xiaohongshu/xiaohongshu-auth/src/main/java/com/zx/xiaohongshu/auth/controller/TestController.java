package com.zx.xiaohongshu.auth.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.zx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zx.framework.common.response.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class TestController {
    @GetMapping("/test")
    @ApiOperationLog(description = "测试接口1")
    public Response<String> test(){
        return Response.success("Hello, wzx!");
    }

    //测试该项目的exception
    @PostMapping("/test2")
    @ApiOperationLog(description = "测试接口2")  //打印到控制台的日志
    public Response<User> test2(@RequestBody @Validated User user){
        //int i=1/0;
        return Response.success(user);
    }

    //测试token
    @RequestMapping("/user/doLogin")
    public String doLogin(String username,String password){
        //此处仅作为模拟实例，真实项目要在数据库中获取进行对比
        if("zhang".equals(username)&&"123456".equals(password)){
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    //查询登录状态
    @RequestMapping("/user/isLogin")
    public String isLogin(){
        return "当前会话是否登录:"+StpUtil.isLogin();
    }


}
























