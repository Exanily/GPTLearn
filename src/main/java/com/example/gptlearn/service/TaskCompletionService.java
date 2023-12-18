package com.example.gptlearn.service;

import com.example.gptlearn.entity.Task;
import com.example.gptlearn.entity.TaskCompletion;
import com.example.gptlearn.entity.User;
import com.example.gptlearn.entity.key.TaskCompletionKey;
import com.example.gptlearn.repository.TaskCompletionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TaskCompletionService {

    private final UserService userService;
    private final TaskService taskService;
    private final TaskCompletionRepository taskCompletionRepository;
    private final TaskManagementService taskManagementService;

    @Transactional
    public Boolean add(Long taskId, String answer) {
        Task task = taskService.getTaskById(taskId);
        User user = userService.getCurrentUser();
        Boolean result = taskManagementService.checkAnswer(task, answer);
        taskCompletionRepository.save(createTaskCompletion(user, task, answer, result));
        return result;
    }

    public Page<TaskCompletion> getTaskCompletions(int pageNumber, int pageSize) {
        Sort sort = Sort.by("completionDate");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return taskCompletionRepository.findByKey_UserId(userService.getCurrentUser().getId(), pageable);
    }

    public int totalTaskCompletion(Long id) {
        return taskCompletionRepository.countTaskCompletionByKey_UserId(id);
    }

    public int totalTaskCompletionResultTrue(Long id) {
        return taskCompletionRepository.countTaskCompletionByKey_UserIdAndResult(id, true);
    }

    private TaskCompletion createTaskCompletion(User user, Task task, String answer, Boolean result) {
        TaskCompletion taskCompletion = new TaskCompletion();
        taskCompletion.setTask(task);
        taskCompletion.setUser(user);
        taskCompletion.setAnswer(answer);
        taskCompletion.setResult(result);
        taskCompletion.setCompletionDate(new Date());
        taskCompletion.setKey(createTaskCompletionKey(user.getId(), task.getId()));
        return taskCompletion;
    }

    private TaskCompletionKey createTaskCompletionKey(Long userId, Long taskId) {
        TaskCompletionKey key = new TaskCompletionKey();
        key.setTaskId(taskId);
        key.setUserId(userId);
        return key;
    }


}
