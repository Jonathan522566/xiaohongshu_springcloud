package com.zx.xiaohongshu.auth.sms;



import com.aliyun.dysmsapi20170525.Client;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//用于初始化一个短信发送客户端，注入到 Spring 容器中
@Configuration
@Slf4j
public class AliyunSmsClientConfig {
    @Resource
    private AliyunAccessKeyProperties aliyunAccessKeyProperties;

    @Bean
    public Client smsClient(){
        try{
            Config config=new Config()
                    .setAccessKeyId(aliyunAccessKeyProperties.getAccessKeyId())
                    .setAccessKeySecret(aliyunAccessKeyProperties.getAccessKeySecret());
               config.endpoint="dysmsapi.aliyuncs.com";
               return new Client(config); //配置到client中
        }catch (Exception e){
               log.error("初始化阿里云短信发送客户端错误:",e);
               return null;
        }
    }
}
