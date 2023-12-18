package com.example.gptlearn.mapper;

import com.example.gptlearn.entity.Task;
import com.example.gptlearn.entity.Theme;
import com.example.gptlearn.model.dto.TaskDto;
import com.example.gptlearn.model.dto.enums.Complexity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDto taskToDto(Task task);

    default String complexityToString(Complexity complexity) {
        return complexity.getValue();
    }

    List<TaskDto> taskListToList(List<Task> tasks);

    default String themeToString(Theme theme) {
        return theme.getName();
    }
}
