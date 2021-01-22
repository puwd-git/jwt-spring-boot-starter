package com.puwd.jwt.config;

import com.puwd.jwt.interceptor.JwtInterceptor;
import com.puwd.jwt.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
@ConditionalOnBean({LoginInterceptor.class,JwtInterceptor.class})
@AutoConfigureAfter(JwtAutoConfiguration.class)
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/login");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/authc/**");
    }
}
