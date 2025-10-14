package com.example.userinfo.controller;

import com.example.userinfo.model.Role;
import com.example.userinfo.model.User;
import com.example.userinfo.service.RoleService;
import com.example.userinfo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        if (userService.existsByEmail(user.getUsername())) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleService.findByName("ROLE_USER")
                .orElseGet(() -> roleService.save(new Role("ROLE_USER")));
        user.setRoles(Collections.singleton(userRole));

        userService.saveUser(user);
        return "redirect:/login?success";
    }
}