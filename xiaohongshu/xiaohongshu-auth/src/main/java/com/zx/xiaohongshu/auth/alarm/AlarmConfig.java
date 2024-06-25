package com.zx.xiaohongshu.auth.alarm;

import com.zx.xiaohongshu.auth.alarm.impl.MailAlarmHelper;
import com.zx.xiaohongshu.auth.alarm.impl.SmsAlarmHelper;



import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope   //访问Nacos以便拿取value信息
public class AlarmConfig {

   @Value("${alarm.type}")
   private String alarmType;

    @Bean
    @RefreshScope
    public AlarmInterface alarmHelper(){
        //根据配置文件中的警告类型,初始化选择不同的警告实现类
        if(StringUtils.equals("sms",alarmType)){
            return new SmsAlarmHelper();
        } else if (StringUtils.equals("mail",alarmType)) {
            return new MailAlarmHelper();
        }else {
            throw new IllegalArgumentException("错误的警告类型...");
        }

    }

}
