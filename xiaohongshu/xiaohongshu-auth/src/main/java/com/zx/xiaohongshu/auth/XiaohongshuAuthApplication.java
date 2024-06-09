package com.zx.xiaohongshu.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.zx.xiaohongshu.auth.domain.mapper")
public class XiaohongshuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaohongshuAuthApplication.class, args);
    }

}
