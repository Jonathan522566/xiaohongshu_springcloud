package com.zx.xiaohongshu.auth.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.zx.xiaohongshu.auth.alarm.AlarmInterface;
import jakarta.annotation.Resource;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    //入门案例的用法
    @NacosValue(value = "${rate-limit.api.limit}",autoRefreshed = true)
    private Integer limit;

    @Resource
    private AlarmInterface alarm;

    @GetMapping("/test")
    public String test(){
      return "当前限流阈值为:"+limit;
    }

    @GetMapping("/alarm")
    public String sendAlarm(){
        alarm.send("系统出错啦!");
        return "alarm success";
    }

}
