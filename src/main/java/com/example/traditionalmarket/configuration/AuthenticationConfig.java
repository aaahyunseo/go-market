package com.example.traditionalmarket.configuration;

import com.example.traditionalmarket.authentication.AuthenticatedUserArgumentResolver;
import com.example.traditionalmarket.authentication.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/users/**",
                        "/market-books/**",
                        "/visited-markets",
                        "/reactions/**",
                        "/boards", "/boards/{boardId}",
                        "/comments/{boardId}", "/comments/{commentsId}")
                .excludePathPatterns("/api/**","/markets", "/surveys", "/chats", "/boards/all", "comments/get/{boardId}");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticatedUserArgumentResolver);
    }
}
