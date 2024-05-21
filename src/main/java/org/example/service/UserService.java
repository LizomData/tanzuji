package org.example.service;

import org.example.pojo.User;
import org.example.util.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.sql.Struct;
import java.time.LocalDate;

public interface UserService {

    //验证用户账户是否合法
    User login(User user);

    boolean checkUserNameExists(String username);

    //注册用户
    Result registerUser(User user);

    //注册时查找用户输入的邮件地址是否已经存在
    boolean checkEmailExists(String email);

    //登录后上传头像
    boolean insertImg(String email, MultipartFile img);

    String findImgUrl(String email);

    User selectByEmail(String email);

    User updateUser(String email, String username, String password, int gender, LocalDate birth);
}