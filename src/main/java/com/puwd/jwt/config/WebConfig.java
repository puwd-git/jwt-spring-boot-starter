package com.puwd.jwt.config;

import com.puwd.jwt.interceptor.JwtInterceptor;
import com.puwd.jwt.interceptor.LoginInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @auther puwd
 * @Date 2021-1-20 16:31
 * @Description
 */
@Configuration
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

    @Bean
    public LoginInterceptor loginInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/login");
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/authc/**");
    }
}
