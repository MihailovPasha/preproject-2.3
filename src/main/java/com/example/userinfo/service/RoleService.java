package com.example.userinfo.service;

import com.example.userinfo.model.Role;
import com.example.userinfo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> findByName(String role) {
        return roleRepository.findByName(role);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getRolesByIds(Set<Long> Ids) {
        return roleRepository.findAllById(Ids);
    }
}