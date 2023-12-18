package com.example.gptlearn.mapper;

import com.example.gptlearn.entity.Task;
import com.example.gptlearn.entity.TaskCompletion;
import com.example.gptlearn.model.dto.TaskCompletionDto;
import com.example.gptlearn.model.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TaskCompletionMapper {
    TaskCompletionMapper INSTANCE = Mappers.getMapper(TaskCompletionMapper.class);

    TaskCompletionDto taskCompletionToDto(TaskCompletion taskCompletion);

    List<TaskCompletionDto> taskCompletionListToListDto(List<TaskCompletion> taskCompletion);
    default TaskDto taskToTaskDto(Task task) {
        return TaskMapper.INSTANCE.taskToDto(task);
    }
}
