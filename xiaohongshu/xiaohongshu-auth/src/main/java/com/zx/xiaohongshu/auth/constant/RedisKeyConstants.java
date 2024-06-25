package com.zx.xiaohongshu.auth.constant;

public class RedisKeyConstants {
    /**
     * 验证码key的前缀
     * static 提供了一种不需要实例化对象即可访问的方式
     */
    private static final String VERIFICATION_CODE_KEY_PREFIX="verification_code:";

    /**
     * 全局ID生成器KEY
     */
    public static final String XIAOHONGSHU_ID_GENERATOR_KEY="xiaohongshu_id_generator";

    /**
     * 用户角色数据key前缀
     */
    private static final String USER_ROLES_KEY_PREFIX="user:roles:";

    /**
     * 角色对应的权限集合KEY前缀
     */
    private static final String ROLE_PERMISSIONS_KEY_PREFIX="role:permissions:";

    /**
     * 构建验证码key
     * @Param phone
     * @return
     */
    public static String buildVerificationCodeKey(String phone){
        return VERIFICATION_CODE_KEY_PREFIX+phone;
    }

    /**
     * 构建验证码key
     * @Param phone
     * @return
     */
    public static String buildUserRoleKey(String phone){
        return USER_ROLES_KEY_PREFIX+phone;
    }

    /**
     * 构建校色对应的权限集合KEY
     * @param roleId
     * @return
     */
    public static String buildRolePermissionsKey(Long roleId){
        return ROLE_PERMISSIONS_KEY_PREFIX+roleId;
    }
}
