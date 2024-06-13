package com.zx.xiaohongshu.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.zx.framework.common.exception.BizException;
import com.zx.framework.common.response.Response;
import com.zx.xiaohongshu.auth.constant.RedisKeyConstants;
import com.zx.xiaohongshu.auth.enums.ResponseCodeEnum;
import com.zx.xiaohongshu.auth.model.vo.verificationcode.SendVerificationCodeReqVO;
import com.zx.xiaohongshu.auth.service.VerificationCodeService;
import com.zx.xiaohongshu.auth.sms.AliyunSmsHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private AliyunSmsHelper aliyunSmsHelper;

    /**
     * 发送短信验证码
     * @param sendVerificationCodeReqVO
     * (该类为实体类)
     * @return
     */
    @Override
    public Response<?> send(SendVerificationCodeReqVO sendVerificationCodeReqVO) {
       //手机号
        String phone= sendVerificationCodeReqVO.getPhone();

        //构建验证码redis key(用手机号当key，判断该手机号有无发过验证码)
        String key= RedisKeyConstants.buildVerificationCodeKey(phone);

        //判断是否已发送验证码
        boolean isSent=redisTemplate.hasKey(key);
        if(isSent){
            //若之前发送过验证码未过期，则提示发送频繁(service层的异常用BizException)
            throw new BizException(ResponseCodeEnum.VERIFICATION_CODE_SEND_FREQUENTLY);
        }

        //生成6位随机数字验证码
        String verificationCode= RandomUtil.randomNumbers(6);

        log.info("==>手机号:{},已发送验证码:【{}】",phone,verificationCode);

        //调用第三方服务器发送
        threadPoolTaskExecutor.submit(()->{
            String signName="阿里云短信测试";
            String templateCode="SMS_154950909";
            String templateParam=String.format("{\"code\":\"%s\"}",verificationCode);
            aliyunSmsHelper.sendMessage(signName,templateCode,phone,templateParam);
        });

        //存储验证码到redis，并设置过期时间为3分钟
        redisTemplate.opsForValue().set(key,verificationCode, 3,TimeUnit.MINUTES);

        return Response.success();
    }


}
