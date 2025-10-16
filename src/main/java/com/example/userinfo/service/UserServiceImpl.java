package com.example.userinfo.service;

import com.example.userinfo.exception.UserNotFoundException;
import com.example.userinfo.mapper.UserMapper;
import com.example.userinfo.model.Role;
import com.example.userinfo.model.User;
import com.example.userinfo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User saveUser(User user, Set<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> roles = new HashSet<>(roleService.getRolesByIds(roleIds));
            user.setRoles(roles);
        } else {
            Role userRole = roleService.findByName("ROLE_USER")
                    .orElseGet(() -> roleService.save(new Role("ROLE_USER")));
            user.setRoles(Set.of(userRole));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails, Set<Long> roleIds) {
        User existingUser = getUserById(id);
        userMapper.updateUser(userDetails, existingUser);

        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> roles = new HashSet<>(roleService.getRolesByIds(roleIds));
            existingUser.setRoles(roles);
        }

        if (userDetails.getPassword() != null && !userDetails.getPassword().trim().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @PostConstruct
    private void initializeTestUsers() {
        if (userRepository.count() == 0) {
            var userRole = roleService.findByName("ROLE_USER")
                    .orElseGet(() -> roleService.save(new Role("ROLE_USER")));
            var adminRole = roleService.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleService.save(new Role("ROLE_ADMIN")));

            User admin = new User("admin@mail.com", passwordEncoder.encode("admin"),
                    "Admin", 30);
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);

            User user = new User("user@mail.com", passwordEncoder.encode("user"),
                    "User", 25);
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
        }
    }
}
