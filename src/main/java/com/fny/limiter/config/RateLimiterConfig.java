package com.fny.limiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @Name RateLimiterConfig
 * @Description RateLimiterConfig
 * @Author HRT
 * @Date 2024/5/17 15:11
 * @Version 1.0.0
 **/
@Configuration
public class RateLimiterConfig {

    @Bean
    DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        return script;
    }
}
