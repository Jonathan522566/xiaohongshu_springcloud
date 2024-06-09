package com.zx.xiaohongshu.auth;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class DruidTests {
    /**
     * Druid密码加密
     */
    @Test
    @SneakyThrows
    void testEncodePassword(){
        //你的密码
        String password="123456";
        String[] arr= ConfigTools.genKeyPair(512); //设置长度为512位

         //私钥加密，公钥解密
        //私钥
        log.info("privatekey:{}",arr[0]);
        //公钥
        log.info("publicKey:{}",arr[1]);

        //通过私钥加密密码
        String encodePassword  = ConfigTools.encrypt(arr[0],password);
        log.info("password:{}",encodePassword);

//        String Password  = ConfigTools.decrypt(arr[1],encodePassword);
//        log.info("password:{}",Password);

    }


}
