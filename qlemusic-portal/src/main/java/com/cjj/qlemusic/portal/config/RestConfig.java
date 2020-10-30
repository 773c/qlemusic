package com.cjj.qlemusic.portal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

//@Component
//public class RestConfig {
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Bean
//    public RestTemplate getRestTemplate(){
//        RestTemplate restTemplate = new RestTemplate();        //将默认的编码格式指定成utf-8
//        StringHttpMessageConverter converter = new StringHttpMessageConverter();
//        converter.setDefaultCharset(Charset.forName("utf-8"));//处理乱码
//        restTemplate.getMessageConverters().set(1,converter);
//        return restTemplate;
//    }
//}
