package org.example.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.protocol.HTTP;
import org.example.config.RC4Util;
import org.springframework.web.bind.annotation.*;
import org.example.pojo.User1;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

@RestController
public class mytest {


    @GetMapping("/hello")
    public String hello(User1 user)
    {
        System.out.println(user.toString());
        return "1111d5";
    }

    @PostMapping("/upload")
    public String up(@RequestHeader String token , MultipartFile photo, HttpServletRequest request)throws IOException
    {
        String encryStr = RC4Util.encryRC4String("hgy|442424");
       System.out.println("加密后得到得字符串："+encryStr);
        String decryStr = RC4Util.decryRC4(token);
        System.out.println("解密后得到得字符串："+decryStr);
        String[] parts = decryStr.split("\\|");
        if(parts.length!=3)
        {
            System.out.println("token错误: length="+parts.length);
        }
        if(!("|"+parts[2]).equals(RC4Util.addS))
        {
            System.out.println("token错误: parts[2]="+parts[2]);
        }


        if(photo==null)
            return token+"上传失败:图片资源为空";

        System.out.println(photo.getOriginalFilename());
        System.out.println(photo.getContentType());


        String path=request.getServletContext().getRealPath("/upload/");
        System.out.println(path);
        saveFile(photo,path);
        return token+"上传成功";
    }

    public void saveFile(MultipartFile photo,String path)throws IOException
    {
        File dir=new File(path);
        if(!dir.exists())
        {
            dir.mkdir();
        }
        File file=new File(path+photo.getOriginalFilename());
        photo.transferTo(file);
    }
}

