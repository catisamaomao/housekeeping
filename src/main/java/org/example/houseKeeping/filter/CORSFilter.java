package org.example.houseKeeping.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/**")
@Order(1)
@Configuration
@Component
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // 允许所有域名的请求
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的请求方法
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        // 允许的请求头
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        // 是否支持凭据
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        // 预检请求缓存时间
        httpResponse.setHeader("Access-Control-Max-Age", "3600");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
