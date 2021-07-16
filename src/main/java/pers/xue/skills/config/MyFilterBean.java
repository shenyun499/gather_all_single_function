package pers.xue.skills.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @auther huangzhixue
 * @data 2021/7/13 2:48 下午
 * @Description
 */
@Component
//@WebFilter(filterName="McyFilterBean",urlPatterns="/*")
public class MyFilterBean implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
