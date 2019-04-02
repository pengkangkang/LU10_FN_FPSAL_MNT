package com.fn.fpsal.mnt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fn.fpsal.mnt.filter.TokenInterception;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getTokenInterception()).addPathPatterns("/**");
	}
	
	@Bean
	public TokenInterception getTokenInterception() {
		return new TokenInterception();
	}
}
