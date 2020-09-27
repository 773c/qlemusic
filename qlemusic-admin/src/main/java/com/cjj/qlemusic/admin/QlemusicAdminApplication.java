package com.cjj.qlemusic.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cjj.qlemusic")
public class QlemusicAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(QlemusicAdminApplication.class,args);
    }
}
