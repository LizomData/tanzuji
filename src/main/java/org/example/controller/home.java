package org.example.controller;

import org.springframework.web.bind.annotation.*;
import org.example.pojo.User1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/home")
public class home {
    @GetMapping
    public String showCarbon() {
        return "redirect:www.baidu.com";
    }

}