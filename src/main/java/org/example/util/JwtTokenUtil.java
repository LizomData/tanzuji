package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;

@Slf4j
public class JwtTokenUtil {
    private static final String signKey = "hgy";      //密钥
    private static final Long expire = 86400000L;     //过期时间为十二个小时

    //创建私有构造函数防止被实例化
    private JwtTokenUtil(){};

    //在用户成功登录之后创建一个JWT Token
    /**
     * 生成JWT Token。
     * @return 生成的JWT Token字符串。
     */
    public static String generateJwt(Map<String,Object> claims){
        String jwt = Jwts.builder()
                .setClaims(claims)  //自定义内容（载荷）
                .signWith(SignatureAlgorithm.HS256,signKey.getBytes())   //签名算法
                .setExpiration(new Date(System.currentTimeMillis() + expire))    //有效期
                .compact();
        log.info("生成的JWT令牌：{}",jwt);
        return jwt;
    }

    public static Claims parseJwt(String jwt){
        log.info(jwt);
        Claims claims = Jwts.parser()
                .setSigningKey(signKey.getBytes())   //指定签名密钥
                .parseClaimsJws(jwt)     //解析令牌
                .getBody();
        log.info("解析的结果：\n{}",claims);
        return claims;
    }
}
