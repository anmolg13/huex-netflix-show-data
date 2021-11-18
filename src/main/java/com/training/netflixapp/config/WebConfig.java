package com.training.netflixapp.config;

import com.training.netflixapp.interceptor.AuthTokenInterceptor;
import com.training.netflixapp.interceptor.TimeToExecuteInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthTokenInterceptor authTokenInterceptor;

    @Autowired
    private TimeToExecuteInterceptor timeToExecuteInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authTokenInterceptor);
        registry.addInterceptor(timeToExecuteInterceptor);
    }
}
