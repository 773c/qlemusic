package com.cjj.qlemusic.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类
 */
@Configuration
@MapperScan({"com.cjj.qlemusic.admin.dao","com.cjj.qlemusic.security.dao"})
public class MybatisConfig {
}
