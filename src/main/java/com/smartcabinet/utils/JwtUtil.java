package com.smartcabinet.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT工具类
 * 
 * @author SmartCabinet Team
 */
@Slf4j
@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;
    
    /**
     * 生成访问令牌
     */
    public String generateToken(String userId) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration * 1000);
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * 生成刷新令牌
     */
    public String generateRefreshToken(String userId) {
        Date expirationDate = new Date(System.currentTimeMillis() + refreshExpiration * 1000);
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    /**
     * 从令牌中获取用户ID
     */
    public String getUserIdFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (Exception e) {
            log.error("获取用户ID失败", e);
            return null;
        }
    }
    
    /**
     * 验证令牌
     */
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT令牌已过期");
        } catch (UnsupportedJwtException e) {
            log.warn("不支持的JWT令牌");
        } catch (MalformedJwtException e) {
            log.warn("JWT令牌格式错误");
        } catch (SecurityException e) {
            log.warn("JWT令牌签名验证失败");
        } catch (IllegalArgumentException e) {
            log.warn("JWT令牌为空");
        }
        return false;
    }
    
    /**
     * 获取令牌过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration();
        } catch (Exception e) {
            log.error("获取令牌过期时间失败", e);
            return null;
        }
    }
    
    /**
     * 判断令牌是否过期
     */
    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }
}
