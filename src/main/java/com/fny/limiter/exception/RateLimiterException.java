package com.fny.limiter.exception;

/**
 * @Name RateLimiterException
 * @Description RateLimiterException
 * @Author HRT
 * @Date 2024/5/17 15:19
 * @Version 1.0.0
 **/
public class RateLimiterException extends Exception{
    public RateLimiterException(String message) {
        super(message);
    }
}
