package com.atguigu.gulimall.oms.controller;

import com.atguigu.gulimall.oms.feign.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope //可以从注册中心动态获取配置
@RestController
public class HelloController {
    @Autowired
    WorldService worldService;

    @Value("${my.content}")
    private String content = "";
    /**
     * feign声明式调用
     * @return
     */
    @GetMapping("/oms/hello")
    public String Hello(){
        String msg = worldService.world();
        return "hello"+msg+content;
    }
}
