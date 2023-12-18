package com.example.gptlearn.mapper;

import com.example.gptlearn.entity.Role;
import com.example.gptlearn.entity.User;
import com.example.gptlearn.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserDto userToDto(User user, int totalTasks, int solvedTasks);

    default String roleToString(Role role) {
        return role.getName();
    }


}
