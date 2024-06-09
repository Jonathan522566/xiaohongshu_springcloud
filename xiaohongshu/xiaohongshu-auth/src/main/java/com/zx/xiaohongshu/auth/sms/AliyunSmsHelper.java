package com.zx.xiaohongshu.auth.sms;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import com.zx.framework.common.util.JsonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//创建一个 AliyunSmsHelper 短信发送工具类
@Component
@Slf4j
public class AliyunSmsHelper {
    @Resource
    private Client client;

    /**
     * 发送短信
     * @param signName
     * @param templateCode
     * @param phone
     * @param templateParm
     * @return
     */
    public boolean sendMessage(String signName,String templateCode,String phone,String templateParm){
        SendSmsRequest sendSmsRequest=new SendSmsRequest()
                .setSignName(signName)
                .setTemplateCode(templateCode)
                .setPhoneNumbers(phone)
                .setTemplateParam(templateParm);
        RuntimeOptions runtime=new RuntimeOptions();

        try{
            log.info("==> 开始短信发送 ,phone:{},signName:{},templateCode:{},templateParam:{}",
                                        phone,signName,templateCode,templateParm);
            //发送短信
            //client已经提前配置好key
            //然后client的方法封装信息
            SendSmsResponse response=client.sendSmsWithOptions(sendSmsRequest,runtime);
            log.info("==> 短信发送成功,response:{}", JsonUtils.toJsonString(response));
            return  true;
        }catch (Exception error){
            log.error("==>短信发送错误：",error);
            return false;
        }
    }

}
