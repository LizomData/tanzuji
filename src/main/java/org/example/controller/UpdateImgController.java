package org.example.controller;

import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.example.util.JwtTokenUtil;
import org.example.util.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class UpdateImgController {
    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;

    @PostMapping("/updateImg")
    public Result updateImg(@RequestHeader("token") String token,  MultipartFile img){
        log.info("文件上传开始，Jwt：{}, 文件名：{}", token, img.getOriginalFilename());
        Claims nowClaims = JwtTokenUtil.parseJwt(token);
        String email =(String) nowClaims.get("email");
        boolean isSend = userService.insertImg(email, img);
        String message = isSend ? "图片上传成功！" : "图片上传失败！";
        log.info("文件上传状态：{}",message);

        //获取url
        String imgUrl = userService.findImgUrl(email);
        log.info("用户头像更新成功！");
        userMapper.insertImg(email,imgUrl);

        return Result.success(imgUrl);
    }
}
