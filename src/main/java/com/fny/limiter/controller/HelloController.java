package com.fny.limiter.controller;

import com.fny.limiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name HelloController
 * @Description HelloController
 * @Author HRT
 * @Date 2024/5/17 15:48
 * @Version 1.0.0
 **/
@RestController
public class HelloController {

    @GetMapping("/hello")
    @RateLimiter(time=10, count=3)
    public String hello() {
        return "hello";
    }
}
