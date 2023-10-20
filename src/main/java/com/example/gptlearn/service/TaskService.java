package com.example.gptlearn.service;

import com.example.gptlearn.entity.Task;
import com.example.gptlearn.entity.Theme;
import com.example.gptlearn.model.dto.Complexity;
import com.example.gptlearn.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final ChatService chatService;
    private final TaskRepository taskRepository;
    private final ThemeService themeService;

    public Task add(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public Task generate(String themeString, Complexity complexity) {
        String message = patternTask(themeString, complexity);
        String taskString = chatService.chat(message);
        Theme theme = themeService.findByName(taskString);
        //return add(stringToTask(taskString, theme, complexity));
        System.out.println(taskString);
        return new Task();
    }

    public boolean checkAnswer(String message) {
        return false;
    }

    private Task stringToTask(String taskString, Theme theme, Complexity complexity) {
        String description = taskString;
        String hint = taskString;
        String solution = taskString;
        Task task = new Task();
        task.setComplexity(complexity);
        task.setTheme(theme);
        task.setHint(hint);
        return task;
    }

    private String patternTask(String theme, Complexity complexity) {
        return "Придумай задание по теме \"" + theme + "\", сложность \"" + complexity.getValue() + "\", с подсказкой и ответом";
    }

    private String patternAnswer(String task, String answer) {
        return "Ответь на задание только сообщением \"true\" или \"false\"\n" +
                "Задание: " + task + "\n" +
                "Мой ответ: " + answer;
    }
}
