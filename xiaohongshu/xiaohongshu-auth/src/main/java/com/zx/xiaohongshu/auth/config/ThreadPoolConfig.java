package com.zx.xiaohongshu.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(10);
        //最大线程数
        executor.setMaxPoolSize(50);
        //队列容量
        executor.setQueueCapacity(200);
        //线程活跃时间(秒)
        executor.setKeepAliveSeconds(30);
        //线程名前缀
        executor.setThreadNamePrefix("AuthExecutor-");
        //拒绝策列:由调用线程处理
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //等待索引任务结束后关闭线程
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置等待时间,如果超过这个时间还没有销毁强制销毁
        executor.setAwaitTerminationSeconds(60);

        executor.initialize();
        return executor;
    }

}
