package com.mbs.qlcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class QlccApplication {
    public static void main(String[] args) {
        SpringApplication.run(QlccApplication.class, args);
    }
}
