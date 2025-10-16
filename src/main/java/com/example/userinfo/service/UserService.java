package com.example.userinfo.service;

import com.example.userinfo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User saveUser(User user, Set<Long> roleIds);

    User updateUser(Long id, User user, Set<Long> roleIds);

    void deleteUser(Long id);

    boolean existsByEmail(String email);
}
