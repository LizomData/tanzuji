package org.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;    //用户名
    private String password;    //密码
    private Integer gender;     //性别
    private String email;   //邮箱
    private LocalDate birth; //生日
    private String imgurl;  //头像
    private String token;   //token
}