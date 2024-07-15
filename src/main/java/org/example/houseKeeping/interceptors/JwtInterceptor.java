package org.example.houseKeeping.interceptors;

import org.example.houseKeeping.utils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    // 在请求处理之前调用，用于验证 JWT 令牌的有效性
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求路径
        String requestURI = request.getRequestURI();

        // 如果是登录请求，不进行JWT验证，直接放行
        if (requestURI.contains("/login")) {
            return true;
        }
        // 从请求头中获取令牌
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            // 如果令牌为空，则返回未提供令牌的错误消息
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "未提供令牌");
            return false;
        }

        try {
            token = token.replace("Bearer ", "");// 移除令牌前缀
//验证jwt
            JwtUtils.validateJwt(token);
            return true; // 令牌有效，允许请求继续处理
        } catch (Exception e) {
            // 令牌验证失败，返回错误消息
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "令牌验证失败");
            return false;
        }
    }
}
