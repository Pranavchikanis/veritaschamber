package com.veritaschambers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VeritasChambersApplication {

    public static void main(String[] args) {
        SpringApplication.run(VeritasChambersApplication.class, args);
    }
}
