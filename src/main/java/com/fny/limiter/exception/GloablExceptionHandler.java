package com.fny.limiter.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name GloablExceptionHandler
 * @Description 全局异常处理器
 * @Author HRT
 * @Date 2024/5/17 15:16
 * @Version 1.0.0
 **/
@RestControllerAdvice
public class GloablExceptionHandler {

    @ExceptionHandler(RateLimiterException.class)
    public Map<String, Object> handlerRateLimitException(RateLimiterException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", 500);
        map.put("message", e.getMessage());
        return map;
    }
}
