package com.fny.limiter.enums;

/**
 * @Name LimitType
 * @Description 限流类型
 * @Author HRT
 * @Date 2024/5/17 12:35
 * @Version 1.0.0
 **/
public enum LimitType {

    /**
     * 默认限流策略, 针对某一个接口限流
     */
    DEFAULT,

    /**
     * 针对某一ip限流
     */
    IP,

    ;
}
