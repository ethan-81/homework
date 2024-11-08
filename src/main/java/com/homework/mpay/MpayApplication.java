package com.homework.mpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MpayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpayApplication.class, args);
    }
}
