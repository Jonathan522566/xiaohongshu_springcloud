package com.zx.xiaohongshu.auth.controller;

import com.zx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zx.framework.common.response.Response;
import com.zx.xiaohongshu.auth.model.vo.user.UserLoginReqVO;
import com.zx.xiaohongshu.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    @ApiOperationLog(description = "用户登录/注册")
    public Response<String> loginAndRegister(@Validated @RequestBody UserLoginReqVO userLoginReqVO){
         return userService.loginAndRegister(userLoginReqVO);
    }

}
