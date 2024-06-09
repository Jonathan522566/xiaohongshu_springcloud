package com.zx.xiaohongshu.auth.enums;

import com.zx.framework.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    //------------通用异常状态码--------
    SYSTEM_ERROR("AUTH-10000","出错啦,后台小哥正在努力修复中"),
    PARAM_NOT_VALID("AUTH-10001","参数错wu"),
    //--------------业务异常状态码---------------
    VERIFICATION_CODE_SEND_FREQUENTLY("AUTH-20000","请求太频繁，请3分钟后再试"),
    ;

    //异常码
    private final String errorCode;
    //错误消息
    private final String errorMessage;
}