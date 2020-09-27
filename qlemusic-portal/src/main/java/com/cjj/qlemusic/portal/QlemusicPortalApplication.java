package com.cjj.qlemusic.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.cjj.qlemusic")
public class QlemusicPortalApplication extends SpringBootServletInitializer{
    public static void main(String[] args) {
        SpringApplication.run(QlemusicPortalApplication.class,args);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(QlemusicPortalApplication.class);
//    }
}
