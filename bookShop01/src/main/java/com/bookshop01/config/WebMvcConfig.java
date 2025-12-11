package com.bookshop01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesView;

import com.bookshop01.common.interceptor.ViewNameInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        // Tiles definitions packaged under resources/tiles (filenames are tiles_*.xml)
        tilesConfigurer.setDefinitions("classpath*:tiles/tiles_*.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    @Bean
    public TilesViewResolver viewResolver() {
        TilesViewResolver resolver = new TilesViewResolver();
        resolver.setViewClass(TilesView.class);
        // Tiles로 먼저 해석하도록 우선순위를 높게 설정
        resolver.setOrder(0);
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ViewNameInterceptor())
                .addPathPatterns("/*/*.do", "/*/*/*.do");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
