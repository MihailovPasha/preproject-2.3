package com.example.userinfo.service;

import com.example.userinfo.exception.UserNotFoundException;
import com.example.userinfo.mapper.UserMapper;
import com.example.userinfo.model.User;
import com.example.userinfo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

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
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        userMapper.updateUser(userDetails, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @PostConstruct
    private void initializeTestUsers() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("Andy", 28, "testmail1@mail.ru"));
            userRepository.save(new User("Bob", 27, "testmail2@mail.ru"));
            userRepository.save(new User("Cade", 26, "testmail3@mail.ru"));
        }
    }
}
