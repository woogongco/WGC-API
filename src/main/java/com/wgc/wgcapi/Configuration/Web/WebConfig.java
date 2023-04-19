package com.wgc.wgcapi.Configuration.Web;
/*
Created on 2023/04/19 10:02 PM In Intelli J IDEA 
by jeon-wangi
*/

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
