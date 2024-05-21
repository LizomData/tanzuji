package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.User;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO carbonfoot.user (username, password, gender, email,birth,imgurl) " +
            "VALUES (#{username}, #{password}, #{gender}, #{email},#{birth},#{imgurl})")
    void createUser(User user);

    @Select("select * from carbonfoot.user where username = #{username} order by id desc")
    User selectByUsername(String username);



    @Select("select * from carbonfoot.user where email = #{email} order by id desc")
    User selectByEmail(String email);

    //注册搜索邮件
    @Select("select * from carbonfoot.user where email = #{email} order by id desc")
    User findByEmail(String email);

    @Select("select * from carbonfoot.user where username = #{username} order by id desc")
    User findByUsername(String username);

    //存储图像在服务器的url
    @Update("UPDATE carbonfoot.user SET imgurl = #{imgurl} WHERE email = #{email}")
    boolean insertImg(String email,String imgurl);

    //返回图片的url
    @Select("select imgurl from carbonfoot.user where email = #{email} order by id desc")
    String findImgUrl(String email);

    //更新数据库token
    @Update("UPDATE carbonfoot.user SET token = #{token} WHERE email = #{email}")
    void updateToken(String email,String token);

    @Update("UPDATE carbonfoot.user " +
            "SET email = #{email}, " +
            "    username = #{username}, " +
            "    password = #{password}, " +
            "    gender = #{gender}, " +
            "    birth = #{birth} " +
            "WHERE email = #{email}")
    void updateUser(String email, String username, String password, int gender, LocalDate birth);
}