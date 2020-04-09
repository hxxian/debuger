package com.lightcone.debuger.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;

/**
 * Created by huang_xiao_xian
 * Date : 2019/3/26.
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean encodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("encodingFilter");
        registrationBean.setFilter(encodingFilter);
        registrationBean.setOrder(0);
        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        return registrationBean;
    }

}
