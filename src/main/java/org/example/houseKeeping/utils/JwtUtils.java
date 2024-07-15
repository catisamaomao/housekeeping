package org.example.houseKeeping.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {
    // 生成JWT令牌的密钥
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    // 生成JWT
    public static String generateJwt(String subject) {
        // 设置JWT的过期时间，这里设置为1小时
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 3600000; // 1 hour
        Date exp = new Date(expMillis);

        // 生成JWT
        String jwt = Jwts.builder()
                .setSubject(subject) // 设置主题，一般是用户的唯一标识
                .setExpiration(exp) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // 使用HS256算法和密钥进行签名
                .compact();

        return jwt;
    }

    // 验证JWT
    public static boolean validateJwt(String jwt) {
        try {
            // 解析JWT
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY) // 设置密钥
                    .parseClaimsJws(jwt) // 解析JWT
                    .getBody();

            // 这里可以根据需要验证JWT中的其他信息，比如主题、过期时间等
            Date expiration = claims.getExpiration(); // 获取过期时间

            if (expiration != null && expiration.before(new Date())) {
                // JWT已经过期
                return false;
                // 在此处添加处理逻辑，例如抛出异常或返回错误信息
            } else {
                // JWT仍然有效
                return true;
                // 在此处添加处理逻辑，例如继续处理请求
            }

        } catch (Exception e) {
            // JWT验证失败
            return false;
        }
    }

    // 解析JWT
    public static String parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY) // 设置密钥
                .parseClaimsJws(jwt) // 解析JWT
                .getBody();

        // 获取JWT中的主题（subject）
        String subject = claims.getSubject();

        return subject;
    }
}
