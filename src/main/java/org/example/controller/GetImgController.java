package org.example.controller;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.service.UserService;
import org.example.util.JwtTokenUtil;
import org.example.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GetImgController {

    @Resource
    UserService userService;

    @PostMapping("/findImg")
    public Result getImg(@RequestBody String token) {
        log.info("开始获取头像url......");
        Claims claims = JwtTokenUtil.parseJwt(token);
        //获取url
        System.out.println(token);
        String email =(String) claims.get("email");
        String imgurl = userService.findImgUrl(email);
        log.info("成功获取头像url：{}",imgurl);
        return Result.success(imgurl);
    }
}
