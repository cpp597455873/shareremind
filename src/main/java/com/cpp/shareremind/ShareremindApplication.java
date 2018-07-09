package com.cpp.shareremind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@EnableSwagger2
public class ShareremindApplication {


    public static void main(String[] args) {
        SpringApplication.run(ShareremindApplication.class, args);
    }
}
