package com.ss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.ss.dao")
@SpringBootApplication
public class YingxueApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxueApplication.class, args);
    }

}
