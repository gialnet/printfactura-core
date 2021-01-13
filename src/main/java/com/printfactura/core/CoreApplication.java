package com.printfactura.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

import java.io.IOException;

@SpringBootApplication
@EnableRetry
public class CoreApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(CoreApplication.class, args);

    }


}
