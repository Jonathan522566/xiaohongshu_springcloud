package com.zx.xiaohongshu.auth.service;

import com.zx.framework.common.response.Response;
import com.zx.xiaohongshu.auth.model.vo.user.UserLoginReqVO;

public interface UserService {

    /**
     * 登录与注册
     * @param userLoginReqVO
     * @return
     */
    Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO);

}





















