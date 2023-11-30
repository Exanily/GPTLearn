package com.example.gptlearn.service;

import com.example.gptlearn.entity.Task;
import com.example.gptlearn.exception.TaskNotFoundException;
import com.example.gptlearn.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task add(Task task) {
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Задание не найдено"));
    }

    public List<Task> getTaskAll() {
        return taskRepository.findAll();
    }

    public List<Task> addAll(List<Task> tasks) {
       return taskRepository.saveAll(tasks);
    }
}
