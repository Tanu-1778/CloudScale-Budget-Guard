package com.cloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Global Web configuration.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Explicitly map static resources
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Map root to index.html
        registry.addViewController("/").setViewName("forward:/index.html");
        
        // Map common frontend routes explicitly
        registry.addViewController("/dashboard").setViewName("forward:/index.html");
        registry.addViewController("/login").setViewName("forward:/index.html");
        registry.addViewController("/servers").setViewName("forward:/index.html");
        registry.addViewController("/budget").setViewName("forward:/index.html");
        registry.addViewController("/killswitch").setViewName("forward:/index.html");
        registry.addViewController("/schedule").setViewName("forward:/index.html");
        registry.addViewController("/billing").setViewName("forward:/index.html");
        registry.addViewController("/organizations").setViewName("forward:/index.html");
        registry.addViewController("/departments").setViewName("forward:/index.html");
        registry.addViewController("/employees").setViewName("forward:/index.html");
        registry.addViewController("/sa-overview").setViewName("forward:/index.html");
        registry.addViewController("/sa-revenue").setViewName("forward:/index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
