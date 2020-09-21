package com.github.rshtishi.app.configuration;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfiguration implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/images/**", "/css/**", "/js/**").addResourceLocations(
				"classpath:/static/images/", "classpath:/static/css/",
				"classpath:/static/js/");
	}

}
