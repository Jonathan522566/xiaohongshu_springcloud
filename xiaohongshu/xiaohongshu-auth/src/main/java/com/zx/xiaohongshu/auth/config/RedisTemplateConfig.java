package com.zx.xiaohongshu.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisTemplateConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<>();
        //设置RedisTemplate的连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);

        //使用StringRedisSerializer来序列化redis的kry值;确保key时可读的字符窜
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        //使用Jackson2jJsonRedisSerializer来序列化和反序列化redis的value值，
        //确保存储的时json格式
        Jackson2JsonRedisSerializer<Object> serializer=new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}





























