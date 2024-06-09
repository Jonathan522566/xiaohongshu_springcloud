package com.zx.xiaohongshu.auth.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//AliyunAccessKeyProperties 配置类，用于接收配置文件中填写的 AccessKey 信息
@ConfigurationProperties(prefix = "aliyun")
@Component
@Data
public class AliyunAccessKeyProperties {
    private String accessKeyId;
    private String accessKeySecret;
}
