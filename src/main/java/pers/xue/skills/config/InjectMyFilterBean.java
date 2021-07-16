package pers.xue.skills.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther huangzhixue
 * @data 2021/7/13 3:04 下午
 * @Description
 */
@Configuration
public class InjectMyFilterBean {
    @Autowired
    private MyFilterBean myFilterBean;
    private Logger logger = LoggerFactory.getLogger(InjectMyFilterBean.class);

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(myFilterBean);
        registration.addUrlPatterns("/test/*");
        registration.setName("myFilter");
        registration.setOrder(1);
        return registration;
    }
}
