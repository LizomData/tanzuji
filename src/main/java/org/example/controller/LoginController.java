package org.example.controller;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.UserMapper;
import org.example.pojo.Token;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.JwtTokenUtil;
import org.example.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {
    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        String jsonStr = JSON.toJSONString(user);
        log.info("登录请求: {}", jsonStr);
        User userTemp = userService.login(user);

        //登陆成功，下发令牌
        if (userTemp != null){
            Map<String,Object> claims = new HashMap<>();;
            claims.put("email",userTemp.getEmail());
            claims.put("username",userTemp.getUsername());
            String jwt =  JwtTokenUtil.generateJwt(claims);     //jwt包含了当前登录的员工信息

            //更新数据库用户token
            userMapper.updateToken(userTemp.getEmail(),jwt);

            // 使用LinkedHashMap来保持数据的插入顺序
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put("email", userTemp.getEmail());
            data.put("username", userTemp.getUsername());
            String gen = (userTemp.getGender() == 1) ? "男" : "女";
            data.put("gender", gen);
            data.put("birth", userTemp.getBirth());
            data.put("token", jwt);
            data.put("imgurl",userTemp.getImgurl());

            return Result.success(data);
        }
        //登录失败，返回错误信息
        return Result.error("登录失败，用户名或密码错误");
    }
}
