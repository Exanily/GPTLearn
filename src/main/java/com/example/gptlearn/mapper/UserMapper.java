package com.example.gptlearn.mapper;

import com.example.gptlearn.entity.Role;
import com.example.gptlearn.entity.TaskCompletion;
import com.example.gptlearn.entity.User;
import com.example.gptlearn.model.dto.TaskCompletionDto;
import com.example.gptlearn.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "taskCompletion", source = "taskCompletions", qualifiedByName = "taskCompletionToDto")
    UserDto userToDto(User user);

    default String roleToString(Role role) {
        return role.getName();
    }

    @Named("taskCompletionToDto")
    default TaskCompletionDto taskCompletionToDto(TaskCompletion taskCompletion) {
        return TaskCompletionMapper.INSTANCE.taskCompletionToDto(taskCompletion);
    }
}
