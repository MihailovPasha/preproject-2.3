package com.example.userinfo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/v1/")
    public String home() {
        return "index";
    }
}
