package com.java.example;

import com.example.shared.SharedModuleExample;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class JavaExampleApp {

    @PostConstruct
    public void postConstructor() {
        SharedModuleExample.INSTANCE.test("java-example", log);
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaExampleApp.class, args);
    }
}
