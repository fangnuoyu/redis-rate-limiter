package com.fny.limiter.aspect;

import com.fny.limiter.annotation.RateLimiter;
import com.fny.limiter.enums.LimitType;
import com.fny.limiter.exception.RateLimiterException;
import com.fny.limiter.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Collections;

/**
 * @Name RateLimiterAspect
 * @Description RateLimiterAspect
 * @Author HRT
 * @Date 2024/5/17 15:24
 * @Version 1.0.0
 **/
@Aspect
@Component
@Slf4j
public class RateLimiterAspect {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisScript<Long> redisScript;

    @Before("@annotation(rateLimiter)")
    public void before(JoinPoint jp, RateLimiter rateLimiter) {
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        String rateLimiterKey = buildRateLimiterKey(jp, rateLimiter);
        try {
            Long rlt = redisTemplate.execute(redisScript, Collections.singletonList(rateLimiterKey), time, count);
            if(rlt == null || rlt.intValue() > count) {
                // 超过限流阈值
                log.info("当前接口已达到限流次数");
                throw new RateLimiterException("访问过于频繁,请稍后访问");
            }
            log.info("一个时间窗内请求次数: {}, 当前请求次数: {}, 缓存的key为: {}", count, rlt, rateLimiterKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String buildRateLimiterKey(JoinPoint jp, RateLimiter rateLimiter) {
        StringBuffer key = new StringBuffer(rateLimiter.key());
        if(rateLimiter.limitType().equals(LimitType.IP)) {
            key.append(IpUtils.getIpAddr(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()))
                    .append("-");
        }
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        key.append(method.getDeclaringClass().getName())
                .append("-");
        return key.toString();
    }


}
