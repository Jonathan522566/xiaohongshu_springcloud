package com.zx.xiaohongshu.auth.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.zx.framework.common.enums.DeletedEnum;
import com.zx.framework.common.enums.StatusEnum;
import com.zx.framework.common.exception.BizException;
import com.zx.framework.common.response.Response;
import com.zx.framework.common.util.JsonUtils;
import com.zx.xiaohongshu.auth.constant.RedisKeyConstants;
import com.zx.xiaohongshu.auth.constant.RoleConstants;
import com.zx.xiaohongshu.auth.domain.dataobject.UserDO;
import com.zx.xiaohongshu.auth.domain.dataobject.UserRoleDO;
import com.zx.xiaohongshu.auth.domain.mapper.UserDOMapper;
import com.zx.xiaohongshu.auth.domain.mapper.UserRoleDOMapper;
import com.zx.xiaohongshu.auth.enums.LoginTypeEnum;
import com.zx.xiaohongshu.auth.enums.ResponseCodeEnum;
import com.zx.xiaohongshu.auth.model.vo.user.UserLoginReqVO;
import com.zx.xiaohongshu.auth.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserDOMapper userDOMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

     @Resource
     private UserRoleDOMapper userRoleDOMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    /**
     * 登录与注册
     *
     * @param userLoginReqVO
     * @return
     */
    @Override
    public Response<String> loginAndRegister(UserLoginReqVO userLoginReqVO) {
        String phone=userLoginReqVO.getPhone();
        Integer type=userLoginReqVO.getType(); //通过type来判读去redis验证or数据库眼睁睁
                                                 //type是枚举形的值，通过值返回类型名称

        LoginTypeEnum loginTypeEnum=LoginTypeEnum.valueOf(type);

        Long userId=null;  //用于判断用户是否注册了账号

        //判断登录类型
        switch (loginTypeEnum){
            case VERIFICATION_CODE:  //验证码登录
                String verificationCode = userLoginReqVO.getCode();

                //校验认证码是否为空->由于有 password or code
                //因此只能在里面进行判断，而不是@NotBlank
//                if(StringUtils.isBlank(verificationCode)){
//                    return Response.fail(ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode(),"验证码不能空");
//                }
                Preconditions.checkArgument(StringUtils.isNotBlank(verificationCode),"验证码不能为空");

                //构建验证码 redis key
                String key= RedisKeyConstants.buildVerificationCodeKey(phone);
                //查询存储在redis中的用户的验证码
                String sentCode=(String) redisTemplate.opsForValue().get(key);

                //判断用户提交的验证码,与redis中的验证码是否一致
                //sentCode是redis中取的 verificationCode是需要验证的验证码
                if(!StringUtils.equals(verificationCode,sentCode)){
                      //在全局异常中已经定义过一个校验异常->直接抛异常
                    throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_ERROR);
                }

                //通过手机号查询记录->判断是否注册过 UserDO实体类
                UserDO userDO=userDOMapper.selectByPhone(phone);

                log.info("==>用户是否注册,phone:{},userDO:{}",phone, JsonUtils.toJsonString(userDO));

                //判断是否注册
                if(Objects.isNull(userDO)){
                    //若此用户还没有注册,系统自动注册该用户
                    userId=registerUser(phone);
                }else {
                    //已注册,则获取器用户的ID
                    userId=userDO.getId();
                }
                break;
            case PASSWORD:  //密码登录
                //todo
                break;
            default:
                break;
        }

        //SaToken 登录用户,入参为用户ID
        StpUtil.login(userId);

        //获取Token令牌
        SaTokenInfo tokenInfo=StpUtil.getTokenInfo();

        //返回Token令牌
        return Response.success(tokenInfo.tokenValue);
    }

    /**
     * 系统自动注册用户
     * @param phone
     * @return
     */
    //@Transactional(rollbackFor = Exception.class)
    public Long registerUser(String phone) {
        return transactionTemplate.execute(status -> {

          try {

            //获取全局自增的小红书id 在redis中获取key为：xiaohongshu_id_generator
            Long xiaohongshuId = redisTemplate.opsForValue()
                    .increment(RedisKeyConstants.XIAOHONGSHU_ID_GENERATOR_KEY);

            UserDO userDO = UserDO.builder()
                    .phone(phone)
                    .xiaohongshuId(String.valueOf(xiaohongshuId))
                    .nickname("小红薯" + xiaohongshuId)
                    .status(StatusEnum.ENABLE.getValue()) //状态为开启
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .isDeleted(DeletedEnum.NO.getValue())
                    .build();

            //1.添加入库-->数据库中注册
            userDOMapper.insert(userDO);

            //获取刚刚添加入库的ID
            Long userId = userDO.getId();

            //2.给该用户分配一个默认角色->分配角色
            UserRoleDO userRoleDO = UserRoleDO.builder()
                    .userId(userId)
                    .roleId(RoleConstants.COMMON_USER_ROLE_ID)
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .isDeleted(DeletedEnum.NO.getValue())
                    .build();
            userRoleDOMapper.insert(userRoleDO);

            //将该用户的校色ID存入redis中
            // key-> user:roles:[phone] value->roles
            List<Long> reles = Lists.newArrayList(); //角色id集合->由于一个用户可能存在多个角色
            reles.add(RoleConstants.COMMON_USER_ROLE_ID);
            String userRolesKey = RedisKeyConstants.buildUserRoleKey(phone);
            redisTemplate.opsForValue().set(userRolesKey, JsonUtils.toJsonString(reles));

            return userId;

          }catch (Exception e){
            status.setRollbackOnly(); // 标记事务为回滚->执行事务回滚
            log.error("==> 系统注册用户异常: ", e);
            return null;
          }

        });
    }

}
