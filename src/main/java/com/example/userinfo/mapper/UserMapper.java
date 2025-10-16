package com.example.userinfo.mapper;

import com.example.userinfo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default void updateUser(User source, @MappingTarget User target) {
        if (source == null) {
            return;
        }

        if (source.getName() != null) {
            target.setName(source.getName());
        }

        if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }

        target.setAge(source.getAge());
    }
}
