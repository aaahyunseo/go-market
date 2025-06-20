package com.example.traditionalmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TraditionalMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TraditionalMarketApplication.class, args);
    }

}
