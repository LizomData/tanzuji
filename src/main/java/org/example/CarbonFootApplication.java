package org.example;

import org.example.config.AES;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarbonFootApplication {

    public static void main(String[] args) {
        AES. aes_exm();
        System.out.println(org.springframework.core.SpringVersion.getVersion());
        SpringApplication.run(CarbonFootApplication.class, args);
    }

}
