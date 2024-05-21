package org.example.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.UserMapper;
import org.example.pojo.Token;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.CheckLogin;
import org.example.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import  org.example.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import java.util.Date;
import java.util.LinkedHashMap;
import io.jsonwebtoken.ExpiredJwtException;

@Slf4j
@RestController
public class CheckLoginController {
    @Resource
    UserService userService;    //注入userService

    @PostMapping("/checklogin")
    public Result checkLogin(@RequestBody Token token) {
        try {
            // 解析JWT令牌，如果令牌无效或过期，parseJwt会抛出异常
            log.info("开始检查用户是否登录");
            Claims claims = JwtTokenUtil.parseJwt(token.getToken());
            log.info("用户已登录");

            // 由于parseJwt已经成功，我们可以安全地从claims中获取信息
            String email = (String) claims.get("email");
            User dbUser = userService.selectByEmail(email);
            log.info("成功获取用户信息：{}", dbUser);
            if(!dbUser.getToken().equals(token.getToken()))
                return Result.error("您已在其他地方登录，请重新登录！");

            // 构建返回数据，假设Result.success是一个合适的方法来返回成功结果
            return Result.success(dbUser);
        } catch (ExpiredJwtException e) {
            // 处理JWT令牌过期的情况
            log.error("JWT令牌已过期", e);
            return Result.error("JWT令牌已过期");
        } catch (Exception e) {
            // 处理其他可能发生的异常
            log.error("处理JWT令牌时发生错误: {}", e.toString());
            return Result.error("内部服务器错误");
        }
    }
}
