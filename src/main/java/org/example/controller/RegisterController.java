package org.example.controller;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ServiceException;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegisterController {

    @Resource
    UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        String jsonStr = JSON.toJSONString(user);
        log.info("注册请求: {}", jsonStr);
        return userService.registerUser(user);

    }
}
