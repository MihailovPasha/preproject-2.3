package com.example.userinfo.mapper;

import com.example.userinfo.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    void updateUser(User source, @MappingTarget User target);
}
