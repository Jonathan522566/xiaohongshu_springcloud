package com.zx.xiaohongshu.auth.controller;

import com.zx.framework.biz.operationlog.aspect.ApiOperationLog;
import com.zx.framework.common.response.Response;
import com.zx.xiaohongshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.zx.xiaohongshu.auth.service.VerificationCodeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class VerificationCodeController {
    @Resource
    private VerificationCodeService verificationCodeService;

    //Response<?>一般是返回Response.success();
    //无类型返回
    @PostMapping("/verification/code/send")
    @ApiOperationLog(description = "发送短信验证码")
    public Response<?> send(@Validated @RequestBody SendVerificationCodeReqVO sendVerificationCodeReqVO){
        return  verificationCodeService.send(sendVerificationCodeReqVO);
    }
}

























