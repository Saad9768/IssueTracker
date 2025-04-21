package com.egroup.issuetracker.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.egroup.issuetracker.interceptor.LoggingInterceptor;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final LoggingInterceptor loggingInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");
	}
}
