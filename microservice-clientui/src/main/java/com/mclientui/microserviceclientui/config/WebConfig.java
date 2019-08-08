package com.mclientui.microserviceclientui.config;

import com.mclientui.microserviceclientui.filters.RestrictionAdminFilterConfig;
import com.mclientui.microserviceclientui.filters.RestrictionFilterConfig;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig {
    //Register RestrictionFilterConfig
    @Bean
    public FilterRegistrationBean<RestrictionFilterConfig> restrictionFilter() {
        FilterRegistrationBean<RestrictionFilterConfig> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new RestrictionFilterConfig());
        filterRegBean.addUrlPatterns("/*");
        filterRegBean.setOrder(Ordered.LOWEST_PRECEDENCE -1);
        return filterRegBean;
    }

    //Register RestrictionAdminFilterConfig
    @Bean
    public FilterRegistrationBean<RestrictionAdminFilterConfig> adminRestrictionFilter() {
        FilterRegistrationBean<RestrictionAdminFilterConfig> filterRegBean = new FilterRegistrationBean<>();
        filterRegBean.setFilter(new RestrictionAdminFilterConfig());
        filterRegBean.addUrlPatterns("/admin/*");
        filterRegBean.setOrder(Ordered.LOWEST_PRECEDENCE -2);
        return filterRegBean;
    }

}
