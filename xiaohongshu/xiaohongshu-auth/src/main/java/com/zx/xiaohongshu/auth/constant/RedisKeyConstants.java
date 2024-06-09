package com.zx.xiaohongshu.auth.constant;

public class RedisKeyConstants {
    /**
     * 验证码key的前缀
     * static 提供了一种不需要实例化对象即可访问的方式
     */
    private static final String VERIFICATION_CODE_KEY_PREFIX="verification_code:";

    /**
     * 构建验证码key
     * @Param phone
     * @return
     */
    public static String buildVerificationCodeKey(String phone){
        return VERIFICATION_CODE_KEY_PREFIX+phone;
    }


}
