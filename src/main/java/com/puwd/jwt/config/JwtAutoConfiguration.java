package com.puwd.jwt.config;

import com.puwd.jwt.interceptor.JwtInterceptor;
import com.puwd.jwt.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @auther puwd
 * @Date 2021-1-20 15:08
 * @Description
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true")
@AutoConfigureBefore(WebConfig.class)
public class JwtAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(JwtAutoConfiguration.class);

    @Autowired
    private JwtProperties jwtProperties;

    @PostConstruct
    public void init() {
        logger.info("jwt starter: jwt init.");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }
}
