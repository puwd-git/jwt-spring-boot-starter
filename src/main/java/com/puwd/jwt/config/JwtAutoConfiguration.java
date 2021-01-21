package com.puwd.jwt.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @auther puwd
 * @Date 2021-1-20 15:08
 * @Description
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true")
public class JwtAutoConfiguration {
}
