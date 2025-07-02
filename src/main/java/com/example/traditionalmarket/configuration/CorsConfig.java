package com.example.traditionalmarket.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    private final long MAX_AGE_SECS = 3600L;

    private final List<String> clientHosts = Arrays.asList(
            "https://market-gogo.netlify.app",
            "https://market-gogo.com",
            "http://localhost:3000",
            "https://api.market-gogo.com"
    );

    private final List<String> allowedMethods = Arrays.asList(
            "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD", "TRACE"
    );

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        clientHosts.forEach(config::addAllowedOriginPattern);

        config.setAllowCredentials(true);                // 쿠키 포함 허용
        config.setAllowedHeaders(List.of("*"));          // 모든 헤더 허용
        config.setAllowedMethods(allowedMethods);        // 필요한 메소드만 허용
        config.setMaxAge(MAX_AGE_SECS);                  // Preflight 캐시 시간

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(new CorsFilter(source));
        filterBean.setOrder(0);

        return filterBean;
    }
}
