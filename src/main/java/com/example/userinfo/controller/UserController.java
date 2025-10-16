package com.example.userinfo.controller;

import com.example.userinfo.model.SecurityUser;
import com.example.userinfo.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/v1/user")
    public String userPage(@AuthenticationPrincipal SecurityUser securityUser, Model model) {
        User user = securityUser.getUser();
        model.addAttribute("user", user);
        return "user";
    }
}