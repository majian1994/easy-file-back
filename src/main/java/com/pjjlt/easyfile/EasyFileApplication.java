package com.pjjlt.easyfile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pjjlt.easyfile.moudle.mapper")
public class EasyFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyFileApplication.class, args);
    }

}
