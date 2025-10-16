package com.example.userinfo.controller;

import com.example.userinfo.model.User;
import com.example.userinfo.service.RoleService;
import com.example.userinfo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Set;


@Controller
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping
    public String saveUser(@ModelAttribute User user,
                           @RequestParam(value = "selectedRoles", required = false) Set<Long> roleIds) {
        userService.saveUser(user, roleIds);
        return "redirect:/v1/admin";
    }

    @GetMapping("/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User user,
                             @RequestParam(value = "selectedRoles", required = false) Set<Long> roleIds) {
        userService.updateUser(id, user, roleIds);
        return "redirect:/v1/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/v1/admin";
    }
}