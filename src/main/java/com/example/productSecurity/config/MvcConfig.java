package com.example.productSecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("login");
        registry.addViewController("cards").setViewName("cards");
        registry.addViewController("addQR").setViewName("add");
        registry.addViewController("viewQR").setViewName("qrcode");
        registry.addViewController("modify").setViewName("modify");
    }
}
