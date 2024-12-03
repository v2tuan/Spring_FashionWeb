package com.fashionweb;

import com.fashionweb.service.IStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootFashionWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFashionWebApplication.class, args);
    }

    // thêm cấu hình storage
    @Bean
    CommandLineRunner init (IStorageService storageService) {
        return (args -> {
            storageService.init();
        });
    }
}
