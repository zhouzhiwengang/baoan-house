package com.digipower.sercurity.util;

import java.util.Date;
import org.springframework.stereotype.Component;
import com.digipower.sercurity.entity.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;

/**
 * jwt 工具类
 * 
 * @author zzg
 *
 */
public class JwtTokenUtil {
	 // 主题
    private static final String SUBJECT = "digipower";

    // jwt的token有效期，
    //private static final long EXPIRITION = 1000L * 60 * 60 * 24 * 7;//7天
    private static final long EXPIRITION = 1000L * 60 * 30;   // 半小时

    // 加密key（黑客没有该值无法篡改token内容）
    private static final String APPSECRET_KEY = "digipower";

    // 用户url权限列表key
    private static final String AUTH_CLAIMS = "auth";

    /**
     * TODO  生成token
     *
     * @param user
     * @return java.lang.String
     * @date 2020/7/6 0006 9:26
     */
    public static String generateToken(JwtUserDetails user) {
        String token = Jwts
                .builder()
                // 主题
                .setSubject(SUBJECT)
                // 添加jwt自定义值
                .claim(AUTH_CLAIMS, user.getAuthorities())
                .claim("username", user.getUsername())
                .claim("userPin", user.getUserPin())
                .claim("sid", user.getSid())
                .setIssuedAt(new Date())
                // 过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                // 加密方式,加密key
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }


    /**
     * 获取用户Id
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("sid").toString();
    }


    /**
     * 获取用户名
     *
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

   

    /**
     * 是否过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        System.out.println("过期时间: " + claims.getExpiration());
        return claims.getExpiration().before(new Date());
    }
}
