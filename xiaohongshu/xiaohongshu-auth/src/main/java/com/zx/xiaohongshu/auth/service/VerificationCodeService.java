package com.zx.xiaohongshu.auth.service;

import com.zx.framework.common.response.Response;
import com.zx.xiaohongshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;

public interface VerificationCodeService {
    /**
     * 发送短信验证码
     *
     * @param sendVerificationCodeReqVO
     * @return
     */
    Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO);

}
