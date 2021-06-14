package com.example.productSecurity.controller;

import com.example.productSecurity.pojo.User;
import com.example.productSecurity.service.UserService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Resource
    private UserService userService;


    @RequestMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpServletRequest requset) {
        if (userService.isExist(email)) {
            if (userService.isMatch(email, password)) {
                HttpSession session = requset.getSession();
                session.setAttribute("id", userService.getIdByEmail(email));
                return "redirect:/cards";
            } else {
                return "/login";
            }
        } else {
            return "/login";
        }
    }

    @RequestMapping("registered")
    public String registered(@RequestParam String username, @RequestParam String password,
                             @RequestParam String email, @RequestParam String repassword) {
        if(password.equals(repassword)) {
            userService.registered(new User(username, password, email));
            return "redirect:/";
        } else {
            return "redirect:/signup";
        }
    }


}
