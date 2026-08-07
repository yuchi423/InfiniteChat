package com.yuchi.realtimeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.yuchi.realtimeservice.client") //负责扫秒并生成代理对象
public class RealTimeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealTimeServiceApplication.class, args);
    }

}
