package com.wgc.wgcapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WgcApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WgcApiApplication.class, args);
    }

}
