package org.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Token;

import java.util.Date;

@Slf4j
public class CheckLogin {
    public static boolean checkLogin(String token) {

        boolean isTokenValid;

        try {
            // 解析JWT令牌
            Claims claims = JwtTokenUtil.parseJwt(token);
            // 获取JWT令牌的过期时间
            Date expirationDate = claims.getExpiration();
            // 获取当前时间
            Date now = new Date();

            // 检查当前时间是否在JWT令牌的过期时间之前
            if (now.before(expirationDate)) {
                isTokenValid = true;
            } else {
                isTokenValid = false;
            }
        } catch (ExpiredJwtException e) {
            // 处理JWT令牌过期的情况
            log.error("JWT令牌已过期", e);
            // 根据需要设置isTokenValid的值，或者抛出自定义异常
            isTokenValid = false;
            // 或者抛出一个自定义异常，让调用者处理
        } catch (Exception e) {
            // 处理其他可能发生的异常
            log.error("处理JWT令牌时发生错误", e);
            isTokenValid = false;
        }
        return isTokenValid;
    }
}
