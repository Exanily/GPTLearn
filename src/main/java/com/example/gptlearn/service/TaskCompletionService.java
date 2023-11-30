package com.example.gptlearn.service;

import com.example.gptlearn.entity.Task;
import com.example.gptlearn.entity.TaskCompletion;
import com.example.gptlearn.entity.User;
import com.example.gptlearn.entity.key.TaskCompletionKey;
import com.example.gptlearn.repository.TaskCompletionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TaskCompletionService {

    private final UserService userService;
    private final TaskService taskService;
    private final TaskCompletionRepository taskCompletionRepository;
    private final TaskGeneratorService taskGeneratorService;

    @Transactional
    public Boolean add(Long taskId, String answer) {
        Task task = taskService.getTaskById(taskId);
        User user = userService.getCurrentUser();
        Boolean result = taskGeneratorService.checkAnswer(task, answer);
        taskCompletionRepository.save(createTaskCompletion(user, task, answer, result));
        return result;
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
