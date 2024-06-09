package com.zx.xiaohongshu.auth;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
public class RedisTests {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * set key value
     */
    @Test
    void testSetKeyValue(){
        //添加一个key为name，value为wzx
        redisTemplate.opsForValue().set("name","wzx");
    }

    /**
    *判断某个key是否存在
     * 当判读true false中还包含null时 ->用Boolean.TRUE.equals
     */
    @Test
    void testHashKey(){
        log.info("key是否存在:{}",Boolean.TRUE.equals(redisTemplate.hasKey("name")));
    }

    /**
     *获取某个key的value
    **/
    @Test
    void testGetValue(){
        log.info("value值:{}",redisTemplate.opsForValue().get("name"));
    }

     /**
     *删除某个key
      */
     @Test
    void testDelete(){
         redisTemplate.delete("name");
     }

}
