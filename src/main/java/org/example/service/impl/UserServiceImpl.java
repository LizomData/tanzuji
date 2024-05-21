package org.example.service.impl;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ServiceException;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.util.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    //登录实现逻辑
    @Override
    public User login(User user) {

        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null){
            log.error("用户登录失败：用户名不存在");
            //throw new ServiceException("用户名或密码错误1");
            return dbUser;
        }
        if (!user.getPassword().equals(dbUser.getPassword())){
            log.error("用户登录失败：密码错误");
            //throw new ServiceException("用户名或密码错误2");
            return dbUser;
        }
        log.info("用户登录成功：{}", user.getUsername());
        return dbUser;
    }

    //检查用户是否存在逻辑
    @Override
    public boolean checkEmailExists(String email) {
        User dbEmail = userMapper.findByEmail(email);
        return dbEmail != null;
    }

    @Override
    public boolean checkUserNameExists(String username) {
        User dbUser = userMapper.findByUsername(username);
        return dbUser != null;
    }

    //注册用户逻辑
    @Override
    public Result registerUser(User user) {
        //log.info("开始注册用户：{}", user.getUsername());

        if (checkEmailExists(user.getEmail())) {
            log.error("用户注册失败：邮箱已存在");
            //throw new ServiceException("该邮箱已存在，请使用其他邮箱进行注册");
            return Result.error("该邮箱已存在，请使用其他邮箱进行注册") ;
        }
        if (checkUserNameExists(user.getUsername())) {
            log.error("用户注册失败：用户名已存在");
            //throw new ServiceException("该用户名已存在，请使用其他用户名进行注册");
            return Result.error("该用户名已存在，请使用其他用户名进行注册") ;
        }

        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getEmail())) {
            log.error("用户注册失败：用户名、密码或邮箱不能为空");
           // throw new ServiceException("用户名、密码或邮箱不能为空");

            return Result.error("用户名、密码或邮箱不能为空") ;
        }

        if (StringUtils.isNotEmpty(String.valueOf(user.getBirth()))) {
            LocalDate birth = LocalDate.parse(String.valueOf(user.getBirth()));
            user.setBirth(birth);

        }

        try {
            if (user.getGender() == 1){
                user.setImgurl("http://47.98.177.117/img/userimg_man.png");
            }else {
                user.setImgurl("http://47.98.177.117/img/userimg_default.png");
            }
            userMapper.createUser(user);
            log.info("用户注册成功：{}", user.getUsername());

            return Result.success("用户注册成功") ;
        } catch (Exception e) {
            log.error("用户注册失败：{}", e.getMessage());
            //throw new ServiceException("注册失败，请稍后重试");

            return Result.error("注册失败,未知原因,请稍后重试") ;
        }
    }

    //插入头像逻辑
    @Override
    public boolean insertImg(String email, MultipartFile img) {
        //获取原始文件名
        String originalFileName = img.getOriginalFilename();
        log.info("获取原始文件名：{}",originalFileName);
        //构建新的文件名，防止冲突
        String newFileName = UUID.randomUUID()+originalFileName.substring(originalFileName.lastIndexOf("."));
        log.info("构建新的文件名：{}",newFileName);
        try {
            //将文件保存在C:\wwwroot\47.98.177.117\img\目录下
            img.transferTo(new File("C:/wwwroot/47.98.177.117/img/"+newFileName));
            log.info("成功将文件保存！");
            //并将图片的url存入数据库
            String imgurl = "C:/wwwroot/47.98.177.117/img/" + newFileName;
            return userMapper.insertImg(email,imgurl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //返回图像url
    @Override
    public String findImgUrl(String email){
        log.info(email);
        String url=userMapper.findImgUrl(email);
        log.info("返回头像的服务器url：{}",url);
        return url;
    }

    //checklogin返回用户信息
    @Override
    public User selectByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    //修改用户数据
    @Override
    public User updateUser(String email, String username, String password, int gender, LocalDate birth) {
        userMapper.updateUser(email,username,password,gender,birth);
        return null;
    }
}