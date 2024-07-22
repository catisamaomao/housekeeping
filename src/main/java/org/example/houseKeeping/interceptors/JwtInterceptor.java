/*
package org.example.houseKeeping.interceptors;

import org.example.houseKeeping.exception.TokenExpiredException;
import org.example.houseKeeping.exception.TokenNotProvidedException;
import org.example.houseKeeping.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();


        if (requestURI.contains("/login")) {
            return true;
        }

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("Method:OPTIONS");
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new TokenNotProvidedException("未提供令牌");
        }

        try {
            token = token.replace("Bearer ", "");
            JwtUtils.validateJwt(token);
            return true;
        } catch (Exception e) {
            throw new TokenExpiredException("令牌验证失败");
        }
    }
}
*/
