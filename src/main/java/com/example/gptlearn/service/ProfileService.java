package com.example.gptlearn.service;

import com.example.gptlearn.entity.TaskCompletion;
import com.example.gptlearn.entity.User;
import com.example.gptlearn.mapper.TaskCompletionMapper;
import com.example.gptlearn.mapper.UserMapper;
import com.example.gptlearn.model.dto.PageDto;
import com.example.gptlearn.model.dto.UserDto;
import com.example.gptlearn.model.response.TaskCompletionsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserService userService;
    private final TaskCompletionService taskCompletionService;

    @Transactional
    public UserDto getMe() {
        User user = userService.getCurrentUser();
        int solvedTasks = taskCompletionService.totalTaskCompletionResultTrue(user.getId());
        int totalTasks = taskCompletionService.totalTaskCompletion(user.getId());
        return UserMapper.INSTANCE.userToDto(user, totalTasks, solvedTasks);
    }

    @Transactional
    public TaskCompletionsResponse getTaskCompletions(int pageNumber, int pageSize) {
        Page<TaskCompletion> page = taskCompletionService.getTaskCompletions(pageNumber, pageSize);
        return TaskCompletionsResponse.builder()
                .tasks(TaskCompletionMapper.INSTANCE.taskCompletionListToListDto(page.toList()))
                .page(new PageDto(
                        page.getTotalPages(),
                        page.getTotalElements(),
                        page.getNumber(),
                        page.isEmpty(),
                        page.isFirst(),
                        page.isLast()
                ))
                .build();
    }
}
