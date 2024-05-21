package org.example.controller;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.JwtTokenUtil;
import org.example.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class UpadateUserContrller {
    @Resource
    UserService userService;

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user){
        String jsonStr = JSON.toJSONString(user);
        log.info("用户数据修改请求: {}", jsonStr);

        //获得token中的email
        Claims nowClaims = JwtTokenUtil.parseJwt(user.getToken());
        String email =(String) nowClaims.get("email");
        String imgurl= null;
//        //判断是否包含头像
//        if (!img.isEmpty()){
//            userService.insertImg(email,img);
//            imgurl = userService.findImgUrl(email);
//            log.info("用户需要修改头像！");
//        }
        userService.updateUser(email,user.getUsername(),user.getPassword(),user.getGender(),user.getBirth());

        User tempUser = userService.selectByEmail(email);
        log.info("用户数据更新成功：{}",tempUser);

        return Result.success(tempUser);
    }
}
